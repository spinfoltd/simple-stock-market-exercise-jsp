package stockmarket.example.run;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import stockmarket.example.model.PriceInvalidException;
import stockmarket.example.model.StockNotFoundException;
import stockmarket.example.uifacade.input.StockSampleInputPrompts;
import stockmarket.example.uifacade.input.types.ProceedDefaultValueException;

public class StockSampleMainRun {
	public static void main(String[] args) throws IOException, StockNotFoundException, PriceInvalidException, ProceedDefaultValueException{
		ApplicationContext ctx = 
	            new AnnotationConfigApplicationContext("stockmarket.example.sprgs.config");
		StockSampleInputPrompts inputPrompts = ctx.getBean(StockSampleInputPrompts.class);
		inputPrompts.askStockExample();
	}

}
