import os
import re
import json
import shutil
import subprocess
import time
import datetime
import random
import string
import logging
import argparse
from python_utils.memcached_managers import MemcachedManager
from python_utils.slack_manager import SlackManager
from python_utils.file_upload_manager import FileUploadManager

# Define log directory and file
LOG_DIR = "/var/log/kukufm"
LOG_FILE = os.path.join(LOG_DIR, "test_utils_automation.log")

# Set up logging
logging.basicConfig(
    filename=LOG_FILE,
    level=logging.INFO,
    format="%(asctime)s - %(levelname)s - %(message)s",
)
console_handler = logging.StreamHandler()
console_handler.setLevel(logging.INFO)
formatter = logging.Formatter("%(asctime)s - %(levelname)s - %(message)s")
console_handler.setFormatter(formatter)
logging.getLogger().addHandler(console_handler)

# Set Args
parser = argparse.ArgumentParser(description="A Python script.")
parser.add_argument('--package_name', type=str, help='App Package name')
args = parser.parse_args()

# Paths and constants
PROJECT_DIR = "/home/ubuntu/mob-auto/kukufm-mobile-automation"
JSON_FILE = os.path.join(PROJECT_DIR, "src/package_resource_map.json")
TEST_UTILS_FILE = os.path.join(PROJECT_DIR, "src/main/java/utils/TestUtils.java")
MEMCACHE_KEY = "is_automation_running"
UNIQUE_ID = ''.join(random.choices(string.ascii_uppercase + string.digits, k=10))
now = datetime.datetime.now()

memcached_client = MemcachedManager.get_memcached_client()
# Check for script current running state.
if memcached_client.get(MEMCACHE_KEY):
    logging.info(f"Script already running.")
    exit(1)

# Set script state as running.
memcached_client.set(MEMCACHE_KEY, "running", 60 * 10 * 3)

# Load JSON data
if not os.path.isfile(JSON_FILE):
    logging.error(f"{UNIQUE_ID}: JSON file '{JSON_FILE}' not found!")
    exit(1)

with open(JSON_FILE, "r") as f:
    try:
        data = json.load(f)
    except json.decoder.JSONDecodeError:
        SlackManager.send_message_to_channel(
            channel_name='#qa-automation-report',
            message=f'QA exited for {UNIQUE_ID}. Please check resource map json file <@UDB44D5GT>, <@U06CR0KE9U5>'
        )
        memcached_client.delete(MEMCACHE_KEY)
        exit(1)

# In case we want to run it for single
package_name = args.package_name
if package_name:
    suite_file = data.get(package_name, None)
    if not suite_file:
        logging.error(f"{UNIQUE_ID}: failed for {package_name}")
        exit(1)
    data = {package_name: suite_file}

logging.info(f"{UNIQUE_ID}: Loaded JSON data from '{JSON_FILE}'.")

# Backup the original TestUtils.java
backup_file = f"{TEST_UTILS_FILE}.bak"
if not os.path.isfile(backup_file):
    logging.info(f"{UNIQUE_ID}: Creating backup of TestUtils.java...")
    shutil.copy(TEST_UTILS_FILE, backup_file)

SlackManager.send_message_to_channel(channel_name='#qa-automation-report',
                                     message=f'QA Automation started for {UNIQUE_ID}')

