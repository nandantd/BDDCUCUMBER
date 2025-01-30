package TestKUKUFM;

import java.util.ArrayList;
import java.util.TreeSet;

import org.testng.annotations.Test;

public class Arrayliost {

	
	@Test
	public void test() {
		
		
		TreeSet set1 = new TreeSet();
		
		set1.add(1);
		set1.add(2);
		set1.add(3);
		
		ArrayList l1 = new ArrayList(new TreeSet(set1));
		
		for (int i = 0; i < l1.size(); i++) {
			
			System.out.println(l1.get(i));
			
		}
		
		
		
	}
}
