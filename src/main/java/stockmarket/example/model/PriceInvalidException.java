package stockmarket.example.model;

public class PriceInvalidException extends Exception{
	String msg;
	public PriceInvalidException(String msg) {
		super();
		this.msg = "Price Cannot Be :"+msg;
	}
}
