package hr.fer.oprpp1.hw02.prob1;

public class Lexer {
	
	// Variables 
	private char[] data;
	private Token token; 
	private int currentIndex;
	private LexerState state;
	private String ss;
	
	
	// Constructor
	
	public Lexer(String text) {
		if (text == null) throw new NullPointerException();
		this.data = text.toCharArray();
		this.currentIndex = 0;
		this.state = LexerState.BASIC;
		this.ss = text;
	}
	
	// Methods 
	public Token nextToken() {
		
		
		int ignoreCounter=0;
		int potention = 0;
		String word="";
		char symbol='\0';
		String number="";
		boolean escape = false;
		char escapedChar='\0';
		char[] symbols = {'\n','\t','\r',' '};
		Token tokenSave = this.token;
		
		if (this.state == LexerState.BASIC) {
			if (token != null && token.getType() == TokenType.EOF) throw new LexerException();
		     for (int i=this.currentIndex;i<data.length;i++) {
		    	    boolean ischarLetter = Character.isLetter(data[i]);
		    	    boolean ischarNumber = Character.isDigit(data[i]);
		    	    boolean isEscapedCharEqual = escapedChar == data[i];
		    	    
					if (ischarLetter == false && ischarNumber == false) {
						if (data[i] == '\\' && escape == false) {
							escape = true;
							if (i == (data.length-1) || Character.isLetter(data[i+1]) == true) throw new LexerException();
							escapedChar = data[i+1];
						} else if (escape == true) {
							escape = false;
							word+=data[i];
						} else if (word.length() > 0 || number.length() > 0  || symbol != '\0') {
							this.currentIndex = i;
							break;
						}else {
							for (int j=0;j<symbols.length;j++) {
								  if (data[i] != symbols[j] && Character.isWhitespace(data[i]) == false) {
									symbol = data[i];
								  }
					    	 }
							if (symbol == '\0') ignoreCounter++;
						    continue;
						}
						
					}else {
					 if (ischarLetter == true || isEscapedCharEqual) {
						 if (number.length() > 0 || symbol != '\0') {
							 this.currentIndex = i;
							 break;
						 }
						if (escape == true) {
							escapedChar = '\0';
							word+=data[i];
							escape = false;
							continue;
						} 
						word+= data[i];
					  } else if (ischarNumber == true && escape == false) {
						 if (word.length() > 0 || symbol != '\0') {
							 this.currentIndex = i;
							 break;
						 }
						 number+=data[i];
						 if (number.length() > 19) throw new LexerException();
					  }
						
				   }
					
				 if (i == (data.length-1)) { // rubni slucaj bez razmaka na kraju
					 this.currentIndex = i+1;
				 }
			  }
		    
			if ((ignoreCounter > 0 &&
				 word.length() == 0 && 
				 number.length() == 0) || data.length == 0 )
				 token = new Token(TokenType.EOF,null);
		
			if (word.length() > 0) {
				token = new Token(TokenType.WORD,word);
			} else if (number.length() > 0) {
				token = new Token(TokenType.NUMBER,Long.parseLong(number));
			} else if (symbol != '\0') {
				token = new Token(TokenType.SYMBOL,symbol);
			} else {
				token = new Token(TokenType.EOF,null);
			}
			
		}else { // ADVANCED LEXER
			
			int wordCounter = 0;
			if (token != null && token.getType() == TokenType.EOF) throw new LexerException();
			if (this.data.length == 0) {
				token = new Token(TokenType.EOF,null);
				return token;
			} 
			
			for(int i=this.currentIndex;i<data.length;i++) {
				//if (ss.equals("Janko 3# Ivana26\\a 463abc#zzz")) System.out.println(i);
				boolean isCharLetter = Character.isLetter(data[i]);
				boolean isCharNumber = Character.isDigit(data[i]);
				boolean isCharWhiteSpace = Character.isWhitespace(data[i]);
				boolean isCharTarabe = data[i] == '#';
				
				if ((isCharWhiteSpace || isCharTarabe )&& word.length() > 0) {
					token = new Token(TokenType.WORD,word);
					this.currentIndex = i;
					break;
				} else if (isCharTarabe == true && word.length() == 0) {
					token = new Token(TokenType.SYMBOL,'#');
					this.currentIndex = i+1;
					break;
				}
				if(isCharNumber == false && isCharLetter == false) {
					for (int j=0;j<symbols.length;j++) {
						  if (data[i] != symbols[j] && isCharWhiteSpace == false) {
							  wordCounter++;
						  } else {
							  ignoreCounter++;
							  break;
						  }
			    	 }
					
				   if (wordCounter == symbols.length) word+=data[i];
					
				}else {
					word+= data[i];
				}
			}
			
			if (ignoreCounter > 0 && word.length() == 0) {
				token = new Token(TokenType.EOF,null);
			}
			
		}
		return token;
	}
		
	
	public Token getToken() {
		return this.token;
	}
	
	public void setState(LexerState state) {
		if (state == null) throw new NullPointerException();
		this.state = state;
	}

	public static void main(String[] args) {
		String s="";
		
		System.out.println(s.toCharArray().length);
	}
	
	
	
}
