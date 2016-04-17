
public class FILE {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public char[] formatData(String data){
		
			int arrayLength = (data.length()/15)*16;
			
			if(data.length()%15 != 0 && data.length() > arrayLength)
				arrayLength += 16;
			
			
	        char[] returnData = new char[arrayLength];
	        
	        
	        int pointer = 0;
	        int num = 0;;
	        
	        char[] toBeDivided = data.toCharArray();
	        
	        for(int a=0; a<arrayLength; a++){
	       
	        	if(a >= toBeDivided.length+1){
	        		returnData[a] = '!';
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
		
}
