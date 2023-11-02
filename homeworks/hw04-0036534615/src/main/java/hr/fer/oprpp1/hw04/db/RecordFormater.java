package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class RecordFormater {

	private static Function<List<StudentRecord>,Integer> maxSizeOfFirstName = (record) -> {
		List<Integer> lista = new ArrayList<>();
		for(var element : record) {
			lista.add(element.getFirstname().length());
		}
		
		lista.sort((el1,el2)-> {return el2-el1;});
		return lista.get(0);	
	};
	
	private static Function<List<StudentRecord>,Integer> maxSizeOfLastName = (record) -> {
		List<Integer> lista = new ArrayList<>();
		for(var element : record) {
			lista.add(element.getLastname().length());
		}
		lista.sort((el1,el2)-> {return el2-el1;});
		return lista.get(0);	
	};

	
	public static List<String> format(List<StudentRecord> list){
		
		
		List<String> lista = new ArrayList<>();
		if (list.size() == 0) {
			lista.add("Records selected: 0");
			return lista;
		}
		
		String record = "";
		
		String okvir = "+============+";
		
		int max1 = maxSizeOfFirstName.apply(list);
		int max2 = maxSizeOfLastName.apply(list);
		
		for(int i=0;i<(max2+2);i++) {
			okvir+="=";
		}
		okvir+="+";
		for(int i=0;i<(max1+2);i++) {
			okvir+="=";
		}
		okvir+= "+===+";
		
		lista.add(okvir);
		for (var element : list) {
			
			String firstName = element.getFirstname();
			String lastName = element.getLastname();
			int pom = (max1-1) - firstName.length();
			int pom2 = (max2-1) - lastName.length();
			
			record = "| "+element.getJmbag()+" | "+element.getLastname();
			
			for(int j=0;j<pom2+1;j++) {
				record+=" ";
			}
			
			record+=" | ";
			
			record+=firstName;
			
			for(int j=0;j<pom+1;j++) {
				record+=" ";
			}
			
			record+=" | "+element.getFinalGrade()+" |";
			
			lista.add(record);
			record = "";
		}
		
		lista.add(okvir);
		lista.add("Records Selected: "+list.size());
		return lista;	
	}
	
	
}
