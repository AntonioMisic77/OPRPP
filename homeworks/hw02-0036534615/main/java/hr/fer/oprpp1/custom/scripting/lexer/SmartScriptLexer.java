package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

public class SmartScriptLexer {
     private char[] textElements;
     private int currentIndex;
     private LexerType lexerType;
     private Token token;
     
     public SmartScriptLexer(String text) {
    	 this.textElements = text.toCharArray();
    	 this.currentIndex = 0;
    	 this.lexerType = LexerType.OutsideTag;
     }
     
     
     public Token nextToken() {
    	 
    	 String text="";
    	 boolean isTokenTag=false;
    	 char[] symbols = {'\n','\t','\r'};
    	 
    	 if (this.lexerType == LexerType.OutsideTag) {
    		 
    		 for(int i=currentIndex;i<textElements.length;i++) {
    		        if (textElements[i] == '\\') {
    		        	if (textElements[i+1] != '{') throw new SmartScriptParserException();
    		        	text+= '{';
    		        	i++;
    		        } else if(textElements[i] == '{' && textElements[i+1] == '$' && text.charAt(text.length()-1) != '{') {
    		        	 this.currentIndex = i+1;
		        		 token = new Token(TokenType.Text,text);
		        		 break;
    		        } else if (textElements[i] == '$' && textElements[i-1] == '{' && currentIndex > 0 ) {
    		        	String tag="";
    		        	tag += textElements[i-1];
    		        	for(int j=i;i<textElements.length;j++) {
    		        		tag+= textElements[j];
    		        		if (textElements[j] == '}' && textElements[j-1] == '$' ) break;
    		        	}
    		        	
    		        	isTokenTag = true;
    		            
    		        	token = new Token(TokenType.TAG,tag);
    		        	this.currentIndex=i+1;
    		        	break;
    		        	
    		        }
    		        else {
    		        	text+=textElements[i];
    		        }
    	       }
    		 
    		   if (isTokenTag == false) {
    			  token = new Token(TokenType.Text,text); 
    		   } 
    	 }
    	 else {
    		  String tagName="";
    		  String variableName="";
    		  String integer="";
    		  String doubler="";
    		  int index = 0;
    		  
    		  if(textElements[currentIndex] == '=') {
    			  tagName+='=';
    			  this.currentIndex++;
    			    token =  new Token(TokenType.TagName,tagName);
    		   }
    		  for(int i=currentIndex;i<textElements.length;i++) {
    			  char element = textElements[i];
    			  if (Character.isWhitespace(textElements[i]) == true) {
    				  if(tagName.length() > 0) {
    					  this.currentIndex = i;
    				      token = new Token(TokenType.TagName,tagName);
    				      break;
    				   } 
    				  if(variableName.length() > 0) {
    					  this.currentIndex = i;
    					  token = new Token(TokenType.Variable,variableName);
    					  break;
    				 
    				  }else {
    					 continue; 
    				  }
    				  
    			  } else {
    				  
    				  if (Character.isLetter(element) == true) {
    					  variableName+=element;
    				  } else if(Character.isDigit(element) == true) {
    					  if (variableName.length() > 0) variableName+=element;
    					  else {
    						  integer+=element;
    					  }
    					  
    				  }else {
    					  if (element == '_' && variableName.length() > 0) variableName+=element;
    				  }
    				  if(tagName.equals("END")) {
    					  token = new Token(TokenType.TagName,tagName);
    					  break;
    				  }
    				  tagName+=textElements[i];
    				  
    			  }
    		  }
    		  
    		  //if (token.getTokenType() == TokenType.TagName && token.getValue() == "=") this.currentIndex++;
    		  
    		 /* for(int i=currentIndex;i<textElements.length;i++) {
    			  
    			  if (textElements[0])
    			  
    			  
    			  if (textElements[i] == '=') {
    				  token = new Token(TokenType.TagName,"=");
    			  }
    			  
    			  
    		  }*/
    	 }
    	 
    	 
    	 return token;
     }
     
     public Token getToken() {
    	 return this.token;
     }
     
     public void setLexerType(LexerType lexerType) {
		this.lexerType = lexerType;
	}


	public static void main(String[] args) {
    	String s = "{ bla } blu \\{$=1$}. Nothing interesting {=here}";
    	String p = "A tag follows {$ FOR A7_123 $}.";
    	String z = "A tag follows {$END$}.";

		SmartScriptLexer lexer = new SmartScriptLexer(p);
		
		System.out.println(lexer.nextToken().getValue());
		System.out.println(lexer.nextToken().getValue());
		lexer.setLexerType(LexerType.InsideTag);
		System.out.println(lexer.nextToken().getValue());
		System.out.println(lexer.nextToken().getValue());
		
		//System.out.println(lexer.currentIndex);
	}
     
     
    	 
     
     
     
}
