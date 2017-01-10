package stockmarket.example.sprgs.config;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import stockmarket.example.dao.MStockDao;
import stockmarket.example.dao.MTradeDao;
import stockmarket.example.model.MStock;
import stockmarket.example.model.MTrade;
import stockmarket.example.model.StockNotFoundException;
import stockmarket.example.model.StocksRepo;
import stockmarket.example.service.impl.StockServiceImpl;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={StocksConfig.class})
public class StocksConfigTest {
	@Autowired
	StocksRepo stocksRepo;

	@Autowired
	MStockDao stockDao;

	@Autowired
	MTradeDao tradeDao;

	@Autowired
	StockServiceImpl stockService;
	/*@Resource
	public  List<MStock> stocks;*/

	@Test
	public void testConfig() {
		Assert.assertNotNull(stocksRepo);
		Assert.assertTrue(stocksRepo.getStocks().size()>0);
		Assert.assertNotNull(stockDao);
		Assert.assertTrue(stockDao.getStocksRepo().getStocks().size()>0);
		Assert.assertNull(stockDao.findBySymbol("teas"));
		stockDao.getStocksRepo().getStocks().add(StocksConfig.createNewStock("TEAs ,Common ,0,,100"));
		Assert.assertTrue(stockDao.getStocksRepo().getStocks().size()>0);
		Assert.assertNotNull(stockDao.findBySymbol("teas"));
		Assert.assertNotNull(stockService);
		Assert.assertNotNull(stockService.getAllStkSymblWithDesc().size()>0);
		//Assert.assertNotNull(stocks);
	}
	

	@Test
	public void testRecordTrade() throws StockNotFoundException, JsonGenerationException, JsonMappingException, IOException{
		Assert.assertNotNull(tradeDao);
		Assert.assertTrue(tradeDao.getAllTrades().size() == 0);
		System.out.println(stockService.getAvailableStocksStrings(5));
		String tradeSyml = "TEA";
		MStock mstock = stockDao.findBySymbol(tradeSyml);
		Assert.assertNotNull(mstock);
		Assert.assertTrue(mstock.getStockTrades().size()==0);
		MTrade recTrade = stockService.recordTrade("TEA",new Date(), "BUY", 33, BigDecimal.ONE);
		Assert.assertTrue(recTrade.getTradedStock() == mstock);
		Assert.assertTrue(mstock.getStockTrades().size()==1);
		Assert.assertTrue(tradeDao.getAllTrades().size() == 1);
		System.out.println(stockService.getRecordedTradesStrings(5));
	}

}