overall_total_passed, overall_total_failed = 0, 0
# Iterate over each platform in the JSON and perform modification and testing
logging.info(f"{UNIQUE_ID}: Processing platforms...")
for platform, suite_file in data.items():
    logging.info(f"Modifying TestUtils.java for platform {platform}...")
    # Read the original file
    with open(TEST_UTILS_FILE, "r") as file:
        content = file.read()

    # Modify the PLATFORM value
    content = re.sub(
        r'public static final String PLATFORM = ".*?";',
        f'public static final String PLATFORM = "{platform}";',
        content
    )

    # Write the modifications back to the file
    with open(TEST_UTILS_FILE, "w") as file:
        file.write(content)

    # Run Maven tests
    logging.info(f"{UNIQUE_ID}: Running Maven tests for platform: {platform} with suite file: {suite_file}")
    process_result = True
    try:
        result = subprocess.run(
            ["mvn", "clean", "test", f"-DsuiteXmlFile={suite_file}"],
            check=True,
            cwd=PROJECT_DIR,
            stdout=subprocess.PIPE,
            stderr=subprocess.PIPE,
            text=True,
            timeout=12000  # Timeout in seconds
        )
        logging.info(f"{UNIQUE_ID}: Maven Output for platform {platform}:\n{result.stdout}")
        text = result.stdout
    except subprocess.TimeoutExpired:
        logging.error(f"{UNIQUE_ID}: Maven tests timed out for platform: {platform} with suite file: {suite_file}")
        SlackManager.send_message_to_channel(
            channel_name='#qa-automation-report',
            message=f'QA Android Automation {UNIQUE_ID} | *{platform}*\n'
                    f'Tests timed out after 10 minutes.\n'
        )
        text = ""
        process_result = False
    except subprocess.CalledProcessError as e:
        text = e.stdout
        logging.error(f"{UNIQUE_ID}: Tests failed for platform: {platform} with suite file: {suite_file}")
        logging.error(f"{UNIQUE_ID}: Maven Output for platform {platform}:\n{e.stdout}")
        logging.error(f"{UNIQUE_ID}: Maven Errors for platform {platform}:\n{e.stderr}")
    finally:
        # Restore the original TestUtils.java after testing
        logging.info(f"{UNIQUE_ID}: Restoring original TestUtils.java after testing platform {platform}...")
        shutil.copy(backup_file, TEST_UTILS_FILE)

        # Only send result if flow is successful.
        if not process_result:
            continue

        # Upload the report to S3 and send Slack message
        with open(os.path.join(PROJECT_DIR, "ExtentTarget/fmreport.html"), 'r', encoding='utf-8') as file:
            html_content = file.read()

        logging.info(f"{UNIQUE_ID}: HTML is available for {platform}")

        link = "https://d1l07mcd18xic4.cloudfront.net/" + FileUploadManager.upload_raw_to_s3(
            raw_file=html_content,
            filename=f"qa-automation/{now.strftime('%d-%b-%Y')}/{platform}_{UNIQUE_ID}.html",
            content_type='text/html'
        )

        logging.info(f"{UNIQUE_ID}: HTML Link {link}")

        # Use regular expressions to extract the numbers
        passed_match = re.search(r"Total Passed Scenarios:\s+(\d+)", text)
        failed_match = re.search(r"Total Failed Scenarios:\s+(\d+)", text)

        # Extract and convert the numbers to integers
        total_passed = int(passed_match.group(1)) if passed_match else 0
        total_failed = int(failed_match.group(1)) if failed_match else 0
        overall_total_passed += total_passed
        overall_total_failed += total_failed

        SlackManager.send_message_to_channel(
            channel_name='#qa-automation-report',
            message=f'QA Android Automation {UNIQUE_ID} | *{platform}*\n'
                    f'Total passed: {total_passed}\n'
                    f'Total failed: {total_failed}\n\n'
                    f'<{link} | Download Report>'
        )

    # Optional delay between tests
    time.sleep(5)

# Cleanup: Restore the original TestUtils.java
logging.info(f"{UNIQUE_ID}: Restoring original TestUtils.java...")
shutil.move(backup_file, TEST_UTILS_FILE)

logging.info(f"{UNIQUE_ID}: All tests completed successfully.")

# Delete memcache
memcached_client.delete(MEMCACHE_KEY)
SlackManager.send_message_to_channel(
    channel_name='#qa-automation-report',
    message=f'QA Android Automation completed for {UNIQUE_ID}\n'
            f'Total passed: {overall_total_passed}\n'
            f'Total failed: {overall_total_failed}\n'
)
# Deployment channel.
SlackManager.send_message_to_channel(
    channel_name='#test-shubh',
    message=f'QA Automation completed for {UNIQUE_ID}. Ready to deploy all'
)
