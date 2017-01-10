package stockmarket.example.uifacade.input.types;

import stockmarket.example.uifacade.input.msg.AllowedType;
import stockmarket.example.uifacade.input.msg.InvalidInputException;

public class StringAllowedType extends AllowedType<String>{
	
	
	
	public StringAllowedType() {
		super(String.class);
	}

	@Override
	public String valueOf(String s) throws NullPointerException,
			InvalidInputException {
		notNullValue(s);
		return s;
	}
}
