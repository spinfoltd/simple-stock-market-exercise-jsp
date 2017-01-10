package stockmarket.example.model;

public enum MStockTypeEnum {
	COMMON("Common"),
	PREFERRED("Preferred");
	
	private String name;
	private MStockTypeEnum(String name) {
		this.name = name;
	}
	
	public static MStockTypeEnum fromValue(String name){
		for(MStockTypeEnum st :values()){
			if(st.name.equalsIgnoreCase(name)){
				return st;
			}
		}
		return null;
	}
}
