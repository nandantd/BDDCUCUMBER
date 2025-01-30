package TestKUKUFM;

public class Splitsubtitle {
	
	public static void main(String[] args) {
		
		
		String s1="Story • Love • हिंदी";
		
		String s2[]=s1.split("•");
		for(int i=0;i<s2.length;i++) {
		System.out.println(s2[1]);
		}
		
	}

}
