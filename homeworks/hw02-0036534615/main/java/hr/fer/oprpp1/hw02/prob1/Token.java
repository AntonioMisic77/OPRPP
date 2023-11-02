package hr.fer.oprpp1.hw02.prob1;

public class Token {
    
	// Variables
	
	private TokenType type;
	private Object value;
	
    // Constructor
	
	public Token(TokenType type, Object value) {
		super();
		this.type = type;
		this.value = value;
	}
	
	// Methods
	
	public TokenType getType() {
		return type;
	}

	public Object getValue() {
		return value;
	}
	
	
	
}
