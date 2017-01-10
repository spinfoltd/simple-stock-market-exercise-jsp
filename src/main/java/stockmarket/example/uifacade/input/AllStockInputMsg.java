package stockmarket.example.uifacade.input;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import stockmarket.example.model.BuySellIndicatorEnum;
import stockmarket.example.uifacade.input.msg.KVPair;
import stockmarket.example.uifacade.input.msg.MInputPromptMessage;
import stockmarket.example.uifacade.input.types.BigDecimalAllowedType;
import stockmarket.example.uifacade.input.types.IntegerAllowedType;
import stockmarket.example.uifacade.input.types.ProceedDefaultValueException;
import stockmarket.example.uifacade.input.types.StringAllowedType;
import stockmarket.example.uifacade.input.types.TimestampAllowedType;

public class AllStockInputMsg {
	public static Map<String,String> getBuySelOptionVals(){
		Map<String,String> buySellVals = new HashMap<String, String>();
		buySellVals.put("B", BuySellIndicatorEnum.BUY.name());
		buySellVals.put("S", BuySellIndicatorEnum.SELL.name());
		return buySellVals;
	}
	
	public static MInputPromptMessage<String, String> getBuySellMsgPrmpt(int tab){
		String fieldName1 = "Buy Or Sell ?";		
		StringAllowedType intAllowType = new StringAllowedType();
		MInputPromptMessage<String,String> keyValueInput = 
				new MInputPromptMessage<String,String>(tab,fieldName1, intAllowType, getBuySelOptionVals());
		return keyValueInput;
	}
	
	public static MInputPromptMessage<String, String> askBuySelOptionValsForRecordTradeMsgPrmpt(int tab) throws IOException{
		MInputPromptMessage<String, String> keyValueInput = getBuySellMsgPrmpt(tab);
		return keyValueInput;
	}
	
	public static MInputPromptMessage<Integer, Integer> getQuantityOfSharesMsgPrmpt(int tab){
		String fieldName1 = "Quantity Of Shares";
		MInputPromptMessage<Integer,Integer> keyValueInput = 
				new MInputPromptMessage<Integer,Integer>(tab,fieldName1, (new IntegerAllowedType()), new Integer[]{});
		return keyValueInput;
	}
	
	public static KVPair<Integer, Integer> askForQuantityOfShares(int tab) throws IOException, ProceedDefaultValueException{
		return getQuantityOfSharesMsgPrmpt(tab).askForInput();
	}
	
	public static Map<String,String> getDefaultValueWithOptionYesNo(String noHelpString, String defaultValueHelpoStr){
		Map<String,String> yNoVals = new HashMap<String, String>();
		yNoVals.put("Y",defaultValueHelpoStr);
		yNoVals.put("N", noHelpString);
		return yNoVals;
	}
	
	public static MInputPromptMessage<String,String> getYNPromptToAcceptDefaultValue(int tab,String noHelpString, String defaultValueHelpoStr){
		String fieldName1 = defaultValueHelpoStr+"insead of null ";
		StringAllowedType intAllowType = new StringAllowedType();
		MInputPromptMessage<String,String> keyValueInput = 
				new MInputPromptMessage<String,String>(tab,fieldName1, intAllowType, getDefaultValueWithOptionYesNo(noHelpString,defaultValueHelpoStr));
		return keyValueInput;
	}
	public static KVPair<String, String> askYNPromptToAcceptDefaultValue(int tab, String noHelpString, String defaultValueHelpoStr) throws IOException, ProceedDefaultValueException{
		MInputPromptMessage<String, String> keyValueInput = getYNPromptToAcceptDefaultValue(tab,noHelpString,defaultValueHelpoStr);
		return keyValueInput.askForInput();
	}
	
	
	
	public static Map<String,String> getAllowedValuesForYesNo(){
		Map<String,String> yNoVals = new HashMap<String, String>();
		yNoVals.put("Y", "YES");
		yNoVals.put("N", "NO");
		return yNoVals;
	}
	
	
	
