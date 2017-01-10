package stockmarket.example.uifacade.input.msg;

public class InvalidInputException extends Exception{
	String str;
	public Boolean promptMsgFromException = false;
	public InvalidInputException(String str, Boolean promptMsgFromException ) {
		super();
		this.str = "Valid Input is of Format:"+str;
		this.promptMsgFromException = promptMsgFromException;
	}
	public String getStr() {
		return str;
	}
	public Boolean getPromptMsgFromException() {
		return promptMsgFromException;
	}
	
}
