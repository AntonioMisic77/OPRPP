package hr.fer.oprpp1.hw04.db;

public class ConditionalExpression {

	private IFieldValueGetter fieldGetter;
	private String stringLiteral;
	private IComparisonOperator comparisonOperator;
	
	public ConditionalExpression(IFieldValueGetter getter,
								String literal,
								IComparisonOperator operator) {
		this.fieldGetter = getter;
		this.stringLiteral = literal;
		this.comparisonOperator = operator;
	}
	
	public boolean Evaluate(StudentRecord record) {
		return comparisonOperator.satisfied(
				fieldGetter.get(record), 
				stringLiteral);
	}

	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	public String getStringLiteral() {
		return stringLiteral;
	}

	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
	
	public String toString() {
		return fieldGetter.toString() + " " + stringLiteral + " " + comparisonOperator.toString();
	}
	
	public static void main(String[] args) {
		String mda = ">=";
	}
}
