package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;

public class QueryFilter implements IFilter {

	List<ConditionalExpression> expressions = new ArrayList<>();
	
	public QueryFilter(List<ConditionalExpression> expressions) {
		this.expressions = expressions;
	}
	
	@Override
	public boolean accepts(StudentRecord record) {
		
		int counter = 0;
		for(var element : expressions) {
			if (element.Evaluate(record)) counter++;
		}
		
		if(counter == expressions.size()) return true;
		return false;
	}

	
}