	public static MInputPromptMessage<String,String> getYNPromptMsg(int tab){
		String fieldName1 = "yesNoField1";
		StringAllowedType intAllowType = new StringAllowedType();
		MInputPromptMessage<String,String> keyValueInput = 
				new MInputPromptMessage<String,String>(tab,fieldName1, intAllowType, getAllowedValuesForYesNo());
		return keyValueInput;
	}
	
	
	public static MInputPromptMessage<Integer, String> getChooseStockPromptMsg(int tab, List<String> allowedVals){
		String fieldName1 = "Choose Stock Symbol";
		IntegerAllowedType intAllowType = new IntegerAllowedType();
		Map<Integer, String> keyValMap = intAllowType.createMapWithCounter(allowedVals.toArray( new String[allowedVals.size()]));
		MInputPromptMessage<Integer,String> keyValueInput = 
				new MInputPromptMessage<Integer,String>(tab,fieldName1, intAllowType, keyValMap);
		return keyValueInput;
	}
	
	public static MInputPromptMessage<Date, Date> getTimeStampOfTradeMsgInput(int tab){
		String fieldName1 = "Time Of Trade";
		MInputPromptMessage<Date,Date> keyValueInput = 
				new MInputPromptMessage<Date,Date>(tab,fieldName1, (new TimestampAllowedType()), new Date[]{});
		return keyValueInput;
	}
	
	public static KVPair<Date, Date> askForTimeStampOfTrade(int tab) throws IOException, ProceedDefaultValueException{
		MInputPromptMessage<Date,Date> keyValueInput = getTimeStampOfTradeMsgInput(tab);
		return keyValueInput.askForInput();
	}
	
	public static MInputPromptMessage<BigDecimal, BigDecimal> getPriceInputMsgPrmpt(int tab){
		String fieldName1 = "Enter Price";		
		BigDecimalAllowedType intAllowType = new BigDecimalAllowedType();
		MInputPromptMessage<BigDecimal,BigDecimal> keyValueInput = 
				new MInputPromptMessage<BigDecimal,BigDecimal>(tab,fieldName1, intAllowType, new BigDecimal[]{});
		return keyValueInput;
	}
	
	public static KVPair<BigDecimal, BigDecimal> askForPriceInput(int tab) throws IOException, ProceedDefaultValueException{
		MInputPromptMessage<BigDecimal,BigDecimal> keyValueInput = getPriceInputMsgPrmpt(tab);
		KVPair<BigDecimal, BigDecimal> enteredVal = keyValueInput.askForInput();
		return enteredVal;
	}
	
	public static MInputPromptMessage<Integer, String> getSampleStockInputMsgPrmpt(int tab) throws IOException{
		String fieldName1 = "Choose Stock Service";
		/*		Given any price as input, calculate the dividend yield ii. 
		Given any price as input,  calculate the P/E Ratio iii. 
		Record a trade, with timestamp, quantity of shares, buy or sell indicator and traded price iv. 
		Calculate Volume Weighted Stock Price based on trades in past 15 minutes */
		String[] fieldSelectVals = new String[]{
				"Calculate Dividend Yield: From Price",
				"Calculate P/E Ratio: From Price"
				,"Record a trade, with timestamp, quantity of shares, buy or sell indicator and traded price"
				,"Calculate Volume Weighted Stock Price based on trades in past 15 minutes"
				,"Calculate the GBCE All Share Index"
				,"Show Available Stocks"
				,"Recorded Trades"
				,"Start Again"};
		IntegerAllowedType intAllowType = new IntegerAllowedType();
		Map<Integer, String> keyValMap = intAllowType.createMapWithCounter(fieldSelectVals);
		MInputPromptMessage<Integer,String> keyValueInput = 
				new MInputPromptMessage<Integer,String>(tab,fieldName1, intAllowType, keyValMap);
		return keyValueInput;
	}

	public static KVPair<Integer, String> askSampleStockInputMsgPrmpt(int tab) throws IOException, ProceedDefaultValueException{
		MInputPromptMessage<Integer,String> keyValueInput = getSampleStockInputMsgPrmpt(tab);
		return keyValueInput.askForInput();
	}

}
