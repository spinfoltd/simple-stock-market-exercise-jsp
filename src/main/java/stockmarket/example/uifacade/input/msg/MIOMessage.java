package stockmarket.example.uifacade.input.msg;

public class MIOMessage {
	protected String msgTemplate;
	protected Object[] vals;
	protected String message;
	
	/*public MIOMessage(String msgTemplate, Object... vals) {
		super();
		this.msgTemplate = msgTemplate;
		this.vals = vals;
	}*/

	public String getMessage(){
		if(null == this.message){
			this.message = java.text.MessageFormat.format(msgTemplate,vals);
		}
		return this.message;
	}


}
