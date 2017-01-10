package stockmarket.example.model;

import java.util.ArrayList;
import java.util.List;

public class StocksRepo {
	
	private List<MStock> stocks = new ArrayList<MStock>();
	
	public List<MStock> getStocks() {
		return stocks;
	}

	public void setStocks(List<MStock> stocks) {
		this.stocks = stocks;
	}	
}
