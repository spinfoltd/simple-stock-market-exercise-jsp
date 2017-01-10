package stockmarket.example.uifacade.input.types;

public class ProceedDefaultValueException  extends Exception {
	public Exception exceptionCaused;	
	
	public ProceedDefaultValueException( Exception exceptionCaused) {
		super();
		this.exceptionCaused = exceptionCaused;
	}

	public Boolean isCausedByException(Class<?> tClass){
		return (exceptionCaused.getClass().equals(tClass));
	}
}
