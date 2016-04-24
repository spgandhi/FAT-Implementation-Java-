
public class FILE {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public char[] formatData(String data){
		
			int arrayLength = (data.length()/15)*16;
			
			if(data.length()%15 != 0)
				arrayLength += 16;
			
	        char[] returnData = new char[arrayLength];
	        
	        
	        int pointer = 0;
	        int num = 0;;
	        
	        char[] toBeDivided = data.toCharArray();
	        
	        for(int a=0; a<arrayLength; a++){
	       
	        	if(pointer == toBeDivided.length){
	        		returnData[a] = '/';
	        	}else if(a%16==0){
	                returnData[a] = '0';   
	        	}else{		
	        		//System.out.println(a);
	                returnData[a]=toBeDivided[pointer];
	                pointer++;
	        	}
	        	
	            num=a;
	        }
	      
		    
	        System.out.println(returnData);
		    //System.out.println(returnData.length);
		    return returnData;
	}  
	
	public char[] reformat(char[] data){
		char[] returnData = new char[data.length - data.length/16];
		
		int j=0;
		for(int i=0; i<data.length; i++){
			
			if(i%16 != 0){
				if(data[i] != '/')
					returnData[j] = data[i];
				j++;
			}
		}
		
		return returnData;
		
	}
		
}
