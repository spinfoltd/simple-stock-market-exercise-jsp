package stockmarket.example.uifacade.input.types;

import java.math.BigDecimal;

import stockmarket.example.uifacade.input.msg.AllowedType;
import stockmarket.example.uifacade.input.msg.InvalidInputException;

public class BigDecimalAllowedType extends AllowedType<BigDecimal>{
	
	
	public BigDecimalAllowedType() {
		super(BigDecimal.class);
	}

	@Override
	public BigDecimal valueOf(String s) throws NullPointerException,
			InvalidInputException {
		notNullValue(s);
		BigDecimal input = null;
		try{
			input =  new BigDecimal(s);
			if(input.compareTo(BigDecimal.ZERO)==0){
				throw new InvalidInputException("Cannot BE Zero", true);
			}else if(input.compareTo(BigDecimal.ZERO)==-1){
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
