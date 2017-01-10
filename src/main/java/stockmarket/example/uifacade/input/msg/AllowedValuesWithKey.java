package stockmarket.example.uifacade.input.msg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.KeyValue;
import org.apache.commons.collections4.iterators.ArrayListIterator;
import org.apache.commons.lang3.StringUtils;

import stockmarket.example.util.MStringUtil;

public class AllowedValuesWithKey<K,V> {
	private AllowedType<K> allowedType;
	//private Map<K,V> keyValues = new HashMap<K, V>();
	private List<KVPair<K, V>> kvPairs = new ArrayList<KVPair<K,V>>();
	public int nextCounter;
	
	public AllowedValuesWithKey(AllowedType<K> allowedType, K[] vals) {
		super();
		this.allowedType = allowedType;
		for(K val : vals){
			kvPairs.add(new KVPair<K,V>(val));
			//keyValues.put(val, null);
		}
	}
	
	public AllowedValuesWithKey(AllowedType<K> allowedType, Map<K,V> keyValues) {
		super();
		this.allowedType = allowedType;
		KVPair.addKeyVauesFromMap(this.kvPairs, keyValues);
	}

	public Boolean hasAllowedValues(){
		return (null != kvPairs&& kvPairs.size()>0);
	}
	
	public Boolean noAllowedValues(){
		return !hasAllowedValues();
	}
	
	public Boolean isKeysHasNoValues(){
		for(KVPair<K, V> kv: kvPairs){
			if(null != kv.getValue()){
				return false;
			}
		}
		return true;
	}
	public KVPair<K, V> getValueFromKey(K t){
		for(KVPair<K, V> kv: kvPairs){
			if(null != kv.getKey() && kv.getKey().equals(t) ){
				return kv;
			}
		}
		return null;
	}
	
	private List<String> getKeyValuePairs(){
		List<String> kvPair = new ArrayList<String>();		
		if(isKeysHasNoValues()){
			//for(K k: keyValues.keySet()){
			for(KVPair<K, V> kv: kvPairs){
				//kvPair.add(""+kv.getKey());
				kvPair.add(kv.getAllowedMsg());
			}
		}else{
			//for(K k: keyValues.keySet()){
			for(KVPair<K, V> kv: kvPairs){
				//kvPair.add(""+kv.getKey()+kv.getValue());
				kvPair.add(kv.getAllowedMsg());
			}
		}
		
		return kvPair;
	}
	public List<String> getKeyValuePairsOnNewLinesAndTabs(int tab){
		List<String> kvPair = new ArrayList<String>();
			for(String k: getKeyValuePairs()){
				kvPair.add(MStringUtil.getStringWithTabOnNewLine(tab, k));
			}
		return kvPair;
	}
	
	
	public String getAllowedMessage(Integer tab, int counter){
		if(!hasAllowedValues()){
			nextCounter = counter;
			return "" ;
		}
		return "\n"+MStringUtil.getStringWithTab(tab+1,
				" AllowedValues: ("
					+StringUtils.join(getKeyValuePairsOnNewLinesAndTabs(tab+2).toArray(),",")
					+")");
	}
	public Boolean isValueInTheAllowedValues(K inputT){
		return (getValueFromKey(inputT) != null);
	}
}
