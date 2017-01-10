package stockmarket.example.dao;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import stockmarket.example.model.MStock;
import stockmarket.example.model.MTrade;
import stockmarket.example.model.StockNotFoundException;

public class MStockDaoTest extends  BaseSpringTestConfig{

	@Test
	public void testFindBySymbol() {
		for(MStock stck : stocks){
			Assert.assertNotNull(stockDao.findBySymbol(stck.getSymbol()));
			Assert.assertEquals(stck, stockDao.findBySymbol(stck.getSymbol()));
		}
	}

	@Test
	public void testGetTradesForStock() throws StockNotFoundException {
		for(MStock stck : stocks){
			assertNoTrades(stck);
			MTrade newTrade = createNewTradeNow(stck.getSymbol());
			stockDao.addTradeToStock(stck.getSymbol(),newTrade);
			assertOneTrades(stck);
			stockDao.deleteTradeToStock(stck.getSymbol(),newTrade);
			assertNoTrades(stck);
		}
	}

	@Test
	public void testGetAllTrades() throws StockNotFoundException {
		for(MStock stck : stocks){
			Assert.assertTrue(stockDao.getAllTrades().size()==0);
			MTrade newTrade = createNewTradeNow(stck.getSymbol());
			stockDao.addTradeToStock(stck.getSymbol(),newTrade);
			Assert.assertTrue(stockDao.getAllTrades().size()==1);
			stockDao.deleteTradeToStock(stck.getSymbol(), newTrade);
			Assert.assertTrue(stockDao.getAllTrades().size()==0);
		}
	}

	@Test
	public void testGetAllStocks() {
		Assert.assertTrue(stockDao.getAllStocks().size() == stocks.size());
	}

	@Test
	public void testAddTradeToStock() throws StockNotFoundException {
		for(MStock stck : stocks){
			Assert.assertTrue(stockDao.getAllTrades().size()==0);
			MTrade newTrade = createNewTradeNow(stck.getSymbol());
			stockDao.addTradeToStock(stck.getSymbol(),newTrade);
			Assert.assertTrue(stockDao.getAllTrades().size()==1);
			stockDao.deleteTradeToStock(stck.getSymbol(), newTrade);
			Assert.assertTrue(stockDao.getAllTrades().size()==0);
		}
	}

	@Test
	public void testCalcSumAllTradesPriceMultQuantWithin15Mins() throws StockNotFoundException {
		
		MStock stck = stocks.get(0);
		{
			BigDecimal firstTradeQuant = new BigDecimal(10);
			BigDecimal firstTradePrice = new BigDecimal(10);
			MTrade newTrade = createNewTradeNow(stck.getSymbol(),firstTradeQuant.intValue(), firstTradePrice);
			stockDao.addTradeToStock(stck.getSymbol(),newTrade);
			Assert.assertTrue(stockDao.findBySymbol(stck.getSymbol()).calcSumAllTradesQuantWithin15Mins().compareTo(firstTradeQuant)==0);
			Assert.assertTrue(stockDao.findBySymbol(stck.getSymbol()).calcSumAllTradesPriceMultQuantWithin15Mins().compareTo(firstTradePrice.multiply(firstTradeQuant))==0);
			Assert.assertTrue(stockDao.findBySymbol(stck.getSymbol()).calcVolumeWeightedStockPriceWithin15Mins().compareTo(new BigDecimal(10))==0);
			
		}
		{
			BigDecimal firstTradeQuant = new BigDecimal(10);
			BigDecimal firstTradePrice = new BigDecimal(10);
			MTrade newTrade = createNewTradeNow(stck.getSymbol(),firstTradeQuant.intValue(), firstTradePrice);
			stockDao.addTradeToStock(stck.getSymbol(),newTrade);
			Assert.assertTrue(stockDao.findBySymbol(stck.getSymbol()).calcSumAllTradesQuantWithin15Mins().compareTo(new BigDecimal(20))==0);
			Assert.assertTrue(stockDao.findBySymbol(stck.getSymbol()).calcSumAllTradesPriceMultQuantWithin15Mins().compareTo(new BigDecimal(200)) == 0);
			Assert.assertTrue(stockDao.findBySymbol(stck.getSymbol()).calcVolumeWeightedStockPriceWithin15Mins().compareTo(new BigDecimal(10))==0);
		}
		{
			BigDecimal firstTradeQuant = new BigDecimal(5);
			BigDecimal firstTradePrice = new BigDecimal(15);
			MTrade newTrade = createNewTradeNow(stck.getSymbol(),firstTradeQuant.intValue(), firstTradePrice);
			stockDao.addTradeToStock(stck.getSymbol(),newTrade);
			Assert.assertTrue(stockDao.findBySymbol(stck.getSymbol()).calcSumAllTradesQuantWithin15Mins().compareTo(new BigDecimal(25))==0);
			Assert.assertTrue(stockDao.findBySymbol(stck.getSymbol()).calcSumAllTradesPriceMultQuantWithin15Mins().compareTo(new BigDecimal(275))==0);
			Assert.assertTrue(stockDao.findBySymbol(stck.getSymbol()).calcVolumeWeightedStockPriceWithin15Mins().compareTo(new BigDecimal(11))==0);
		}
		cleanUp();
	}

}
