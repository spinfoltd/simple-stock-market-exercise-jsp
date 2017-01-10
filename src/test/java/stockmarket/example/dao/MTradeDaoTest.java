package stockmarket.example.dao;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import stockmarket.example.model.MStock;
import stockmarket.example.model.MTrade;
import stockmarket.example.model.StockNotFoundException;

public class MTradeDaoTest extends BaseSpringTestConfig{

	@Test
	public void testGetAllTrades() throws StockNotFoundException {
		for(MStock stck : stocks){
			Assert.assertTrue(tradeDao.getAllTrades().size()==0);
			MTrade newTrade = createNewTradeNow(stck.getSymbol());
			stockDao.addTradeToStock(stck.getSymbol(),newTrade);
			Assert.assertTrue(tradeDao.getAllTrades().size()==1);
			stockDao.deleteTradeToStock(stck.getSymbol(), newTrade);
			Assert.assertTrue(tradeDao.getAllTrades().size()==0);
		}
	}
	
	@Test
	public void testGetAllTradesWithin15Mins() throws StockNotFoundException {
		for(MStock stck : stocks){
			MTrade newTrade = createNewTradeNow(stck.getSymbol());
			MTrade newTradeB4_15mins = createNewTradeBefore15Mins(stck.getSymbol());
			{
			Assert.assertTrue(tradeDao.getAllTradesWithin15Mins().size()==0);
			stockDao.addTradeToStock(stck.getSymbol(),newTrade);
			Assert.assertTrue(tradeDao.getAllTradesWithin15Mins().size()==1);
			}
			{
			stockDao.addTradeToStock(stck.getSymbol(),newTradeB4_15mins);
			Assert.assertTrue(tradeDao.getAllTrades().size()==2);
			Assert.assertTrue(tradeDao.getAllTradesWithin15Mins().size()==1);
			}
			stockDao.deleteTradeToStock(stck.getSymbol(), newTrade);
			stockDao.deleteTradeToStock(stck.getSymbol(), newTradeB4_15mins);
			Assert.assertTrue(tradeDao.getAllTrades().size()==0);
		}
	}

}
