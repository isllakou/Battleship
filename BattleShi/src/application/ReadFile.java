package application;

import java.io.*;
import java.util.*;

public class ReadFile{
	private Scanner scanner;
	 int[] type = new int[5];
	 int[] x = new int[5];
	 int[] y = new int[5];
	 int[] orientation = new int[5]; 
	 int count=0;
	 int find=0;
	public void findFile(String who, String id) {
			
			File folder = new File("C:\\Users\\ioann\\eclipse-workspace\\ReadFile\\src\\medialab");
			File[] matchingFiles = folder.listFiles(new FilenameFilter() {
			    public boolean accept(File dir, String name) {
			    	if(name.indexOf(id)!=-1 && name.startsWith(who)) {//has scenario id
			    		find=count;
			    	   return name.startsWith(who) && name.endsWith("txt") && name.indexOf(id)!=-1;
			    	}
			    	else if(name.indexOf(id)==-1 && name.startsWith(who)) {
			    		count++;
			    	   return name.startsWith(who) && name.endsWith("txt");
			    	}
			    	else {
			    		return false;
			    	}
			    }
			});
			
			readFile(matchingFiles[find]);
	}
	
			
	 
	public void readFile(File filename) {
		//open file
		try {
			scanner = new Scanner(filename);
			
		}
		catch(Exception e) {
			System.out.println("Could not find file");
		}
		//read file
		scanner.useDelimiter(",|\\r\\n");
	    int i=0;
		while(scanner.hasNextLine()) {
			type[i]=scanner.nextInt();
			try{  
				InvalidShipCheck.validate(type[i]); 
			      }
			catch(Exception m){System.out.println("Exception occured: "+m);} 
			
			x[i] = scanner.nextInt();
			y[i] = scanner.nextInt();
			orientation[i] = scanner.nextInt();
			i++;
		}
		//close file
		scanner.close();
	}
	
	
	
}
