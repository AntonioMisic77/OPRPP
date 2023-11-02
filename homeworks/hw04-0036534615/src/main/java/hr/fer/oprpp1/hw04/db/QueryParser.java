package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;

public class QueryParser {	
	
  private static class QueryLexer {
		
		private static class Token{
			
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
			
			public String toString() {
				return tokenType.toString()+" "+value;
			}
		}
		
		private static enum TokenType{
			Attributes,
			StringLiteral,
			ComparisonOperator,
			AND
		}
		
		private Token token;
		private char[] elements;
		private int currentIndex;
		
		
		public QueryLexer(String query) {
			this.elements = query.toCharArray();
			this.currentIndex = 0;
		}
		
		public Token nextToken() {
			String attribute = "";
			String literal = "";
			String operator = "";
			String[] operators = {">",">=","<","<=","=","!="};
			char[]  skipingChars = {' ','\t'};
			boolean skip = false;
			boolean test = false;
			
			for(int i=currentIndex;i<elements.length;i++) {
				
				for(int x=0;x< skipingChars.length;x++) {
					if (elements[i] == skipingChars[x]) {
						skip = true;
					}
				}
				if (!skip) {
				   skip = false;
				   boolean isCharLetter = Character.isLetter(elements[i]);
				   
				   if (isCharLetter) {
					 
					   if (attribute.toUpperCase().equals("AND")) {
						   currentIndex = i;
						   return new Token(TokenType.AND,attribute);
					   }
					   if (elements[i] == 'L') {
						   if(attribute.length() > 0) {
							   test = true;
							   currentIndex = i;
							   return new Token(TokenType.Attributes,attribute);
						   }
					   }
					   attribute+= elements[i];
					    if (attribute.equals("LIKE")) {
						   currentIndex = i+1;
						   return new Token(TokenType.ComparisonOperator,attribute);
					   }
					  
				   }else {
					 if (elements[i] == '\"') {
						 int x;
						 for(x = i+1;x < elements.length;x++) {
							 if (elements[x] == '\"') break;
							 literal+=elements[x]; 
						 }
						 currentIndex = x+1;
						 return new Token(TokenType.StringLiteral,literal);
					 } else {
						for(int x=0;x < operators.length;x++) {
						  if (elements[i] == operators[x].charAt(0)) {
						   if (attribute.length() > 0) {
							  currentIndex = i;
						      return new Token(TokenType.Attributes,attribute); 
						   }else {
							   if (operators[x].length() == 2) {
								   if (elements[i+1] == operators[x].charAt(1)) {
									   currentIndex=i+2;
								   }
							   }else {
								   currentIndex = ++i;   
							   }
							   return new Token(TokenType.ComparisonOperator,operators[x]);
						    }   
					      }
						} 	 
				    }
					 
					 
				  }
				   
				}
				skip = false;
			}
			
			return null;
		}
		
		
  }
 
  private QueryLexer lexer;
  private String literal;
  private List<QueryLexer.Token> lista = new ArrayList<>();
  private List<ConditionalExpression> expr = new ArrayList<>();
  
  public QueryParser(String literal) {
	  this.lexer = new QueryLexer(literal);
	  this.literal = literal;
	  this.getTokens();
  }
   
  public boolean isDirectQuery() {
	  
	  if (expr.size() == 0) expr.addAll(this.getQuery());
	 
	  if (expr.size() == 1) {
		  ConditionalExpression elem = expr.get(0);
		  if (elem.getFieldGetter().equals(FieldValueGetters.JMBAG) 
				 && elem.getComparisonOperator().equals(ComparisonOperators.EQUALS)){
			  return true;
		  }
	  }
	  return false;
  }
  
  public String getQueriedJMBAG() {
	  
	  if (this.isDirectQuery()) {
		  return expr.get(0).getStringLiteral();
	  }else {
		 throw new IllegalStateException(); 
	  }  
  }
  
  public List<ConditionalExpression> getQuery(){
	  
	  List<ConditionalExpression> list = new ArrayList<>();
	  
	  String[] atributes = {"jmbag","lastName","firstName"};
	  String[] operators = {">",">=","<","<=","=","!=","LIKE"};
	  
	  IFieldValueGetter getter = null;
	  IComparisonOperator operator = null;
	  String literal= "";
	  for(var element : lista) {
		  
		  if (element.tokenType.equals(QueryLexer.TokenType.Attributes)) {
			   if (getter != null) return null;
			   if (element.value.equals(atributes[0])) getter = FieldValueGetters.JMBAG;
			   else if (element.value.equals(atributes[1])) getter = FieldValueGetters.LAST_NAME;
			   else if (element.value.equals(atributes[2])) getter = FieldValueGetters.FIRST_NAME;
			   
		  } else if (element.tokenType.equals(QueryLexer.TokenType.ComparisonOperator)) {
			  
			  if (element.value.equals(operators[0])) operator = ComparisonOperators.GREATER;
			  else if (element.value.equals(operators[1])) operator = ComparisonOperators.GREATER_OR_EQUAL;
			  else if (element.value.equals(operators[2])) operator = ComparisonOperators.LESS;
			  else if (element.value.equals(operators[3])) operator = ComparisonOperators.LESS_OR_EQUAL;
			  else if (element.value.equals(operators[4])) operator = ComparisonOperators.EQUALS;
			  else if (element.value.equals(operators[5])) operator = ComparisonOperators.NOT_EQUALS;
			  else if (element.value.equals(operators[6])) operator = ComparisonOperators.LIKE;
			  
		  } else if (element.tokenType.equals(QueryLexer.TokenType.StringLiteral)) {
			  literal = element.value;
		  } else if (element.tokenType.equals(QueryLexer.TokenType.AND)) {
			  list.add(new ConditionalExpression(
					       getter,
					       literal,
					       operator  
					  ));
			  getter = null;
			  literal = null;
			  operator = null;
			  
		  }	  	  
	  }
	  
	  list.add(new ConditionalExpression(
		       getter,
		       literal,
		       operator  
		  ));
	  
	  return list;
  }
  
  private void getTokens(){
	    
	  boolean nesto = true;
	  do {
		  QueryLexer.Token token = lexer.nextToken();
		  if (token != null) {
			  lista.add(token);
		  } else {
			  nesto = false;
		  }
	  }while (nesto == true);
	  
	  
  }
  
  
  public static void main(String[] args) {
 
	  String query = " firstName!=\"A\" and firstName<\"C\" and lastName LIKE \"B*ć\" and jmbag>\"0000000002\"\t\n";
	  
	  QueryParser queryParser = new QueryParser(query);
	  
	  
	  List<ConditionalExpression> listina = queryParser.getQuery();
	  
	  
	  System.out.println(listina.size());
	  
	  for(var element : listina) {
		  System.out.println(element);
	  }
	  //this.lista = queryParser.getTokens();
	  
	  //for(QueryLexer.Token element : list) {
		//  System.out.println(element);
	 // }
	  
	  
}
	
}
