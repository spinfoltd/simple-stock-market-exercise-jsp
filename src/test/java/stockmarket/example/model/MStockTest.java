package stockmarket.example.model;

import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;

import stockmarket.example.consts.StockConstants;
import stockmarket.example.dao.BaseTestConfig;


public class MStockTest extends BaseTestConfig{
	
	@Mock
	MStock stock;
	
	@Test
	public void testMDivideWithScale() {
		System.out.println(StockConstants.MBigDecimalScale.mDivideWithScale(new BigDecimal(2), new BigDecimal(2)));
		System.out.println( (new BigDecimal(2)).divide(new BigDecimal(1), BigDecimal.ROUND_HALF_DOWN));
		Assert.assertTrue(StockConstants.MBigDecimalScale.mDivideWithScale(new BigDecimal(2), new BigDecimal(2)).compareTo(BigDecimal.ONE)==0);
		Assert.assertTrue(StockConstants.MBigDecimalScale.mDivideWithScale(new BigDecimal(2), new BigDecimal(1.000)).compareTo(new BigDecimal(2))==0);
		Assert.assertEquals(StockConstants.MBigDecimalScale.mDivideWithScale(null, new BigDecimal(2)),null);
	}

	@Test
	public void testCalculateDividend() {
		{
			MStock stock1 = new MStock("TTT", MStockTypeEnum.COMMON, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE);
			System.out.println(stock1.calculateDividend(new BigDecimal(2)));
			Assert.assertTrue(stock1.calculateDividend(new BigDecimal(2)).compareTo(BigDecimal.ZERO)==1);
		}
		{
			MStock stock1 = new MStock("TTT", MStockTypeEnum.COMMON,new BigDecimal(2),new BigDecimal(2), new BigDecimal(2));
			System.out.println(stock1.calculateDividend(new BigDecimal(2)));
			Assert.assertTrue(stock1.calculateDividend(new BigDecimal(2)).compareTo(BigDecimal.ONE)==0);
		}
		{
			MStock stock1 = new MStock("TTT", MStockTypeEnum.PREFERRED,new BigDecimal(2),new BigDecimal(2), new BigDecimal(100));
			System.out.println(stock1.calculateDividend(new BigDecimal(2)));
			Assert.assertTrue(stock1.calculateDividend(new BigDecimal(2)).compareTo(new BigDecimal(1))==0);
		}
	}

	@Test
	public void testCalculatePERatio() {
		{
			MStock stock1 = new MStock("TTT", MStockTypeEnum.COMMON, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE);
			System.out.println(stock1.calculatePERatio(new BigDecimal(2)));
			Assert.assertTrue(stock1.calculatePERatio(new BigDecimal(2)).compareTo(new BigDecimal(2))==0);
		}
		{
			MStock stock1 = new MStock("TTT", MStockTypeEnum.COMMON,new BigDecimal(2),new BigDecimal(2), new BigDecimal(2));
			System.out.println(stock1.calculatePERatio(new BigDecimal(2)));
			Assert.assertTrue(stock1.calculatePERatio(new BigDecimal(2)).compareTo(BigDecimal.ONE)==0);
		}
		{
			MStock stock1 = new MStock("TTT", MStockTypeEnum.PREFERRED,new BigDecimal(2),new BigDecimal(2), new BigDecimal(100));
			System.out.println(stock1.calculatePERatio(new BigDecimal(4)));
			Assert.assertTrue(stock1.calculatePERatio(new BigDecimal(4)).compareTo(new BigDecimal(2))==0);
		}
	}

	@Test
	public void testRecordTrade() throws StockNotFoundException {
		{
			MTrade trade1 = createNewTradeNow("TTT",2,new BigDecimal(2));
			MStock stock1 = new MStock("TTT", MStockTypeEnum.PREFERRED,new BigDecimal(2),new BigDecimal(2), new BigDecimal(2));
			stock1.getStockTrades().add(trade1);
			Assert.assertTrue(stock1.calcSumAllTradesPriceMultQuantWithin15Mins().compareTo(new BigDecimal(4)) ==0);
			Assert.assertTrue(stock1.calcSumAllTradesQuantWithin15Mins().compareTo(new BigDecimal(2)) ==0);
			Assert.assertTrue(stock1.calcVolumeWeightedStockPriceWithin15Mins().compareTo(new BigDecimal(2)) ==0);
		}
		{
			MTrade trade1 = createNewTradeNow("TTT",2,new BigDecimal(2));
			MTrade trade2 = createNewTradeNow("TTT",2,new BigDecimal(4));
			MStock stock1 = new MStock("TTT", MStockTypeEnum.PREFERRED,new BigDecimal(2),new BigDecimal(2), new BigDecimal(2));
			stock1.getStockTrades().add(trade1);
			stock1.getStockTrades().add(trade2);
			Assert.assertTrue(stock1.calcSumAllTradesPriceMultQuantWithin15Mins().compareTo(new BigDecimal(12)) ==0);
			Assert.assertTrue(stock1.calcSumAllTradesQuantWithin15Mins().compareTo(new BigDecimal(4)) ==0);
			Assert.assertTrue(stock1.calcVolumeWeightedStockPriceWithin15Mins().compareTo(new BigDecimal(3)) ==0);
			
		}
	}
}
