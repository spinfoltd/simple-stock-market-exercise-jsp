package stockmarket.example.uifacade.input.msg;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import stockmarket.example.consts.StockConstants;
import stockmarket.example.uifacade.input.types.ProceedDefaultValueException;
import stockmarket.example.util.MStringUtil;

public abstract class AllowedType<T> {
	Class<T> tClass;
	protected String format;
	public int nextCounter;
	
	public AllowedType(Class<T> tClass) {
		this.tClass = tClass;
	}

	public AllowedType(Class<T> tClass,String format) {
		super();
		this.tClass = tClass;
		this.format = format;
	}
	
	public String getAllowedTp(){
		return tClass.getSimpleName();
	}
	
	/*public AllowedValues<T> createAllowedValues(T ... vals){
		AllowedValues<T> allowedVal = new AllowedValues<T>(this,vals);
		return allowedVal;
	}*/
	public <V> AllowedValuesWithKey<T,V> createAllowedValues(T ... vals){
		AllowedValuesWithKey<T,V> allowedVal = new AllowedValuesWithKey<T,V>(this,vals);
		return allowedVal;
	}
	
	public<V> Map<T,V> createMapWithCounter(V ... vals){
		Map<T,V> intKeyValMap = new HashMap<T, V>();
		for(int i=0 ; i<vals.length; i++){
			if(tClass == Integer.class){
				intKeyValMap.put(((T)new Integer(i+1)), vals[i]);
			}
		}
		return intKeyValMap;
	}
	
	public<V> AllowedValuesWithKey<T,V> createAllowedValuesWithCounter(V ... vals){
		AllowedValuesWithKey<T,V> allowedVal = new AllowedValuesWithKey<T,V>(this,createMapWithCounter(vals));
		return allowedVal;
	}
	
	public <V> AllowedValuesWithKey<T,V> createAllowedValues(Map<T,V> vals){
		AllowedValuesWithKey<T,V> allowedVal = new AllowedValuesWithKey<T,V>(this,vals);
		return allowedVal;
	}
	
	public String getAllowedMessage(Integer tab,int counter){
		nextCounter = counter;
		return "\n"+ MStringUtil.getStringWithTab(tab+1,"("+StockConstants.AllowedType.allowedType+": {"+(nextCounter++)+"})" +
				((format !=null)? "("+StockConstants.AllowedType.allowedFormat+":{"+(nextCounter++)+"})":"")
				)
				;
	}
	
	public Boolean notNullValue(String s) throws NullPointerException{
		if(null ==s ||StringUtils.isEmpty(s.trim())){
			throw new NullPointerException();		}
		return true;
	}
	
	public abstract T valueOf(String s) throws NullPointerException, InvalidInputException, ProceedDefaultValueException;
	
	public Boolean isInputFromSet(T inputT, T... t){
		return Arrays.asList(t).contains(inputT);
	}
}

