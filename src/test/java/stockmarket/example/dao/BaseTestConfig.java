package stockmarket.example.dao;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import stockmarket.example.model.BuySellIndicatorEnum;
import stockmarket.example.model.MStock;
import stockmarket.example.model.MTrade;
import stockmarket.example.model.StockNotFoundException;

public class BaseTestConfig {
	
	public static final Integer quantityConst = 33;
	public static final BigDecimal stockPricConst = new BigDecimal(100);
	
	
	public MTrade createNewTradeNow(String stockSymbl) throws StockNotFoundException{
		MTrade recTrade =new MTrade(new Date(), BuySellIndicatorEnum.BUY, quantityConst, stockPricConst);
		return recTrade;
	}
	
	public MTrade createNewTradeNow(String stockSymbl, int quantity,  BigDecimal stockPrice) throws StockNotFoundException{
		MTrade recTrade =new MTrade(new Date(), BuySellIndicatorEnum.BUY, quantity, stockPrice);
		return recTrade;
	}
	public Date getDateBefore15Mins(){
		Date d = Calendar.getInstance().getTime();
		d.setMinutes(d.getMinutes()-16);
		return d;
	}
	
	public MTrade createNewTradeBefore15Mins(String stockSymbl) throws StockNotFoundException{
		MTrade recTrade =new MTrade(getDateBefore15Mins(), BuySellIndicatorEnum.BUY, quantityConst, stockPricConst);
		return recTrade;
	}

}
