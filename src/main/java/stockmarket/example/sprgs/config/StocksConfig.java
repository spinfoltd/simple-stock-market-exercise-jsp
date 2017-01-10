package stockmarket.example.sprgs.config;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import stockmarket.example.dao.MStockDao;
import stockmarket.example.dao.MTradeDao;
import stockmarket.example.model.MStock;
import stockmarket.example.model.MStockTypeEnum;
import stockmarket.example.model.StocksRepo;
import stockmarket.example.service.impl.StockServiceImpl;
import stockmarket.example.uifacade.input.StockSampleInputPrompts;

@Configuration
public class StocksConfig {

	public static  MStock createNewStock(String symbol, String stockType, Float lastDivident, Float fixedDivident, Float parValue){
		MStock stock = new MStock(symbol,MStockTypeEnum.fromValue(stockType),new BigDecimal(lastDivident), 
				(fixedDivident!=null? new BigDecimal(fixedDivident): null)
		,new BigDecimal(parValue));
		return stock;
	}
	public static MStock createNewStock(String symbolColonized){
		String[] vals = symbolColonized.split(",");
		Float fixedDivident = (StringUtils.isEmpty(vals[3])?null:(Float.valueOf(vals[3].trim())));
		return createNewStock(vals[0].trim(),vals[1].trim(),Float.valueOf(vals[2].trim()), fixedDivident,Float.valueOf(vals[4].trim()));
	}
	
	@Bean
	public List<MStock> stocks(){
		List<MStock> allStocks = new ArrayList<MStock>();
		allStocks.add(createNewStock("TEA ,Common ,0,,100"));
		allStocks.add(createNewStock("POP ,Common ,8,,100"));
		allStocks.add(createNewStock("ALE ,Common ,23 ,,60"));
		allStocks.add(createNewStock("GIN ,Preferred ,8 ,2 ,100"));
		allStocks.add(createNewStock("JOE, Common ,13 ,,250"));
		return allStocks;
	}
	
	@Bean
	public StocksRepo stocksRepo(){
		StocksRepo allStocks = new StocksRepo();
		allStocks.setStocks(stocks());
		return allStocks;
	}
	
	@Bean
	public MStockDao stockDao(){
		MStockDao stockDao = new MStockDao();
		return stockDao;
	}
	
	@Bean
	public MTradeDao tradeDao(){
		MTradeDao tradeDao = new MTradeDao();
		return tradeDao;
	}
	
	@Bean
	public StockServiceImpl stockService(){
		StockServiceImpl srv = new StockServiceImpl();
		return srv;
	}

	@Bean
	public StockSampleInputPrompts stockSampleInputPrompts(){
		return new StockSampleInputPrompts();
	}
}
