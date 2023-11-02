package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ObjectStack;

public class StackDemo {
       public static void main(String[] args) {
    	   String[] array = args[0].trim().split(" ");
    	   ObjectStack stack = new ObjectStack();
    	    
    	  for(int i=0;i<array.length;i++) {
    		   char c = array[i].charAt(0);
    		   if (Character.isDigit(c) == true || array[i].length() == 2) {
    			   int number = Integer.parseInt(array[i]);
    			   stack.push(Integer.valueOf(number));
    		   }else {
    			   int secondNumber = (Integer)stack.pop();
    			   int firstNumber = (Integer)stack.pop();
    			   int result = calculate(firstNumber,secondNumber,c);
    			   stack.push(Integer.valueOf(result));
    		   }
    	   }
    	  
    	   if(stack.size() != 1) System.out.println("Error");
    	   else System.out.println(stack.pop());
       }
       
       /**
        * Metoda nam radi osnovne matematicke operacije pomocu zadana joj 2 broja i operatora.
        * Metoda obradjuje matematicke operacije (+,-,*,/,%)
        * @param firstNumber
        * @param secondNumber
        * @param operator, + - / * %
        * @return broj, rezultat operacije
        * @return UnsupportedOperationException, ako je
        */
       
       private static int calculate(int firstNumber,int secondNumber,char operator) {
    	   
    	   if (operator == '+') {
    		   return (firstNumber + secondNumber);
    	   } else if( operator == '-') {
    		   return (firstNumber - secondNumber);
    	   }else if( operator == '*') {
    		   return (firstNumber * secondNumber);
    	   } else if( operator == '/') {
    		   return (firstNumber/secondNumber);
    	   }else if (operator == '%') {
    		   return (firstNumber % secondNumber);
    	   }else {
    		   throw new UnsupportedOperationException();
    	   }
       }
}
