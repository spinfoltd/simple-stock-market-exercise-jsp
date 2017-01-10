package stockmarket.example.dao;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import stockmarket.example.model.BuySellIndicatorEnum;
import stockmarket.example.model.MStock;
import stockmarket.example.model.MTrade;
import stockmarket.example.model.StockNotFoundException;
import stockmarket.example.model.StocksRepo;
import stockmarket.example.service.impl.StockServiceImpl;
import stockmarket.example.sprgs.config.StocksConfig;
import stockmarket.example.uifacade.input.StockSampleInputPrompts;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={StocksConfig.class})
public class BaseSpringTestConfig extends BaseTestConfig{
	
	@Autowired
	protected StocksRepo stocksRepo;
	
	@Autowired
	protected MStockDao stockDao;
	
	@Autowired
	protected MTradeDao tradeDao;
	
	@Autowired
	protected StockServiceImpl stockService;
	
	@Autowired
	protected StockSampleInputPrompts stockSampleInputPrompts;
	
	@Resource
	protected List<MStock> stocks;
	
	@Test
	public void testInitialise(){
		Assert.assertNotNull(stocks);
		Assert.assertNotNull(stockDao);
		Assert.assertNotNull(tradeDao);
		Assert.assertNotNull(stockService);
		Assert.assertNotNull(stocksRepo);
		Assert.assertNotNull(stockSampleInputPrompts);
	}
	
	@Before @After
	public void cleanEnv(){
		cleanUp();
		Assert.assertTrue(stocks.size()>1);
		for(MStock stk :stocks){
			assertNoTrades(stockDao.findBySymbol(stk.getSymbol()));
		}
	}	
	 
	public void cleanUp(){
			Assert.assertTrue(stocks.size()>1);
			for(MTrade stk :tradeDao.getAllTrades()){
				stockDao.deleteTradeToStock(stk.getTradedStock().getSymbol(), stk);
			}
		}
	
	protected void assertTradeCount(MStock stck, int count){
		Assert.assertTrue(stockDao.getTradesForStock(stck.getSymbol()).size() == count);
	}
	
	protected void assertNoTrades(MStock stck){
		assertTradeCount(stck,0);
	}
	protected void assertOneTrades(MStock stck){
		assertTradeCount(stck,1);
	}
	
	
}
