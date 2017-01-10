package stockmarket.example.uifacade.input.types;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class ProceedDefaultValueExceptionTest {

	@Test
	public void testIsCausedByException() {
		ProceedDefaultValueException pe = new ProceedDefaultValueException(new NullPointerException());
		Assert.assertTrue(pe.isCausedByException(NullPointerException.class));
		Assert.assertFalse(pe.isCausedByException(IOException.class));
	}
}
