package stockmarket.example.uifacade.input.types;

import java.math.BigDecimal;

import stockmarket.example.uifacade.input.msg.AllowedType;
import stockmarket.example.uifacade.input.msg.InvalidInputException;

public class IntegerAllowedType extends AllowedType<Integer>{
	
	public IntegerAllowedType() {
		super(Integer.class);
	}

	@Override
	public Integer valueOf(String s) throws NullPointerException,
			InvalidInputException {
		notNullValue(s);
		Integer input = null;
		try{
			input =  new Integer(s);
			if(input.intValue() == 0){
				throw new InvalidInputException("Cannot BE Zero", true);
			}else if(input.intValue() <0){
				throw new InvalidInputException("Cannot BE Negative",true);
			}
		}
		catch(InvalidInputException iie){
			throw iie;
		}
		catch(Exception e){
			throw new  InvalidInputException(this.format, false);
		}
		return input;
	}
}
