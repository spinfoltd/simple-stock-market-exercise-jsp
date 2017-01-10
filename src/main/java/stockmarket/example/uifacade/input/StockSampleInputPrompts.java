package stockmarket.example.uifacade.input;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import stockmarket.example.consts.StockConstants;
import stockmarket.example.model.PriceInvalidException;
import stockmarket.example.model.StockNotFoundException;
import stockmarket.example.service.impl.StockServiceImpl;
import stockmarket.example.uifacade.input.msg.KVPair;
import stockmarket.example.uifacade.input.msg.MInputPromptMessage;
import stockmarket.example.uifacade.input.types.BigDecimalAllowedType;
import stockmarket.example.uifacade.input.types.ProceedDefaultValueException;
import stockmarket.example.uifacade.input.types.TimestampAllowedType;
import stockmarket.example.util.MStringUtil;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class StockSampleInputPrompts {
	@Autowired
	StockServiceImpl stockService;
	
	private MInputPromptMessage<Integer, String> rt_recTradeAskSmbl;
	private MInputPromptMessage<Date,Date> rt_recordedTimeMsg;
	private MInputPromptMessage<String, String> rt_buySellMsgPrompt;
	private MInputPromptMessage<Integer, Integer> rt_qttyMsgPrmpt;
	private MInputPromptMessage<BigDecimal,BigDecimal> rt_askPricMsgPrmt;
	private MInputPromptMessage<String, String> rt_useTimeStmpDefaultInpMsgPrmpt;
	
	public void askRecordTradeMsgPrmpt(int tab) throws IOException, StockNotFoundException, ProceedDefaultValueException{
		String fieldName1 = "   <<Record A Trade>>";
		String fieldNamUndrLine = StringUtils.repeat("-", fieldName1.length());
		System.out.println(MStringUtil.getUnderLineWithTabWithSpaces(tab-3, fieldName1));
		System.out.println(MStringUtil.getStringWithTab(tab-3,fieldName1));
		System.out.println(MStringUtil.getUnderLineWithTabWithSpaces(tab-3, fieldName1));
		int childTab = tab+1;
		//Record a trade, with timestamp, quantity of shares, buy or sell indicator and traded price
		rt_recTradeAskSmbl = AllStockInputMsg.getChooseStockPromptMsg(1,stockService.getAllStkSymblWithDesc());
		KVPair<Integer, String> chosenSymb = rt_recTradeAskSmbl.askForInput();
		String chosenSymblStr = chosenSymb.getValue();
		Date recordedTime = null;
		boolean validTimeStamp ;
		do{		
			validTimeStamp = false;
			rt_recordedTimeMsg = AllStockInputMsg.getTimeStampOfTradeMsgInput(tab);
			try {
				KVPair<Date, Date> timestamp = rt_recordedTimeMsg.askForInput();
				recordedTime = timestamp.getKey();
				validTimeStamp = true;
			} catch (ProceedDefaultValueException e) {
				if(e.isCausedByException(NullPointerException.class)){
					//ask for default value inputMsg
					 rt_useTimeStmpDefaultInpMsgPrmpt = AllStockInputMsg.getYNPromptToAcceptDefaultValue(tab+1, "ReEnter the Timestamp","Accept default as present timestamp:"+StockConstants.DateFormat.simplDateFormat.format(new Date()));
					KVPair<String, String> useDefault =rt_useTimeStmpDefaultInpMsgPrmpt.askForInput();
					if(useDefault.getKey().equals("Y")){
						recordedTime = ((TimestampAllowedType)rt_recordedTimeMsg.getAllowedType()).getDefaultValue();
						validTimeStamp = true;
					}
				}
			}
		}while(!validTimeStamp);
		rt_buySellMsgPrompt = AllStockInputMsg.getBuySellMsgPrmpt(tab);
		KVPair<String, String> buySell = rt_buySellMsgPrompt.askForInput();
				//AllStockInputMsg.askBuySelOptionValsForRecordTradeMsgPrmpt(childTab).askForInput();
		rt_qttyMsgPrmpt = AllStockInputMsg.getQuantityOfSharesMsgPrmpt(tab);
		KVPair<Integer, Integer> quantity = rt_qttyMsgPrmpt.askForInput();
		
		rt_askPricMsgPrmt = AllStockInputMsg.getPriceInputMsgPrmpt(tab);
		KVPair<BigDecimal, BigDecimal> askPrice = rt_askPricMsgPrmt.askForInput();
		
		stockService.recordTrade(chosenSymblStr,recordedTime, buySell.getValue(), quantity.getKey(), askPrice.getKey());

	}
	

	public String askForSymbol() throws IOException, ProceedDefaultValueException{
		MInputPromptMessage<Integer, String> smblPrmpg = AllStockInputMsg.getChooseStockPromptMsg(1,stockService.getAllStkSymblWithDesc());
		KVPair<Integer, String> chosenSymb = smblPrmpg.askForInput();
		return chosenSymb.getValue();
	}

	private MInputPromptMessage<BigDecimal, BigDecimal> calcDividendInputPrmptMsg;
	private MInputPromptMessage<Integer, String> calcDividendInputAskSmbl;
	
	public void displayResult(int tab, String resultString){
		System.out.println("\n"+MStringUtil.getAstrikUnderLineWithTabWithSpaces(0, resultString));
		System.out.println(MStringUtil.getAstrikUnderLineWithTabWithSpaces(0, resultString));
		System.out.println(MStringUtil.getStringWithTab(0,resultString));
		System.out.println(MStringUtil.getAstrikUnderLineWithTabWithSpaces(0, resultString));
		System.out.println(MStringUtil.getAstrikUnderLineWithTabWithSpaces(0, resultString)+"\n");
		
	}
	public BigDecimal calculateDivident(int tab) throws StockNotFoundException, IOException, PriceInvalidException, ProceedDefaultValueException{
		calcDividendInputAskSmbl = AllStockInputMsg.getChooseStockPromptMsg(1,stockService.getAllStkSymblWithDesc());
		KVPair<Integer, String> chosenSymb = getCalcDividendInputAskSmbl().askForInput();
		String chosenSymblStr = chosenSymb.getValue();
		calcDividendInputPrmptMsg = AllStockInputMsg.getPriceInputMsgPrmpt(tab);
		KVPair<BigDecimal, BigDecimal> prcInpOutputClcDivYld = calcDividendInputPrmptMsg.askForInput();
		BigDecimal result = stockService.calculateDividendYield(chosenSymblStr, prcInpOutputClcDivYld.getKey());
		//System.out.println("RESULT: Dividend Yield"+":" +result);
		displayResult(tab,"<<<   RESULT: Dividend Yield"+":" +result +"   >>>");
		return result;
	}
	private MInputPromptMessage<BigDecimal, BigDecimal> calculatePRInputPrmpMsg;
	private MInputPromptMessage<Integer, String> calculatePRAskSmbl;

	public void calculatePR(int tab) throws IOException, StockNotFoundException, ProceedDefaultValueException{
		calculatePRAskSmbl = AllStockInputMsg.getChooseStockPromptMsg(1,stockService.getAllStkSymblWithDesc());
		KVPair<Integer, String> chosenSymb = calculatePRAskSmbl.askForInput();
		String chosenSymblStr = chosenSymb.getValue();
		calculatePRInputPrmpMsg = AllStockInputMsg.getPriceInputMsgPrmpt(tab);
		KVPair<BigDecimal, BigDecimal> prcInpOutputClcDivYld = calculatePRInputPrmpMsg.askForInput();
		BigDecimal result = stockService.calculatPrRatio(chosenSymblStr, prcInpOutputClcDivYld.getKey());		
		displayResult(tab,"<<<   RESULT: P/E Ratio"+":" +result +"   >>>");
	}
	private MInputPromptMessage<BigDecimal, BigDecimal> calcVolWtdStkPriceInputPrmptMsg;
	private MInputPromptMessage<Integer, String> calcVolWtdStkPriceAskSmbl;
	
	public void calculateVolumeWeightedStockPrice(int tab) throws IOException, StockNotFoundException, ProceedDefaultValueException{
		calcVolWtdStkPriceAskSmbl = AllStockInputMsg.getChooseStockPromptMsg(1,stockService.getAllStkSymblWithDesc());
		KVPair<Integer, String> chosenSymb = calcVolWtdStkPriceAskSmbl.askForInput();
		String chosenSymblStr = chosenSymb.getValue();
		calcVolWtdStkPriceInputPrmptMsg = AllStockInputMsg.getPriceInputMsgPrmpt(tab);
		BigDecimal result = stockService.calcVolumeWeightedStockPriceWithin15Mins(chosenSymblStr);
		displayResult(tab,"<<<   RESULT: Volume Weighted Stock Price"+":" +result +"   >>>");		
	}

	public void calculateGBCEOfAllShareIndex(int tab) throws IOException, StockNotFoundException{
		BigDecimal result = stockService.calculateGBCE();
		displayResult(tab,"<<<   RESULT: GBCE of All Share Index"+":" +result +"   >>>");		
	}

	public void showAvailableStocks(int tab) throws JsonGenerationException, JsonMappingException, IOException{
		System.out.println(stockService.getAvailableStocksStrings(tab));
	}

	public void showRecordedTrades(int tab) throws JsonGenerationException, JsonMappingException, IOException{
		System.out.println(stockService.getRecordedTradesStrings(tab));
	}
	
	private MInputPromptMessage<Integer, String> sampleStockMainMenuPrompt ;
	
	public void askStockExample() throws IOException, StockNotFoundException, PriceInvalidException, ProceedDefaultValueException{
		boolean repeat = false;
		do{
			repeat = false;
			int tab =0;
			String fieldName1 = "<<Simple Stock Example:>>";
			System.out.println(MStringUtil.getDoubleUnderLineWithTabWithSpaces(tab, fieldName1));
			System.out.println(MStringUtil.getStringWithTab(tab,fieldName1));
			System.out.println(MStringUtil.getDoubleUnderLineWithTabWithSpaces(tab, fieldName1));
			int stockServiceTab = tab+1;
			sampleStockMainMenuPrompt = AllStockInputMsg.getSampleStockInputMsgPrmpt(stockServiceTab);
			int chosenService = sampleStockMainMenuPrompt.askForInput().getKey();
			int specificServiceTab = stockServiceTab +2;
			switch (chosenService) {
			case 1:
				calculateDivident(specificServiceTab);
				break;
			case 2:
				calculatePR(specificServiceTab);
				break;
			case 3:
				askRecordTradeMsgPrmpt(specificServiceTab);
				break;
			case 4:
				calculateVolumeWeightedStockPrice(specificServiceTab);
				break;
			case 5:
				calculateGBCEOfAllShareIndex(specificServiceTab);
				break;
			case 6:
				showAvailableStocks(specificServiceTab);
				break;
			case 7:
				showRecordedTrades(specificServiceTab);
				break;
			default:
				break;
			}
		}while(true);
	}


	public MInputPromptMessage<Integer, String> getRt_recTradeAskSmbl() {
		return rt_recTradeAskSmbl;
	}


	public MInputPromptMessage<Date, Date> getRt_recordedTimeMsg() {
		return rt_recordedTimeMsg;
	}


	public MInputPromptMessage<String, String> getRt_buySellMsgPrompt() {
		return rt_buySellMsgPrompt;
	}


	public MInputPromptMessage<Integer, Integer> getRt_qttyMsgPrmpt() {
		return rt_qttyMsgPrmpt;
	}


	public MInputPromptMessage<BigDecimal, BigDecimal> getRt_askPricMsgPrmt() {
		return rt_askPricMsgPrmt;
	}


	public MInputPromptMessage<String, String> getRt_useTimeStmpDefaultInpMsgPrmpt() {
		return rt_useTimeStmpDefaultInpMsgPrmpt;
	}


	public MInputPromptMessage<BigDecimal, BigDecimal> getCalcDividendInputPrmptMsg() {
		return calcDividendInputPrmptMsg;
	}


	public MInputPromptMessage<Integer, String> getCalcDividendInputAskSmbl() {
		return calcDividendInputAskSmbl;
	}


	public MInputPromptMessage<BigDecimal, BigDecimal> getCalculatePRInputPrmpMsg() {
		return calculatePRInputPrmpMsg;
	}


	public MInputPromptMessage<Integer, String> getCalculatePRAskSmbl() {
		return calculatePRAskSmbl;
	}


	public MInputPromptMessage<BigDecimal, BigDecimal> getCalcVolWtdStkPriceInputPrmptMsg() {
		return calcVolWtdStkPriceInputPrmptMsg;
	}


	public MInputPromptMessage<Integer, String> getCalcVolWtdStkPriceAskSmbl() {
		return calcVolWtdStkPriceAskSmbl;
	}


	public MInputPromptMessage<Integer, String> getSampleStockMainMenuPrompt() {
		return sampleStockMainMenuPrompt;
	}

}
