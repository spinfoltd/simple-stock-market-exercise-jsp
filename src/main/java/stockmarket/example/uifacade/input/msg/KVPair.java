package stockmarket.example.uifacade.input.msg;

import java.util.List;
import java.util.Map;

public class KVPair<K,V> {
	private K key;
	private V value;
	private String displayValue;
	public KVPair(K key) {
		super();
		this.key = key;
	}
	
	public K getKey() {
		return key;
	}

	public V getValue() {
		return value;
	}

	public String getDisplayValue() {
		return displayValue;
	}

	public KVPair(K key, V value) {
		super();
		this.key = key;
		this.value = value;
	}


	public static <K,V> void  addKeyVauesFromMap(List<KVPair<K, V>> kvPairs, Map<K,V> keyValues) {
		for(K key: keyValues.keySet()){
			kvPairs.add(new KVPair<K, V>(key, keyValues.get(key)));
		}
	}
	public String getAllowedMsg(){
		if(null == getValue()){
			return ""+getKey();
		}else{
			return ""+getKey()+"   <for>   "+getValue();
		}
	}
}
