import java.util.ArrayList;
import java.util.Arrays;


public class FAT {

	public ArrayList<String[]> files;
	public int[] FATTable;
	public int nextFreeBlock;
	char[] data;
	
	
	public FAT(){
		
		data= new char[1024*1024*20];
		FATTable = new int[1024*1024*20];
		Arrays.fill(FATTable, -2);
		this.files = new ArrayList<String[]>();
		
	}
	
	public boolean CreateFile( String fileName ){
		
		if( checkIfFileExists(fileName) ){
			return false;
		}
		
		System.out.println(checkIfFileExists(fileName));
			
		
		String[] e = new String[4];
		
		//System.out.println("Next free block is " + this.nextFreeBlock);
		//	Array For File
		e[0] = fileName;
		e[1] = String.valueOf(this.nextFreeBlock);
		e[2] = String.valueOf(1);
		e[3] = String.valueOf(0);

		// Adding file to the File ArrayList
		this.files.add(e);
		//System.out.println("Created a new file with name " + e[0] + " starting at " + e[1] + " of size " + e[2] );
		
		// Pointing the end of file to index 0 whose value will be -1		
		FATTable[Integer.parseInt(e[1])] = -1;
		
		//updateNextFreeBlock();
		return true;
	}

	private boolean checkIfFileExists(String fileName) {
		for(String[] file: this.files){
			
			if(file[0].equals(fileName))
				return true;
		}
		
		return false;
		
	}

	private void updateNextFreeBlock() {
		
		
		for(int i=0; i<(1024*1024*20)/16; i++){
			
			int blockToCheck = (this.nextFreeBlock + i)%(1024*1024*20);
			if(FATTable[blockToCheck] == -2){
				this.nextFreeBlock = blockToCheck;
				return;
			}
				
		}
	}
	
	public void saveFile(String fileName, char[] newData, int latestVersion){
		
		this.setVersion(fileName, latestVersion);
		int currentBlock = getFileStart(fileName);
		int oldBlockSize = getFileSize(fileName);
		int newBlockSize = newData.length;
		
		
	//	System.out.println(currentBlock);
		for(int i=0; i < newBlockSize/16; i++){
			
			System.out.println("Current block is " + currentBlock);
			if(FATTable[currentBlock] != -1){
				
				for(int j=0; j<15;j++){
					data[currentBlock*16+j] = newData[i*16+j];
				}
				
			}else{
				
				FATTable[currentBlock] = this.nextFreeBlock;
				FATTable[this.nextFreeBlock] = -1;
				
				int dataStart = currentBlock*16;
				//System.out.println(newData);
				addData(i*16, newData, dataStart);
				
				updateNextFreeBlock();
			}
			
			if(i==newBlockSize-1){
				FATTable[currentBlock] = -1;
				break;
			}
			
			currentBlock = FATTable[currentBlock];
		}
		
		this.setFileSize(fileName, newData.length/16);
	}
	
	private void addData(int startPoint, char[] newData, int dataStart) {
		//System.out.println(newData );
		for(int i=0;i<16;i++){
			
			data[dataStart + i] = newData[startPoint + i];
			//System.out.println(dataStart);
			//System.out.println("Added character " + data[dataStart+i] + " at position " + String.valueOf(dataStart+i));
		}
		
	}

	public char[] readFile(String fileName){
		
		int startPoint = findFile(fileName);
		
		if(startPoint == -1){
			char[] temp = new char[1];
			return temp;
		}
			
		
		int fileSize = getFileSize(fileName);
		char[] returnData = new char[fileSize*16];
		
		for(int i=0; i<fileSize; i++){
			for(int j=0; j<16;j++){
				returnData[16*i + j ] = data[startPoint+j];
			}
			startPoint = FATTable[startPoint];
		}
		
		return returnData;
	}
	
	

	private int findFile(String fileName) {
		for (String[] file : this.files){
			if (file[0] == fileName);
				return Integer.parseInt(file[1]);
		}
		
		return -1;
		
	}
	
	private int getFileStart(String fileName) {
		
		//System.out.println("Searching for " + fileName);
		for (String[] file : this.files){
			if (file[0] == fileName)
				return Integer.parseInt(file[1]);
		}
		
		return -1;
	}
	
	private int setVersion(String fileName, int latestVersion) {
		
		for (int i=0; i<this.files.size(); i++){
			String[] file = this.files.get(i);
			if(file[0] == fileName){
				file[3] = String.valueOf(latestVersion);
				this.files.set(i, file);
				return 0;
			}
				
		}
		
		return -1;
		
	}
	
	private int getVersion(String fileName){
		for (String[] file : this.files){
			if (file[0] == fileName);
				return Integer.parseInt(file[3]);
		}
		
		return -1;
	}
	
	public int setFileSize(String fileName, int size){
		for (int i=0; i<this.files.size(); i++){
			String[] file = this.files.get(i);
			if(file[0] == fileName){
				file[2] = String.valueOf(size);
				this.files.set(i, file);
				return 0;
			}
				
		}
		
		return -1;
	}
	
	private int getFileSize(String fileName){
		for (String[] file : this.files){
			if (file[0] == fileName);
				return Integer.parseInt(file[2]);
		}
		
		return -1;
	}

	
	public void allData() {
		
		for(int i =0; i<100; i++){
			System.out.println(data[i]);
		}
		
	}


}
