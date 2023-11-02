package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StudentDatabase {
	
    private List<StudentRecord> studentList;
    private Map<String,StudentRecord> map = new LinkedHashMap<String,StudentRecord>();
    
    
    public StudentDatabase(List<String> list) {
    	this.studentList = new ArrayList<StudentRecord>(list.size());
    	
        for (String element : list) {
        	String[] values = element.split("\\t");
        	this.varifyingData(values);
        	StudentRecord record = new StudentRecord(
        			            		values[0],
        			            		values[1],
        			            		values[2],
        			            		Integer.parseInt(values[3]));
        	studentList.add(record);
        	map.put(values[0], record);
        }
    }
    
    
    public StudentRecord forJMBAG(String jmbag) {
    	StudentRecord record = map.get(jmbag);
    	
    	if (record == null) {
    		return null;
    	}
    	
    	return record;
    }
    
    public List<StudentRecord> filter(IFilter filter){
    	
    	List<StudentRecord> records = new ArrayList<StudentRecord>();
    	
    	for(StudentRecord record : studentList) {
    		if (filter.accepts(record)) {
    			records.add(record);
    		}
    	}
    	
    	return records;
    }
 
    private boolean varifyingData(String[] values) {
    	String jmbag = values[0];
    	Integer finalGrade = Integer.parseInt(values[3]);
    	
    	for(StudentRecord record : studentList) {
    		if(record.getJmbag().equals(jmbag)) {
    			System.out.println("There is two records in db with same JMBAG" + jmbag +"!");
    			System.exit(-1);
    		}
    		if( finalGrade < 1 || finalGrade > 5) {
    			System.out.println("There is finalGrade with value "+finalGrade+". It must be betwenn 1 and 5");
    			System.exit(-1);
    		}
    	}
		return true;
    }
}
