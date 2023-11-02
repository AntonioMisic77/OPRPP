package hr.fer.oprpp1.hw04.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;



public class Demo {

	public static void main(String[] args) throws IOException {
		 
		 List<String> lines = Files.readAllLines(
		     Paths.get("src/main/java/hr/fer/oprpp1/hw04/db/database.txt"),
		     StandardCharsets.UTF_8		 
		 );
		 
		 StudentDatabase db = new StudentDatabase(lines);
		 Scanner sc = new Scanner(System.in);
		 
		 while(true) {
			 String input = sc.nextLine();
			 
			 if (input.equals("exit")) {
				 System.out.println("Goodbye.");
				 System.exit(0);
			 } else if(input.length() <= 4) {
				 System.out.println("Too short command. Try again!");
				 continue;
			 }
				 
			 if (input.substring(0,5).equals("query")) {
				 startQuery(input.substring(5),db);
			 }else {
				 System.out.println("You have typed wrong command.Try Again!");
				 continue;
			 }
		 }
	      
	}
	
	private static void startQuery(String query, StudentDatabase db) {
		
		QueryParser queryParser = new QueryParser(query);
		  List<StudentRecord> listina = new ArrayList<>();
		  if(queryParser.isDirectQuery()) {
			  StudentRecord record = db.forJMBAG(queryParser.getQueriedJMBAG());
			  listina.add(record);
		  }else {
			  for(StudentRecord r : db.filter(new QueryFilter(queryParser.getQuery()))) {
				  listina.add(r);
			}
		  }
		  
		  for(var element: RecordFormater.format(listina) ) {
			  System.out.println(element);
		  }
	}
}
