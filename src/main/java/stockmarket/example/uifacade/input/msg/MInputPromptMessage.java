package stockmarket.example.uifacade.input.msg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import stockmarket.example.consts.StockConstants;
import stockmarket.example.uifacade.input.types.ProceedDefaultValueException;
import stockmarket.example.util.MStringUtil;

public class MInputPromptMessage<K,V> extends MIOMessage{
	private String fieldName;
	private Integer tab =0;
	private AllowedType<K> allowedType;
	private AllowedValuesWithKey<K,V> allowedValues;
	private MInputPromptMessage parentMenu; 
	
	private List<MInputPromptMessage> subMenus = new ArrayList<MInputPromptMessage>();
	
	public Boolean hasSubMenu(){
		return (subMenus.size()>0);
	}
	public Boolean hasPrentMenu(){
		return (null != parentMenu);
	}
	
	public String getFieldName() {
		return fieldName;
	}
	public MInputPromptMessage<K,V>  addSubMenu(MInputPromptMessage menuItem ){
		this.subMenus.add(menuItem);
		menuItem.parentMenu = this;
		return this;
	}
	public String getFieldNameUnderLine(Integer tab, String fieldName){
		return "\n"+ MStringUtil.getStringWithTab(tab,StringUtils.repeat("*", fieldName.length()))
				
				;
	}
	
	public MInputPromptMessage(Integer tab,String fieldName, AllowedType<K> allowedType) {
		this.fieldName = fieldName;
		this.allowedType = allowedType;
		this.tab= tab;
		//this.msgTemplate = "Input value for  Field:{0} - "+allowedType.getAllowedMessage(tab,1);
		this.msgTemplate = "{0} - "
				+ getFieldNameUnderLine(tab, fieldName)+allowedType.getAllowedMessage(tab,1);
	}
	
	public MInputPromptMessage(Integer tabSpace,String fieldName, AllowedType<K> allowedType, K... valsParam) {
		this(tabSpace,fieldName,allowedType);
		if(null != valsParam && valsParam.length >0){
			this.allowedValues =allowedType.createAllowedValues(valsParam);
			this.msgTemplate += allowedValues.getAllowedMessage(tab,allowedType.nextCounter);
			this.vals = new String[]{this.fieldName,allowedType.getAllowedTp(),allowedType.format};
		}else{
			this.vals = new String[]{this.fieldName,allowedType.getAllowedTp(),allowedType.format};
		}
	}
	public MInputPromptMessage(String fieldName, AllowedType<K> allowedType, K... valsParam) {
		this(0,fieldName,allowedType,valsParam);
	}
	public MInputPromptMessage(Integer tabSpace,String fieldName, AllowedType<K> allowedType, Map<K,V> keyVals) {
		this(tabSpace,fieldName,allowedType);
		if(null != keyVals && keyVals.keySet().size()>0){
			this.allowedValues =allowedType.createAllowedValues(keyVals);
			this.msgTemplate += allowedValues.getAllowedMessage(tab,allowedType.nextCounter);
			this.vals = new String[]{this.fieldName,allowedType.getAllowedTp()};
		}else{
			this.vals = new String[]{this.fieldName,allowedType.getAllowedTp()};
		}
	}
	
	public MInputPromptMessage(String fieldName, AllowedType<K> allowedType, Map<K,V> keyVals) {
		this(0,fieldName,allowedType,keyVals);
	}
	
	
	public void askForExitOption(){
		System.out.print("\n"+MStringUtil.getStringWithTab(tab+1,"(Enter Q for Exit) :"));
	}
	
	public int calculateTreeLevel(){
		int level=1;
		while(this.parentMenu !=null){
			level ++;
		}
		return level;
	}
	public Boolean reactForBackString(String backStr){
		if(("B"+calculateTreeLevel()).equalsIgnoreCase(backStr)){
			return true;
		}
		return false;
	}
	
	public void askForBackOption(){
		if(this.hasPrentMenu()){
		System.out.print("\n"+MStringUtil.getStringWithTab(tab+1,"(Enter Q for Exit) :"));
		this.parentMenu.askForBackOption();
		} 
	}
	
	public String readCommandPrompt(BufferedReader br) throws IOException{
		return  br.readLine();
	}
	
	public KVPair<K, V> askForInput() throws IOException, ProceedDefaultValueException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Boolean validValue = false;
		while (!validValue) {
			//AnsiConsole.out.println(Ansi.ansi().fg(Ansi.Color.RED).a(MStringUtil.getStringWithTab(tab,this.getMessage())));
			System.out.print(MStringUtil.getStringWithTab(tab,this.getMessage()));
            askForExitOption();
            System.out.print("\n"+MStringUtil.getStringWithTab(tab+1,""));
            //String input = br.readLine();
            System.out.println(MStringUtil.getStringWithTab(tab,"$YOUR INPUT:--------->"));
            //AnsiConsole.out.println(Ansi.ansi().reset().bgBright(Ansi.Color.RED).a(MStringUtil.getStringWithTab(tab,"$YOUR INPUT:--------->")));
            String input = readCommandPrompt(br);
            if ("q".equals(input)) {
                System.out.println(MStringUtil.getStringWithTab(tab,"Exit!"));
                System.exit(0);
            }
            K inputVal = null;
            try{
            	inputVal = allowedType.valueOf(input);
            	//callValidate method here
            	
            	if(null !=allowedValues
                		&& !allowedValues.isValueInTheAllowedValues(inputVal)
                		){
                	System.out.println(MStringUtil.getStringWithTab(tab+1,"Please Enter  value from AllowedValues"));
            		//System.out.println(allowedValues.getAllowedMessage(tab,1));
                }else{
                	validValue = true;
                	//
                	if(null != allowedValues && allowedValues.hasAllowedValues()){
                		System.out.print(MStringUtil.getStringWithTab(tab+1,"   {You  Entered:"+allowedValues.getValueFromKey(inputVal).getAllowedMsg()+"}\n"));
                		return allowedValues.getValueFromKey(inputVal);
                	}else{
                		System.out.print(MStringUtil.getStringWithTab(tab+1,"   {You  Entered:"+inputVal+"}\n"));
                		return new KVPair<K, V>(inputVal, null);
                	}
                }
            }catch(NullPointerException ne){            	
            	System.out.println(MStringUtil.getStringWithTab(tab+1,"Enter some value"));
            }catch(InvalidInputException iie){
            	if(iie.getPromptMsgFromException() == true){
            		System.out.println(MStringUtil.getStringWithTab(tab,iie.getStr()));
            	}else{
            		System.out.println(MStringUtil.getStringWithTab(tab,"Please Enter Valid Input of AllowedType:"));
            	}
            	
            }
        }
		return null;
	}
	
	
	
	public AllowedType<K> getAllowedType() {
		return allowedType;
	}

	public AllowedValuesWithKey<K, V> getAllowedValues() {
		return allowedValues;
	}
	public Integer getTab() {
		return tab;
	}
}
