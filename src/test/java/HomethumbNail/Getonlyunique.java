package HomethumbNail;

import java.util.ArrayList;
import java.util.List;

public class Getonlyunique {
    public static void main(String[] args) {
        
        int a[] = { 1, 2, 3, 4, 5, 6, 7, 1, 2, 3, 4, 5, 8, 9 };
        
        // Convert the int array to an Integer list
        List<Integer> l = new ArrayList<>();
        for (int i : a) {
            l.add(i); // Add each int from the array to the list
        }

        ArrayList<Integer> l1 = new ArrayList<>();

        // Loop through each element of the list
        for (Integer firstlist : l) {
            // If the element is not already in l1, add it
            if (!l1.contains(firstlist)) {
                l1.add(firstlist);             
            }
            
        }

        // Print the unique elements
        System.out.println(l1);
    }
}
