package TestKUKUFM;

public class elseif {
	
	public static void main(String[] args) {
		
	
    int arr[]= {1,2,3,4,5,6,7};
    
    for(int i=0;i<arr.length;i++) {
    	
    	if(arr[i]%2==0) {
    		if(arr[i]==4) {
    			continue;
    		}
    		System.out.println("print"+arr[i]);
    	}else {
    		System.out.println("not found any thing");
    	}
    }
    

}
}