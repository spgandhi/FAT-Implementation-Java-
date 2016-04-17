
import java.util.Arrays;
import java.util.Scanner;

public class MAIN {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FAT fat = new FAT();
		FILE file = new FILE();
		
		System.out.println("Welcome to this SnapShot!!");
		System.out.println ("To create a new file, input - create filename");
		System.out.println ("To read the most recent version of the file, input - 'read filename'.");
//		System.out.println("To read an older input - 'read filenamne versionNo'");
//		System.out.println("If you wish to delete a file, input - 'delete filename'");
		
//		fat.CreateFile("Shreyans");
//		fat.saveFile("Shreyans", file.formatData("Shreyans is a very good boy.He is great.Just Great."), 0 );
//		//fat.readFile("Shreyans");
//		
//		//fat.CreateFile("Vivek");
//		//fat.saveFile("Vivek", file.formatData("Vivek!! He is great!!"), 0 );
//		//fat.readFile("Shreyans");
//		
//		fat.saveFile("Shreyans", file.formatData("Shreyans is a very good boy He is great.He is amazing.He can do wordpress.. He can do MEteor"), 0 );
//		//System.out.println(fat.readFile("Shreyans"));
		
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
					fat.saveFile(command[1], file.formatData( sc.nextLine() ), 0);
				}else{
					System.out.println("Sorry the file already exists.Please create a file with a different name");
					continue;
				}
					
			}else if(command[0].equals("read")){
				if( fat.checkIfFileExists(command[1]) ){
					System.out.println(fat.readFile(command[1]));
				}else{
					System.out.println("Sorry the file does not exist. Pleas enter a valid file.");
				}
			}else if(command[0].equals("edit")){
				if(fat.checkIfFileExists(command[1])){
					System.out.println("Please enter the new text");
					fat.saveFile(command[1], file.formatData(sc.nextLine()), 0);
				}else{
					System.out.println("Sorry the file does not exist. Please enter a valid file name");
				}
			}
		
		}

	}

}
