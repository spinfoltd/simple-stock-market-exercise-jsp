package stockmarket.example.dao;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import stockmarket.example.model.MStock;
import stockmarket.example.model.MTrade;
import stockmarket.example.model.StocksRepo;

public class MTradeDao {
	
	@Inject
	StocksRepo stocksRepo;
	
	public StocksRepo getStocksRepo() {
		return stocksRepo;
	}

	public void setStocksRepo(StocksRepo stocksRepo) {
		this.stocksRepo = stocksRepo;
	}
	
	public List<MTrade> getAllTrades(){
		List<MTrade> allTrades = new ArrayList<MTrade>();
		for(MStock stock:stocksRepo.getStocks()){
			allTrades.addAll(stock.getStockTrades());
		}
		return allTrades;
	}
	
	public List<MTrade> getAllTradesWithin15Mins(){
		List<MTrade> allTrades = new ArrayList<MTrade>();
		for(MTrade trade : getAllTrades()){
			if(trade.isTradeTimeWithin15Mins()){
				allTrades.add(trade);
			}
		}
		return allTrades;
	}
	
}
