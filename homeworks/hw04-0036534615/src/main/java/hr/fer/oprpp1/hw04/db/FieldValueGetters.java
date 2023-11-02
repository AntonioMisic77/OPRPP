package hr.fer.oprpp1.hw04.db;

public class FieldValueGetters {

	public static final IFieldValueGetter FIRST_NAME = (rec) -> {return rec.getFirstname();};
	public static final IFieldValueGetter LAST_NAME  =  (rec) -> {return rec.getLastname();};
	public static final IFieldValueGetter JMBAG = (rec) -> {return rec.getJmbag();};
	
}
