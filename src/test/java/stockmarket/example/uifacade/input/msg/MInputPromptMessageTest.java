package stockmarket.example.uifacade.input.msg;

import static org.mockito.Mockito.spy;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import stockmarket.example.consts.StockConstants;
import stockmarket.example.uifacade.input.AllStockInputMsg;
import stockmarket.example.uifacade.input.types.IntegerAllowedType;
import stockmarket.example.uifacade.input.types.ProceedDefaultValueException;
import stockmarket.example.uifacade.input.types.StringAllowedType;

public class MInputPromptMessageTest {


	@Test
	public void testFieldNameWithoutSelectFromOptions() throws IOException {
		String fieldName1 = "testField1";
		MInputPromptMessage<String,String> stringInput = 
				new MInputPromptMessage<String,String>(fieldName1, new StringAllowedType(), new String[]{});
		Assert.assertTrue(stringInput.getMessage().contains(fieldName1));
		Assert.assertTrue(stringInput.getMessage().contains(StockConstants.AllowedType.allowedType));
		Assert.assertFalse(stringInput.getMessage().contains(StockConstants.AllowedType.allowedValues));
	}

	@SuppressWarnings("deprecation")
	@Test(expected=NullPointerException.class)
	public void testFieldNameWithSelectFromOptions() throws IOException, NullPointerException, InvalidInputException, ProceedDefaultValueException {
		String fieldName1 = "testField1";
		String[] fieldSelectVals = new String[]{"Option1","Option2","Option3"};
		MInputPromptMessage<String,String> stringInput = 
				new MInputPromptMessage<String,String>(fieldName1, new StringAllowedType(), fieldSelectVals);
		System.out.println(stringInput.msgTemplate);
		System.out.println(stringInput.getMessage());
		Assert.assertTrue(null,stringInput.getMessage().contains(fieldName1));
		Assert.assertTrue(stringInput.getMessage().contains(StockConstants.AllowedType.allowedType));
		Assert.assertTrue(stringInput.getMessage().contains(StockConstants.AllowedType.allowedValues));
		Assert.assertTrue(stringInput.getMessage().contains("Option1"));
		Assert.assertTrue(stringInput.getMessage().contains("Option2"));
		Assert.assertTrue(stringInput.getMessage().contains("Option3"));
		stringInput.getAllowedType().valueOf(null);
		stringInput.getAllowedType().valueOf("XXXX");
		Assert.assertFalse(stringInput.getAllowedValues().isValueInTheAllowedValues("XXX"));
		Assert.assertTrue(stringInput.getAllowedValues().isValueInTheAllowedValues("Opiton1"));
	}

	@Test(expected=NullPointerException.class)
	public void testFieldNameWithKeyValueSelectFromOptions() throws IOException, NullPointerException, InvalidInputException, ProceedDefaultValueException {
		String fieldName1 = "testField1";
		String[] fieldSelectVals = new String[]{"Option1","Option2","Option3"};
		IntegerAllowedType intAllowType = new IntegerAllowedType();
		Map<Integer, String> keyValMap = intAllowType.createMapWithCounter(fieldSelectVals);

		MInputPromptMessage<Integer,String> keyValueInput = 
				new MInputPromptMessage<Integer,String>(1,fieldName1, intAllowType, keyValMap);
		System.out.println(keyValueInput.msgTemplate);
		System.out.println(keyValueInput.getMessage());
		Assert.assertTrue(keyValueInput.getTab() == 1);
		Assert.assertTrue(keyValueInput.getMessage().contains(fieldName1));
		Assert.assertTrue(keyValueInput.getMessage().contains(StockConstants.AllowedType.allowedType));
		Assert.assertTrue(keyValueInput.getMessage().contains(StockConstants.AllowedType.allowedValues));
		Assert.assertTrue(keyValueInput.getMessage().contains("Option1"));
		Assert.assertTrue(keyValueInput.getMessage().contains("Option2"));
		Assert.assertTrue(keyValueInput.getMessage().contains("Option3"));
		keyValueInput.getAllowedType().valueOf(null);
		keyValueInput.getAllowedType().valueOf("XXXX");
		Assert.assertFalse(keyValueInput.getAllowedValues().isValueInTheAllowedValues(fieldSelectVals.length+1));
		Assert.assertTrue(keyValueInput.getAllowedValues().isValueInTheAllowedValues(fieldSelectVals.length));
	}


	
	
