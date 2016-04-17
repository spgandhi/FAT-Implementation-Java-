
import java.util.Arrays;
import java.util.Scanner;

public class MAIN {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FAT fat = new FAT();
		
		System.out.println("Welcome to this SnapShot!!");
		System.out.println ("To create a new file, input - create filename");
		System.out.println ("To read the most recent version of the file, input - 'read filename'.");
		System.out.println("To read an older input - 'read filenamne versionNo'");
		System.out.println("If you wish to delete a file, input - 'delete filename'");
		
		while(true){
			
			System.out.println("$ ");
			Scanner sc=new Scanner(System.in);
			String[] command = sc.nextLine().trim().split(" ");
			
			System.out.println(command[0]);
			if(command[0].equals("quit"))
				break;
			
			if(command.length > 3 || command.length<2){
				System.out.println("Sorry wrong input. Please try again.");
				continue;
			}
			
			if(command[0].equals("create")){
				if( fat.CreateFile(command[1]) ){
					System.out.println(command[1] + " file has been created. Please type the content below.");
					FILE file = new FILE();
					fat.saveFile(command[1], file.formatData( sc.nextLine() ), 0);
				}else{
					System.out.println("Sorry the file already exists.Please create a file with a different name");
					continue;
				}
					
			}
			
			
				
		
		}
		
		fat.allData();
		
		//fat.CreateFile("Shreyans");
		//char[] newData = "Shreyans is a go".toCharArray();
		
		//fat.saveFile("Shreyans", newData, 0);
		
		//fat.CreateFile("Vivek");
		//char[] newData1 = "Shreyans is n go".toCharArray();
		//fat.saveFile("Vivek", newData1, 0);
		
		//fat.saveFile("Shreyans", "Shreyans is a goShreyans is n go".toCharArray(), 1);
		//System.out.println(fat.readFile("Shreyans"));
		
		
		//FILE file = new FILE();
		//file.formatData("Shreyans ");
	}

}
