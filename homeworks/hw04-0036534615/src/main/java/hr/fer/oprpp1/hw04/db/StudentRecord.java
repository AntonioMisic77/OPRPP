package hr.fer.oprpp1.hw04.db;

public class StudentRecord {
    
	// Variables
	private String jmbag;
	private String lastName;
	private String firstName;
	private int  finalGrade;
	
	
	// Ctor
	public StudentRecord(
			String jmbag, 
			String lastName, 
			String firstName, 
			int finalGrade) {
		
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + finalGrade;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}
	
	
	public String getJmbag() {
		return this.jmbag;
	}
	
	public String getLastname() {
		return this.lastName;
	}
	
	public String getFirstname() {
		return this.firstName;
	}
	
	public int getFinalGrade() {
		return this.finalGrade;
	}
	
	public String toString() {
		return this.jmbag + " " + this.lastName + " " + this.firstName + " " + finalGrade;
	}
}
