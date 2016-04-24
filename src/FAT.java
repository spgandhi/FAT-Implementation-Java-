import java.util.ArrayList;
import java.util.Arrays;


public class FAT {

	public ArrayList<String[]> files;
	public int[] FATTable;
	public int nextFreeBlock = 0;
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
		
		String[] e = new String[4];
		
		e[0] = fileName;											//Filename	
		System.out.println(this.nextFreeBlock);
		e[1] = String.valueOf(this.nextFreeBlock);					//File Start
		e[2] = String.valueOf(0);									//File Size
		e[3] = String.valueOf(0);									//Latest Version									

		System.out.println(e[0] + " - " + e[1] + " - " + e[2] + " - " + e[3]);
		// Adding file to the File ArrayList
		this.files.add(e);
		updateNextFreeBlock();
		
		// Pointing the end of file to index 0 whose value will be -1		
		FATTable[Integer.parseInt(e[1])] = -1;
		System.out.println("File starts at index " + e[1] + " and its size is set to " + e[2]);
		System.out.println("after file creation");
		this.allFATS();
		
		return true;
	}

	private void updateFreeBlock() {
		// TODO Auto-generated method stub
		
	}

	public boolean checkIfFileExists(String fileName) {
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
		System.out.println("We are in file " + fileName);
		this.setVersion(fileName, latestVersion);
		int currentBlock = getFileStart(fileName);
		System.out.println("Returned File start is " + currentBlock);
		int oldBlockSize = getFileSize(fileName);
		System.out.println("Returned File size is " + oldBlockSize);
		int newBlockSize = newData.length;
		int lastAccessedBlock = 0;
		
		for(int i=0;i<newBlockSize/16; i++){
 			
			
 			
			for(int j=0; j<16;j++){
				data[currentBlock*16+j] = newData[i*16+j];
				System.out.println("Writing " + data[currentBlock*16+j] + " at index " + (currentBlock*16 + j ));
			}
			
//			System.out.println("LastAccessedBlock is " + lastAccessedBlock);
//			System.out.println("CurerntBLock is " + currentBlock);
			
			
			if(i == newBlockSize/16-1 && newBlockSize/16 < oldBlockSize){
//				System.out.println("For file " + fileName);
//				System.out.println(newBlockSize/16 + " - " + oldBlockSize);
//				System.out.println("int there");
				int tempNextBlock = FATTable[currentBlock];
				int nextBlock = FATTable[currentBlock];
				
				while (tempNextBlock != -1){
					
					System.out.println("tempnextblock = " + tempNextBlock);
					tempNextBlock = FATTable[tempNextBlock];
					FATTable[nextBlock]= -2;
					nextBlock = tempNextBlock;
					
				}
				FATTable[currentBlock] = -1;
			}
			
			
			if(FATTable[currentBlock] == -2){
//				System.out.println("In 1");
				FATTable[currentBlock] = -1;
				FATTable[lastAccessedBlock] = currentBlock;
				lastAccessedBlock = currentBlock;
				updateNextFreeBlock();
				currentBlock = this.nextFreeBlock;
//				System.out.println("Curernt Block is set to " + currentBlock + " for next round.");
			}else if(FATTable[currentBlock] == -1){
//				System.out.println("In 2");
				lastAccessedBlock = currentBlock;
				updateNextFreeBlock();
				currentBlock = this.nextFreeBlock;
//				System.out.println("Curernt Block is set to " + currentBlock + " for next round.");
			}else{
//				System.out.println("In 3");
				lastAccessedBlock = currentBlock;
				currentBlock = FATTable[currentBlock];
//				System.out.println("Curernt Block is set to " + currentBlock + " for next round.");
			}
			
			this.allFATS();
			System.out.println("Next free block is " + this.nextFreeBlock);
			System.out.println();
			
			// For the case where new data is larger than old.			
//			if(FATTable[currentBlock] == -1){
//				for(i=i+1; i<newBlockSize/16; i++){
//					updateNextFreeBlock();
//					FATTable[currentBlock] = this.nextFreeBlock;
//					currentBlock = this.nextFreeBlock;
//					FATTable[currentBlock] = -1;
//					
//					for(int j=0; j<15;j++){
//						data[currentBlock*16+j] = newData[i*16+j];
//					}
//					
//				}
//				break;
//			}
			
			
		
		}
		
		
		
		this.setFileSize(fileName, newBlockSize/16);
		System.out.println("File size set to " + newBlockSize/16 + " after saving.");
	}
	
	private void addData(int startPoint, char[] newData, int dataStart) {
		
		for(int i=0;i<16;i++){
			data[dataStart + i] = newData[startPoint + i];
//			System.out.println("Writing " + newData[startPoint + i] + " at index " + (dataStart+i));
		}
		
	}

	public char[] readFile(String fileName){
		
		int startPoint = findFile(fileName);
		System.out.println("File start is " + startPoint);
		
//		if(FATTable[startPoint]== -1){
//			char[] temp = new char[1];
//			return temp;
//		}
		
		int fileSize = getFileSize(fileName);
		char[] returnData = new char[fileSize*16];
		
		for(int i=0; i<fileSize; i++){
			
			for(int j=0; j<16;j++){
				System.out.println("Reading " + data[startPoint*16 + j] + "from index " + (startPoint*16 + j)  );
				returnData[16*i + j ] = data[startPoint*16+j];
				
			}
			
			startPoint = FATTable[startPoint];
		}
		
		return returnData;
	}
	
	

	private int findFile(String fileName) {
		for (String[] file : this.files){
			if (file[0].equals(fileName))
				return Integer.parseInt(file[1]);
		}
		
		return -1;
		
	}
	
	private int getFileStart(String fileName) {
		
		for (String[] file : this.files){
			if (file[0].equals(fileName))
				return Integer.parseInt(file[1]);
		}
		
		return -1;
	}
	
	private int setVersion(String fileName, int latestVersion) {
		
		for (int i=0; i<this.files.size(); i++){
			String[] file = this.files.get(i);
			if(file[0].equals(fileName)){
				file[3] = String.valueOf(latestVersion);
				this.files.set(i, file);
				return 0;
			}
				
		}
		
		return -1;
		
	}
	
	private int getVersion(String fileName){
		for (String[] file : this.files){
			if (file[0].equals(fileName));
				return Integer.parseInt(file[3]);
		}
		
		return -1;
	}
	
	public int setFileSize(String fileName, int size){
		for (int i=0; i<this.files.size(); i++){
			String[] file = this.files.get(i);
			if(file[0].equals(fileName)){
				file[2] = String.valueOf(size);
				this.files.set(i, file);
				return 0;
			}
				
		}
		
		return -1;
	}
	
	private int getFileSize(String fileName){
		for (String[] file : this.files){
			if (file[0].equals(fileName)){
				return Integer.parseInt(file[2]);
			}
		}
		
		return -1;
	}

	
	public void allData() {
		
		for(int i =0; i<200; i++){
			System.out.print(data[i]);
		}
		
	}
	
	public void allFATS() {
		for(int i=0 ;i<10; i++){
			System.out.print(String.valueOf(FATTable[i]) + ' ');
			
		}
		System.out.println();
	}


}
