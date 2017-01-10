package stockmarket.example.service;

import java.math.BigDecimal;

import stockmarket.example.model.StockNotFoundException;

public interface IStockService {
	public BigDecimal calculateDividendYield(String stockSymbol, BigDecimal price) throws StockNotFoundException;
}
