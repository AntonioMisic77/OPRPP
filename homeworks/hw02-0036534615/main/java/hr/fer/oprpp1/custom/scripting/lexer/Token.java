package hr.fer.oprpp1.custom.scripting.lexer;

public class Token {
      private TokenType tokenType;
      private String value;
      
      public Token(TokenType tokenType,String value) {
    	  this.tokenType = tokenType;
    	  this.value = value;
      }

	public TokenType getTokenType() {
		return tokenType;
	}

	public String getValue() {
		return value;
	}
      
}
