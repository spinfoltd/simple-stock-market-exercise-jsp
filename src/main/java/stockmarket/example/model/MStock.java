package stockmarket.example.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import stockmarket.example.consts.StockConstants;
import stockmarket.example.consts.StockConstants.MBigDecimalScale;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MStock {
	private String symbol;
	private MStockTypeEnum type;
	private BigDecimal lastDivident;
	private BigDecimal fixedDivident;
	private BigDecimal parValue;

	public MStock(String symbol, MStockTypeEnum type, BigDecimal lastDivident,
			BigDecimal fixedDivident, BigDecimal parValue) {
		super();
		this.symbol = symbol;
		this.type = type;
		this.lastDivident = (null != lastDivident)?MBigDecimalScale.setScale(lastDivident):lastDivident;
		if(null != fixedDivident){
			this.fixedDivident = MBigDecimalScale.mDivideWithScale(
					MBigDecimalScale.setScale(fixedDivident)
					, MBigDecimalScale.setScale(new BigDecimal(100)));
		}else{
			this.fixedDivident = fixedDivident;
		}
		this.parValue = (null != parValue)?MBigDecimalScale.setScale(parValue):parValue;
		
	}

	//@OneToMany
	@JsonIgnore
	private List<MTrade> stockTrades = new ArrayList<MTrade>();
	
	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public MStockTypeEnum getType() {
		return type;
	}

	public BigDecimal getParValue() {
		return parValue;
	}

	public void setType(MStockTypeEnum type) {
		this.type = type;
	}

	public BigDecimal getLastDivident() {
		return lastDivident;
	}

	public void setLastDivident(BigDecimal lastDivident) {
		this.lastDivident = lastDivident;
	}

	public BigDecimal getFixedDivident() {
		return fixedDivident;
	}

	public void setFixedDivident(BigDecimal fixedDivident) {
		this.fixedDivident = fixedDivident;
	}

	public List<MTrade> getStockTrades() {
		return stockTrades;
	}

	public void setStockTrades(List<MTrade> stockTrades) {
		this.stockTrades = stockTrades;
	}
	
	
	
	public BigDecimal calculateDividend(BigDecimal givenPrice){
		givenPrice = StockConstants.MBigDecimalScale.setScale(givenPrice);
		if(type == MStockTypeEnum.COMMON){
			return MBigDecimalScale.mDivideWithScale(this.getLastDivident(),givenPrice);
		}else{
			return MBigDecimalScale.mDivideWithScale(this.getFixedDivident().multiply(this.getParValue()),givenPrice);
		}
	}
	
	public BigDecimal calculatePERatio(BigDecimal givenPrice){
		givenPrice = StockConstants.MBigDecimalScale.setScale(givenPrice);
		if(type == MStockTypeEnum.COMMON){
			if(null != lastDivident && (lastDivident.compareTo(BigDecimal.ZERO) ==0) ){
				return BigDecimal.ZERO; 
			}else{
				return MBigDecimalScale.mDivideWithScale(givenPrice,this.getLastDivident());
			}
			
		}else{
			return MBigDecimalScale.mDivideWithScale(givenPrice,this.getFixedDivident().multiply(this.getParValue()));
		}
		//return MBigDecimalScale.mDivideWithScale(calculateDividend(givenPrice),givenPrice);
	}
	
	public List<MTrade> getTradesWithin15Mins(){
		List<MTrade> allTrades = new ArrayList<MTrade>();
		for(MTrade trade : getStockTrades()){
			if(trade.isTradeTimeWithin15Mins()){
				allTrades.add(trade);
			}
		}
		return allTrades;
	}
	
	public MTrade recordTrade(Date tradeTime, BuySellIndicatorEnum buySell, Integer quantitiy,BigDecimal price){
		price = StockConstants.MBigDecimalScale.setScale(price);
		MTrade newTrade = new MTrade(tradeTime,buySell, quantitiy, price,this);
		stockTrades.add(newTrade);
		return newTrade;
	}
	
	public static List<String> getAllStockSymbols(List<MStock> stocks){
		List<String> symbls = new ArrayList<String>();
		for(MStock stock : stocks){
			symbls.add(stock.symbol);
		}
		return symbls;
	}
	
	public Boolean isThisStock(String symbl){
		return this.getSymbol().equalsIgnoreCase(symbl);
	}
	
	public BigDecimal calcSumAllTradesPriceMultQuantWithin15Mins(){
		BigDecimal result = BigDecimal.ZERO;
		for(MTrade trade : getTradesWithin15Mins()){
			result = result.add(trade.getTradePriceMultQuant());
		}
		return result;
	}
	
	public BigDecimal calcSumAllTradesQuantWithin15Mins(){
		BigDecimal result = BigDecimal.ZERO;
		for(MTrade trade : getTradesWithin15Mins()){
			result = result.add(trade.getQuantitiyBD());
		}
		return result;
	}
	
	@JsonIgnore
	public BigDecimal calcVolumeWeightedStockPriceWithin15Mins(){
		if(calcSumAllTradesQuantWithin15Mins().compareTo(BigDecimal.ZERO) ==0){
			return BigDecimal.ZERO;
		}
		return MBigDecimalScale.mDivideWithScale(calcSumAllTradesPriceMultQuantWithin15Mins(),(calcSumAllTradesQuantWithin15Mins()));
	}
	
}
