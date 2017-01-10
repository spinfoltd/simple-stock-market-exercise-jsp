package stockmarket.example.uifacade.input.types;

import java.util.Date;

import stockmarket.example.consts.StockConstants;
import stockmarket.example.uifacade.input.msg.AllowedType;
import stockmarket.example.uifacade.input.msg.InvalidInputException;
import stockmarket.example.uifacade.input.msg.MInputPromptMessage;

public class TimestampAllowedType extends AllowedType<Date> implements DefaultValueType<Date>{
	
	public TimestampAllowedType() {
		super(Date.class);
		this.format=StockConstants.DateFormat.inputDateFormat;
	}
	

	public Date getDefaultValue() {
		return new Date();
	}

	public Boolean doesTheExceptionPropmtDefault(Exception e) {
		if(e instanceof NullPointerException
			|| e instanceof InvalidInputException){
				return true;
			}
		return false;
	}

	public <V> MInputPromptMessage<Date, V> getPromptForDefault() {
		return null;
	}


	@Override
	public Date valueOf(String s) throws NullPointerException,
			InvalidInputException, ProceedDefaultValueException {
		Date input = null;
		try{
			notNullValue(s);
			input = StockConstants.DateFormat.simplDateFormat.parse(s);
		}catch(NullPointerException ne){
			throw new ProceedDefaultValueException(ne);
		}
		catch(Exception e){
			throw new  InvalidInputException(this.format, false);
		}
		return input;
	}
}
