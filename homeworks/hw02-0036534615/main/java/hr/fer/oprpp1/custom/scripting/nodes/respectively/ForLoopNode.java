package hr.fer.oprpp1.custom.scripting.nodes.respectively;

import hr.fer.oprpp1.custom.scripting.elems.*;

public class ForLoopNode extends Node {

	private ElementVariable variable;
	private Element startExpression; 
	private Element endExpression;
	private Element stepExpression;
	
	
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}
	
	
	public ElementVariable getVariable() {
		return variable;
	}

	public Element getStartExpression() {
		return startExpression;
	}

	public Element getEndExpression() {
		return endExpression;
	}

	public Element getStepExpression() {
		return stepExpression;
	}

	
	
}
