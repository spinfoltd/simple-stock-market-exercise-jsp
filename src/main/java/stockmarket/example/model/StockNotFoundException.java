package stockmarket.example.model;

public class StockNotFoundException extends Exception{
	private String msg;
	public StockNotFoundException(String stockSymbol) {
		super();
		this.msg = "Stock Symbol is not present in enlisted Stocks";
		
	}
	 

}
