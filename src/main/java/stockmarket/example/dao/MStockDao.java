package stockmarket.example.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import stockmarket.example.model.MStock;
import stockmarket.example.model.MTrade;
import stockmarket.example.model.StocksRepo;

public class MStockDao {
	
	@Inject
	StocksRepo stocksRepo;
	
	public StocksRepo getStocksRepo() {
		return stocksRepo;
	}

	public void setStocksRepo(StocksRepo stocksRepo) {
		this.stocksRepo = stocksRepo;
	}

	public MStock findBySymbol(String symbol){
		for(MStock stock: stocksRepo.getStocks()){
			if(stock.getSymbol().equalsIgnoreCase(symbol)){
				return stock;
			}
		}
		return null;
	}
	
	public List<MTrade> getTradesForStock(String stockSymbol){
		MStock stock = findBySymbol(stockSymbol);
		if(null != stock){
			return stock.getStockTrades();
		}else{
			return null;
		}
	}
	
	public List<MTrade> getAllTrades(){
		List<MTrade> allTrades = new ArrayList<MTrade>();
		for(MStock stock:stocksRepo.getStocks()){
			allTrades.addAll(stock.getStockTrades());
		}
		return allTrades;
	}
	
	public List<MStock> getAllStocks(){
		return stocksRepo.getStocks();
	}
	
	public MStock addTradeToStock(String symbol,MTrade mTrade){
		MStock stock = findBySymbol(symbol);
		stock.getStockTrades().add(mTrade);
		if(null == mTrade.getTradedStock()){
			mTrade.setTradedStock(stock);
		}
		return stock;
	}
	
	public MStock deleteTradeToStock(String symbol,MTrade mTrade){
		MStock stock = findBySymbol(symbol);
		stock.getStockTrades().remove(mTrade);
		return stock;
	}
	
	public BigDecimal calcVolumeWeightedStockPriceWithin15Mins(String symbol){
		return findBySymbol(symbol).calcVolumeWeightedStockPriceWithin15Mins();
	}
	
	public List<MTrade> getAllTradesWithin15Mins(String symbol){
		return findBySymbol(symbol).getTradesWithin15Mins();
	}
}