	@Test
	public void testBuySelOptionValsForRecordTradeMsgPrmp() throws IOException, ProceedDefaultValueException{
		MInputPromptMessage<String, String> keyValueInput = spy(AllStockInputMsg.getBuySellMsgPrmpt(1));		
		Mockito.doReturn("A")
		.doReturn(null)
		.doReturn("S").
		when(keyValueInput).readCommandPrompt(Mockito.any(BufferedReader.class));
		KVPair<String, String> retVal = keyValueInput.askForInput();
		Mockito.verify(keyValueInput,Mockito.times(3)).readCommandPrompt(Mockito.any(BufferedReader.class));
		Assert.assertTrue(retVal.getKey() == "S");
		Assert.assertTrue(retVal.getValue().equals(AllStockInputMsg.getBuySelOptionVals().get("S")));
		Assert.assertNotNull(keyValueInput.getAllowedValues());
	}
	
	
	@Test
	public void testSampleStockInputMsgPrmpt() throws IOException, ProceedDefaultValueException{
		MInputPromptMessage<Integer, String> keyValueInput = spy(AllStockInputMsg.getSampleStockInputMsgPrmpt(1));		
		Mockito.doReturn("A")
		.doReturn(null)
		.doReturn("2").
		when(keyValueInput).readCommandPrompt(Mockito.any(BufferedReader.class));
		KVPair<Integer, String> retVal = keyValueInput.askForInput();
		Mockito.verify(keyValueInput,Mockito.times(3)).readCommandPrompt(Mockito.any(BufferedReader.class));
		Assert.assertTrue(retVal.getKey() == 2);
		Assert.assertNotNull(keyValueInput.getAllowedValues());
	}
	
	
	
	@Test
	public void testQuantityOfShareMsgPrmpt() throws IOException, ProceedDefaultValueException{
		MInputPromptMessage<Integer,Integer> keyValueInput = spy(AllStockInputMsg.getQuantityOfSharesMsgPrmpt(1));
		Mockito.doReturn("10").when(keyValueInput).readCommandPrompt(Mockito.any(BufferedReader.class));
		KVPair<Integer, Integer> retVal = keyValueInput.askForInput();
		Assert.assertTrue(retVal.getKey() == 10);
		Assert.assertNull(keyValueInput.getAllowedValues());
	}
	
	@Test
	public void testQuantityOfShareMsgPrmptInvalid() throws IOException, ProceedDefaultValueException{
		MInputPromptMessage<Integer,Integer> keyValueInput = spy(AllStockInputMsg.getQuantityOfSharesMsgPrmpt(1));		
		Mockito.doReturn("A")
		//.doReturn(null)
		.doReturn("10").when(keyValueInput).readCommandPrompt(Mockito.any(BufferedReader.class));
		KVPair<Integer, Integer> retVal = keyValueInput.askForInput();
		Mockito.verify(keyValueInput,Mockito.times(2)).readCommandPrompt(Mockito.any(BufferedReader.class));
		Assert.assertTrue(retVal.getKey() == 10);
		Assert.assertNull(keyValueInput.getAllowedValues());
	}
	
	@Test
	public void testTimeStamp() throws IOException, ParseException, ProceedDefaultValueException{
		MInputPromptMessage<Date, Date> keyValueInput = spy(AllStockInputMsg.getTimeStampOfTradeMsgInput(1));
		String sendDate =StockConstants.DateFormat.simplDateFormat.format(new Date()); 
		Mockito.doReturn("A")
			//.doReturn("")
			.doReturn(sendDate)
			.when(keyValueInput).readCommandPrompt(Mockito.any(BufferedReader.class));
		KVPair<Date, Date> retVal = keyValueInput.askForInput();
		Mockito.verify(keyValueInput,Mockito.times(2)).readCommandPrompt(Mockito.any(BufferedReader.class));
		Assert.assertTrue(retVal.getKey().equals(StockConstants.DateFormat.simplDateFormat.parse(sendDate)));
	}
	
	@Test
	public void testPriceInput() throws IOException, ParseException, ProceedDefaultValueException{
		MInputPromptMessage<BigDecimal, BigDecimal> keyValueInput = spy(AllStockInputMsg.getPriceInputMsgPrmpt(1));
		String sendDate =StockConstants.DateFormat.simplDateFormat.format(new Date()); 
		Mockito.doReturn("A")
			.doReturn(null)
			.doReturn("10.4444")
			.when(keyValueInput).readCommandPrompt(Mockito.any(BufferedReader.class));
		KVPair<BigDecimal, BigDecimal> retVal = keyValueInput.askForInput();
		Mockito.verify(keyValueInput,Mockito.times(3)).readCommandPrompt(Mockito.any(BufferedReader.class));
		Assert.assertTrue(retVal.getKey().equals(new BigDecimal("10.4444")));
	}

	@Test
	public void testYesNoInputsForStock() throws IOException, ProceedDefaultValueException{
		MInputPromptMessage<String,String> keyValueInput =	spy(AllStockInputMsg.getYNPromptMsg(1));
		Assert.assertTrue(keyValueInput.getAllowedValues().hasAllowedValues());
		Assert.assertFalse(keyValueInput.getAllowedValues().isKeysHasNoValues());
		Mockito.doReturn("Y").when(keyValueInput).readCommandPrompt(Mockito.any(BufferedReader.class));
		KVPair<String, String> retVal = keyValueInput.askForInput();
		Assert.assertTrue(retVal.getKey().equals("Y"));
		Assert.assertTrue(retVal.getValue().equals(AllStockInputMsg.getAllowedValuesForYesNo().get("Y")));
	}
	
	
}
