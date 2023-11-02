package hr.fer.oprpp1.hw04.db;

public class ComparisonOperators {

	public static final IComparisonOperator LESS =  (value1,value2) -> { return (value1.compareTo(value2) < 0) ? true : false;};
	public static final IComparisonOperator LESS_OR_EQUAL =  (value1,value2) -> { return (value1.compareTo(value2) <= 0) ? true : false;};
	public static final IComparisonOperator GREATER =  (value1,value2) -> { return (value1.compareTo(value2) > 0) ? true : false;};
	public static final IComparisonOperator GREATER_OR_EQUAL =  (value1,value2) -> { return (value1.compareTo(value2) >= 0) ? true : false;};
	public static final IComparisonOperator EQUALS =  (value1,value2) -> { return (value1.compareTo(value2) == 0) ? true : false;};
	public static final IComparisonOperator NOT_EQUALS =  (value1,value2) -> { return (value1.compareTo(value2) == 0) ? false : true;};
	public static final LikeOperator LIKE = new LikeOperator();
	
	private static class LikeOperator implements IComparisonOperator{

		@Override
		public boolean satisfied(String value1, String value2) {
			if (value1 == "" && value2 == "*") return true;
			if (value1.length() < (value2.length()-1)) return false;
		
			if (value2.contains("*")) {
				
				int index = value2.indexOf("*");
				
				if (index == 0) {
				   
				   return func1(value1,value2,index);
							
				} else if (index == (value2.length()-1)) {
					
					return func2(value1,value2,index);
					
				}else {
					
					if (func1(value1,value2,index) == false) return false;
					if (func2(value1,value2,index) == false) return false;
					
					return true;
				}
			}
			else {
				return ComparisonOperators.EQUALS.satisfied(value1, value2);
			}
			
		}
		
		private boolean func1(String value1,String value2, int index) {
			int x = value1.length()-1;
            
            for(int i=(value2.length()-1);i > index;i--) {
         	   if (value2.charAt(i) != value1.charAt(x)) return false;
         	   
         	   x--;
            }
            
            return true;
		}
		
		private boolean func2(String value1,String value2,int index) {
			for(int i=0;i <index ;i++) {
				if (value2.charAt(i) != value1.charAt(i)) return false;
			}
			
			return true;
		}
		
	}
	
}
