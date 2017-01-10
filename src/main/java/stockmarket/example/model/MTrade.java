package stockmarket.example.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import stockmarket.example.consts.StockConstants;

@XmlAccessorType(XmlAccessType.FIELD)
public class MTrade {
	
	private Timestamp tradeTime;
	private Integer quantitiy;
	private BuySellIndicatorEnum buySell;
	private BigDecimal price;
	
	//@ManyToOne
	@JsonIgnore
	private MStock tradedStock;
	
	
	public MTrade() {
		super();
	}

	public MTrade(Timestamp tradeTime, BuySellIndicatorEnum buySell, Integer quantitiy,
			BigDecimal price) {
		super();
		this.tradeTime = tradeTime;
		this.buySell = buySell;
		this.quantitiy = quantitiy;
		this.price = (price!=null)?StockConstants.MBigDecimalScale.setScale(price):price;
	}
	
	public MTrade(Timestamp tradeTime, BuySellIndicatorEnum buySell, Integer quantitiy,
			BigDecimal price,MStock tradedStock) {
		this(tradeTime,buySell,quantitiy,price);
		this.tradedStock = tradedStock;
	}
	
	public MTrade(Date tradeTime, BuySellIndicatorEnum buySell, Integer quantitiy,
			BigDecimal price) {
		this(new Timestamp(tradeTime.getTime()),buySell,quantitiy,price);
	}
	
	public MTrade(Date tradeTime, BuySellIndicatorEnum buySell, Integer quantitiy,
			BigDecimal price,MStock tradedStock) {
		this(new Timestamp(tradeTime.getTime()),buySell,quantitiy,price,tradedStock);
	}
	
	@JsonIgnore
	public Timestamp getTradeTime() {
		return tradeTime;
	}
	
	public void setTradeTime(Timestamp tradeTime) {
		this.tradeTime = tradeTime;
	}
	
	public Integer getQuantitiy() {
		return quantitiy;
	}
	
	public BigDecimal getQuantitiyBD() {
		return StockConstants.MBigDecimalScale.setScale(new BigDecimal(getQuantitiy()));
	}

	public void setQuantitiy(Integer quantitiy) {
		this.quantitiy = quantitiy;
	}
	
	public BuySellIndicatorEnum getBuySell() {
		return buySell;
	}

	public void setBuySell(BuySellIndicatorEnum buySell) {
		this.buySell = buySell;
	}
	
	public BigDecimal getPrice() {
		return price;
	}
	
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public MStock getTradedStock() {
		return tradedStock;
	}

	public void setTradedStock(MStock tradedStock) {
		this.tradedStock = tradedStock;
	}

	@JsonIgnore
	public Timestamp getNowTimeStamp(){
		return new Timestamp(System.currentTimeMillis());
	}
	
	public Timestamp getTimeStampWithinMinutes(int minutes){
		Date nowDate = new Date();
		nowDate.setMinutes(nowDate.getMinutes() - minutes);
		return (new Timestamp(nowDate.getTime()));
		
	}
	
	@JsonIgnore
	public Timestamp getTimeStampWithin15Mins(){
		return getTimeStampWithinMinutes(15);
	}
	
	public Boolean isTradeTimeWithin15Mins(){
		return (this.getTradeTime().after(getTimeStampWithin15Mins()));
	}
	
	public String getTradeTimeInDateFormat(){
		return StockConstants.DateFormat.simplDateFormat.format(new Date(getTradeTime().getTime()));
	}
	
	public BigDecimal getTradePriceMultQuant(){
		return price.multiply(getQuantitiyBD());
	}
}
