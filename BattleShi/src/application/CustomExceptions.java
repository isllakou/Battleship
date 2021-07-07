package application;

public class CustomExceptions extends Exception {  
	 CustomExceptions(String s){  
	  super(s);  
	 }  
}  


class InvalidShipCheck{  	  
	   static void validate(int type) throws CustomExceptions{  
	     if(type<0 || type>5)  
	      throw new CustomExceptions("Ships must be type of 1,2,3,4 or 5");  
	   }
} 
//exception 2
class InvalidCountException extends Exception{
	InvalidCountException(String s){
		super(s);
	}
}

class ex2{
	static void check_types(int[] types) throws InvalidCountException{
		for(int i=0; i<4; i++) {
			for(int j=i+1; j<5; j++) {
			if(types[i]==types[j])
		      throw new InvalidCountException("Ships must be type of differents types"); 
		}
	  }
	}
}

//exception 3
class  OversizeException extends Exception {  
	 OversizeException(String s){  
	  super(s);  
	 }  
} 

//exception 4
class  OverlapTilesException extends Exception {  
	OverlapTilesException(String s){  
	  super(s);  
	 }  
}

//exception 5
class  AdjacentTilesException extends Exception {  
	AdjacentTilesException(String s){  
	  super(s);  
	 }  
}

