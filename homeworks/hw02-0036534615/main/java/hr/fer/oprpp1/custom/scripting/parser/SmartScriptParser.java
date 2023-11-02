package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexer;

public class SmartScriptParser {
     SmartScriptLexer lexer;
     
     public SmartScriptParser(String text) {
    	 this.lexer = new SmartScriptLexer(text);
     }
	
}
