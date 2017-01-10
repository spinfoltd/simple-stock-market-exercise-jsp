package stockmarket.example.test.integration.cucumber.annotation;

import org.junit.Assert;

import stockmarket.example.dao.BaseSpringTestConfig;
import stockmarket.example.model.MStock;
import stockmarket.example.model.MTrade;
import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;

//@StepDefAnnotation
public class StockTestStepsDefinitions extends BaseSpringTestConfig{
	
    @Given("^Stocks Existing$")
    public void stocksArePresent() {
        Assert.assertTrue(stocks.size()>0);;
    }

    @When("^a trade is added in each stock$")
    public void tradeAddedInEachStock() throws Exception {
    	for( MStock stck :stocks){
    		MTrade nTrade = createNewTradeNow(stck.getSymbol());
    		stockDao.addTradeToStock(stck.getSymbol(), nTrade);
    	}
    }

    @Then("^sum of trades in all stocks should be same as sum of trades$")
    public void itShouldHaveStudent() {
    	Assert.assertEquals(stockDao.getAllTrades().size(),tradeDao.getAllTrades().size());
    }
}
