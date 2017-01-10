package stockmarket.example.uifacade.input.types;

import stockmarket.example.uifacade.input.msg.MInputPromptMessage;

public interface DefaultValueType<T> {
	public T getDefaultValue();
	public Boolean doesTheExceptionPropmtDefault(Exception e);
	public <V> MInputPromptMessage<T,V> getPromptForDefault();
}
