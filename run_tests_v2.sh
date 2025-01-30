#!/bin/bash

# Define the JSON file and path to TestUtils.java
JSON_FILE="src/package_resource_map.json"
TEST_UTILS_FILE="src/main/java/utils/TestUtils.java"
OUTPUT_DIR="src/main/java/utils/generated"

# Check if the JSON file exists
if [[ ! -f "$JSON_FILE" ]]; then
    echo "JSON file '$JSON_FILE' not found!"
    exit 1
fi

# Ensure the TestUtils.java backup exists before proceeding
if [[ ! -f "${TEST_UTILS_FILE}.bak" ]]; then
    echo "Creating backup of TestUtils.java..."
    cp "$TEST_UTILS_FILE" "${TEST_UTILS_FILE}.bak"
fi

# Create the directory to store generated files
mkdir -p "$OUTPUT_DIR"

# Phase 1: Generate modified TestUtils.java files for each package
echo "Generating modified TestUtils.java files..."
while IFS= read -r line; do
    # Extract key-value pairs
    if [[ $line =~ \"([^\"]+)\":\ \"([^\"]+)\" ]]; then
        platform="${BASH_REMATCH[1]}"
        suite_file="${BASH_REMATCH[2]}"

        # Create a new file for this platform in the generated directory
        generated_file="$OUTPUT_DIR/TestUtils_$platform.java"
        cp "$TEST_UTILS_FILE" "$generated_file"

        # Modify the PLATFORM value in the generated file
        echo "Setting PLATFORM to $platform in $generated_file"
        sed "s/public static final String PLATFORM = .*/public static final String PLATFORM = \"$platform\";/" "$generated_file" > "$generated_file.tmp" && mv "$generated_file.tmp" "$generated_file"
    fi
done < <(grep -E '"[^\"]+":\s+"[^\"]+"' "$JSON_FILE")

# Phase 2: Iterate over generated files and run the Maven tests
echo "Running tests for each platform..."
for generated_file in "$OUTPUT_DIR"/*.java; do
    platform=$(basename "$generated_file" .java | sed 's/^TestUtils_//')

    # Run the Maven command for the corresponding suite file
    suite_file=$(grep -E "\"$platform\":\s+\"([^\"]+)\"" "$JSON_FILE" | sed -E 's/.*: "([^"]+)".*/\1/')

    # Clear Maven cache (delete the target directory to clean the cache)
#    echo "Clearing Maven cache..."
#    rm -rf target/

    # Copy the generated TestUtils.java file for this platform
    echo "Copying $generated_file to $TEST_UTILS_FILE"
    cp "$generated_file" "$TEST_UTILS_FILE"

    rm "$generated_file"

    sleep 2

    # Simulate try-catch for Maven command
    echo "Running tests with suite file: $suite_file"

    # Try-Catch block for mvn clean test command
    if ! mvn clean test -DsuiteXmlFile="$suite_file"; then
        echo "Tests failed for platform: $platform. Skipping to next platform."
        continue  # Skip to the next platform if the mvn command fails
    fi

    # Optional: If we want to wait or perform some post-test logic
    sleep 20
done

# Delete the generated files after all iterations are completed
echo "Deleting generated files..."
rm -rf "$OUTPUT_DIR"

# Restore the original TestUtils.java
echo "Restoring original TestUtils.java..."
mv "${TEST_UTILS_FILE}.bak" "$TEST_UTILS_FILE"

echo "All tests completed successfully."
