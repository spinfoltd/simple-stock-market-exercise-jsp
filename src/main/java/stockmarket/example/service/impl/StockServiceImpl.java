package stockmarket.example.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.codehaus.jackson.map.introspect.BasicClassIntrospector.GetterMethodFilter;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import stockmarket.example.consts.StockConstants;
import stockmarket.example.dao.MStockDao;
import stockmarket.example.dao.MTradeDao;
import stockmarket.example.model.BuySellIndicatorEnum;
import stockmarket.example.model.MStock;
import stockmarket.example.model.MTrade;
import stockmarket.example.model.PriceInvalidException;
import stockmarket.example.model.StockNotFoundException;
import stockmarket.example.util.MStringUtil;

public class StockServiceImpl {

	@Inject
	MStockDao stockDao;

	@Inject
	MTradeDao tradeDao;

	private MStock getStock(String symbol) throws StockNotFoundException{
		MStock stock =stockDao.findBySymbol(symbol); 
		if(null == stock){
			throw new StockNotFoundException(symbol);
		}
		return stock;

	}

	public Boolean validatePrice(BigDecimal price) throws PriceInvalidException{
		if(null == price){
			throw new PriceInvalidException("NULL");
		}
		if(price.equals(BigDecimal.ZERO)){
			throw new PriceInvalidException("ZERO");
		}
		return true;
	}

	public BigDecimal calculateDividendYield(String symbol, BigDecimal givenPrice) throws StockNotFoundException, PriceInvalidException{
		validatePrice(givenPrice);
		MStock stock = getStock(symbol);
		return stock.calculateDividend(givenPrice);
	}

	public BigDecimal calculatPrRatio(String symbol,BigDecimal givenPrice) throws StockNotFoundException{
		MStock stock = getStock(symbol);
		return stock.calculatePERatio(givenPrice);
	}

	public MTrade recordTrade(String symbol, Date tradeTime, String buySell, Integer quantitiy,BigDecimal price)
			throws StockNotFoundException{
		MStock stock = getStock(symbol);
		MTrade newTrade = new MTrade(tradeTime,BuySellIndicatorEnum.valueOf(buySell), quantitiy, price, stock);
		stockDao.addTradeToStock(symbol, newTrade);
		return newTrade;
	}

	public List<MTrade> getAllTradesWithin15Mins(){
		return tradeDao.getAllTradesWithin15Mins();
	}

	public List<String> getAllStkSymblWithDesc(){
		return MStock.getAllStockSymbols(stockDao.getAllStocks());
	}

	public String  getAvailableStocksStrings(int tab) throws JsonGenerationException, JsonMappingException, IOException{
		StringBuffer sb = new StringBuffer();
		for(MStock stock :stockDao.getAllStocks()){
			sb.append(MStringUtil.getStringWithTabOnNewLine(tab,MStringUtil.returnJsonStringFromObject(stock)));
		}
		return sb.toString();
	}

	public String  getRecordedTradesStrings(int tab) throws JsonGenerationException, JsonMappingException, IOException{
		StringBuffer sb = new StringBuffer();
		for(MTrade trade :tradeDao.getAllTrades()){
			sb.append(MStringUtil.getStringWithTabOnNewLine(tab,MStringUtil.returnJsonStringFromObject(trade)));
		}
		return sb.toString();
	}

	public BigDecimal calcVolumeWeightedStockPriceWithin15Mins(String symbol){
		return stockDao.calcVolumeWeightedStockPriceWithin15Mins(symbol);
	}

	public BigDecimal calculateGBCE(){
		BigDecimal result = BigDecimal.ONE;
		if(tradeDao.getAllTrades().size() ==0){
			return BigDecimal.ZERO;
		}
		else{
			for(MTrade trade: tradeDao.getAllTrades()){
				result = result.multiply(trade.getPrice());
			};
			double tResult =Math.pow(
					result.doubleValue(),
					StockConstants.MBigDecimalScale.mDivideWithScaleUP(BigDecimal.ONE, new BigDecimal(tradeDao.getAllTrades().size())).doubleValue()
					);
			 
			return StockConstants.MBigDecimalScale.setScale(new BigDecimal(tResult));


		}
	}
}
