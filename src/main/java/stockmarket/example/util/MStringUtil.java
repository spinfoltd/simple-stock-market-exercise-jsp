package stockmarket.example.util;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MStringUtil {
	public static String getStringWithTab(Integer tab,String msg){
		StringBuffer sb = new StringBuffer();
		if(tab!=null){
			for(int i=0;i<tab;i++){
				sb.append("\t");
			}
		}
		return sb.append(msg).toString();
	}

	public static String getSplCharWithTabWithSpaces(Integer tab,String msg,String splChar){
		StringBuffer sb = new StringBuffer();
		if(tab!=null){
			for(int i=0;i<tab;i++){
				sb.append("\t");
			}
			int indexOfSpace = msg.length() - StringUtils.stripStart(msg, null).length();
			sb.append(StringUtils.repeat(" ", indexOfSpace));
			sb.append(StringUtils.repeat(splChar, (msg.length() -indexOfSpace)));
			return sb.toString();
		}
		return sb.append(msg).toString();
	}
	public static String getUnderLineWithTabWithSpaces(Integer tab,String msg){
		return getSplCharWithTabWithSpaces(tab,msg,"-");
	}
	public static String getAstrikUnderLineWithTabWithSpaces(Integer tab,String msg){
		return getSplCharWithTabWithSpaces(tab,msg,"*");
	}
	public static String getDoubleUnderLineWithTabWithSpaces(Integer tab,String msg){
		return getSplCharWithTabWithSpaces(tab,msg,"=");
	}
	public static String getStringWithTabOnNewLine(Integer tab,String msg){
		StringBuffer sb = new StringBuffer("\n");
		if(tab!=null){
			for(int i=0;i<tab;i++){
				sb.append("\t");
			}
		}
		return sb.append(msg).toString();
	}
	
	public static String getStringWithTabPlusOne(Integer tab,String msg){
		return getStringWithTab(tab+1,msg);
	}
	
	public static <T> String returnJsonStringFromObject(T tObj) throws JsonGenerationException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		//String processedJsonString = processJsonToMatchWithClassFields(jsonString, tClass);
		//T t = mapper.readValue(processedJsonString, tClass);
		return  mapper.writeValueAsString(tObj);
		
	}

}
