package stockmarket.example.service.impl;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import stockmarket.example.consts.StockConstants;
import stockmarket.example.dao.BaseSpringTestConfig;
import stockmarket.example.model.MStock;
import stockmarket.example.model.MStockTypeEnum;
import stockmarket.example.model.MTrade;
import stockmarket.example.model.PriceInvalidException;
import stockmarket.example.model.StockNotFoundException;

public class StockServiceImplTest extends BaseSpringTestConfig{


	@Test
	public void testCalculateDividendYield() throws StockNotFoundException, PriceInvalidException {
		
		Assert.assertTrue(stockService.calculateDividendYield("TEA", new BigDecimal(2)).compareTo(BigDecimal.ZERO)==0);
		Assert.assertTrue(stockService.calculateDividendYield("POP", new BigDecimal(16)).compareTo(new BigDecimal(".5"))==0);
		BigDecimal retVal = stockDao.findBySymbol("GIN").getFixedDivident();
		Assert.assertTrue(retVal.compareTo(new BigDecimal("0.02"))==0);
		Assert.assertTrue(stockService.calculateDividendYield("GIN", new BigDecimal(4)).compareTo(new BigDecimal(".5"))==0);
	}

	@Test
	public void testCalculatPrRatio() throws StockNotFoundException {
		Assert.assertTrue(stockService.calculatPrRatio("TEA", new BigDecimal(2)).compareTo(BigDecimal.ZERO)==0);
		Assert.assertTrue(stockService.calculatPrRatio("POP", new BigDecimal(16)).compareTo(new BigDecimal(2))==0);
		Assert.assertTrue(stockService.calculatPrRatio("GIN", new BigDecimal(8)).compareTo(new BigDecimal("4"))==0);
	}	
	

	@Test
	public void testCalcVolumeWeightedStockPriceWithin15Mins() throws JsonGenerationException, JsonMappingException, IOException, StockNotFoundException {
		String tradeSyml = "TEA";
		MStock mstock = stockDao.findBySymbol(tradeSyml);
		Assert.assertNotNull(mstock);
		Assert.assertTrue(mstock.getStockTrades().size()==0);
		{
			MTrade recTrade = stockService.recordTrade("TEA",new Date(), "BUY", 2, new BigDecimal(10));
			Assert.assertTrue(recTrade.getTradedStock() == mstock);
			Assert.assertTrue(mstock.getStockTrades().size()==1);		
			Assert.assertTrue(tradeDao.getAllTrades().size() == 1);
			Assert.assertTrue(recTrade.getTradePriceMultQuant().compareTo(new BigDecimal(20))==0);
			Assert.assertTrue(stockService.calcVolumeWeightedStockPriceWithin15Mins(tradeSyml).compareTo(new BigDecimal(10))==0);
			Assert.assertTrue(stockService.calculateGBCE().compareTo(new BigDecimal(10))<=0);
		}
		{
			MTrade recTrade = stockService.recordTrade("TEA",new Date(), "BUY", 2, new BigDecimal(10));
			Assert.assertTrue(recTrade.getTradedStock() == mstock);
			Assert.assertTrue(mstock.getStockTrades().size()==2);		
			Assert.assertTrue(tradeDao.getAllTrades().size() == 2);
			Assert.assertTrue(recTrade.getTradePriceMultQuant().compareTo(new BigDecimal(20))==0);
			Assert.assertTrue(stockService.calcVolumeWeightedStockPriceWithin15Mins(tradeSyml).compareTo(new BigDecimal(10))==0);
			System.out.println("XXXX:"+
					Math.pow(StockConstants.MBigDecimalScale.setScale(new BigDecimal(100)).doubleValue(), StockConstants.MBigDecimalScale.mDivideWithScale(BigDecimal.ONE,new BigDecimal(2)).doubleValue())
					);
			System.out.println(new BigDecimal(1).divide(new BigDecimal(2)));
			System.out.println(Math.pow(100d, StockConstants.MBigDecimalScale.mDivideWithScaleUP(BigDecimal.ONE,new BigDecimal(2)).doubleValue()));
			System.out.println(Math.pow(100d, StockConstants.MBigDecimalScale.mDivideWithScaleUP(BigDecimal.ONE,new BigDecimal(3)).doubleValue()));
			System.out.println(Math.pow(1000d, StockConstants.MBigDecimalScale.mDivideWithScale(BigDecimal.ONE,new BigDecimal(3)).doubleValue()));
			System.out.println(Math.pow(new BigDecimal(1000).doubleValue(), StockConstants.MBigDecimalScale.mDivideWithScale(BigDecimal.ONE,new BigDecimal(3)).doubleValue()));
			System.out.println("YYY:"+stockService.calculateGBCE());
			Assert.assertTrue(stockService.calculateGBCE().compareTo(new BigDecimal(10))<=0);
		}
		{
			MTrade recTrade = stockService.recordTrade("TEA",new Date(), "BUY", 4, new BigDecimal(10));
			Assert.assertTrue(recTrade.getTradedStock() == mstock);
			Assert.assertTrue(mstock.getStockTrades().size()==3);		
			Assert.assertTrue(tradeDao.getAllTrades().size() == 3);
			Assert.assertTrue(recTrade.getTradePriceMultQuant().compareTo(new BigDecimal(40))==0);
			Assert.assertTrue(mstock.calcSumAllTradesPriceMultQuantWithin15Mins().compareTo(new BigDecimal(80))==0);
			Assert.assertTrue(stockService.calcVolumeWeightedStockPriceWithin15Mins(tradeSyml).compareTo(new BigDecimal(10))==0);
			System.out.println("YYY:"+stockService.calculateGBCE());
			//Assert.assertTrue(stockService.calculateGBCE().compareTo(new BigDecimal(10))<=0);
		}
		{
			MTrade recTrade = stockService.recordTrade("TEA",getDateBefore15Mins(), "BUY", 8, new BigDecimal(10));
			Assert.assertTrue(recTrade.getTradedStock() == mstock);
			Assert.assertTrue(mstock.getTradesWithin15Mins().size()==3);		
			Assert.assertTrue(tradeDao.getAllTrades().size() == 4);
			Assert.assertTrue(recTrade.getTradePriceMultQuant().compareTo(new BigDecimal(80))==0);
			Assert.assertTrue(mstock.calcSumAllTradesPriceMultQuantWithin15Mins().compareTo(new BigDecimal(80))==0);
			Assert.assertTrue(stockService.calcVolumeWeightedStockPriceWithin15Mins(tradeSyml).compareTo(new BigDecimal(10))==0);
			System.out.println("YYY:"+stockService.calculateGBCE());
			//Assert.assertTrue(stockService.calculateGBCE().compareTo(new BigDecimal(10))<=0);
		}
	}
	
	

}
