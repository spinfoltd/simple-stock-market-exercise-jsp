package stockmarket.example.uifacade.input.msg;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

public class AllowedValues<T> {
	private AllowedType<T> allowedType;
	private T[] vals;
	public int nextCounter;
	
	public AllowedValues(AllowedType<T> allowedType, T[] vals) {
		super();
		this.allowedType = allowedType;
		this.vals = vals;
	}
	
	public Boolean hasAllowedValues(){
		return (null != vals&& vals.length>0);
	}
	
	public Boolean noAllowedValues(){
		return !hasAllowedValues();
	}
	
	public String getAllowedMessage(int counter){
		if(null== vals){
			nextCounter = counter;
			return "" ;
		}
		return " AllowedValues: ("+StringUtils.join(vals,",")+")";
	}
	public Boolean isValueInTheAllowedValues(T inputT){
		return Arrays.asList(vals).contains(inputT);
	}
}
