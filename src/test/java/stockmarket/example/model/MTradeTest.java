package stockmarket.example.model;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class MTradeTest {
	
	@Test
	public void testDateFormat() {
		MTrade mTrade = new MTrade();
		Date d = Calendar.getInstance().getTime();
		mTrade.setTradeTime(new Timestamp(d.getTime()));
		System.out.println("Provided Timestamp:"+ mTrade.getTradeTimeInDateFormat());
		Assert.assertNotNull(mTrade.getTradeTimeInDateFormat());
		Assert.assertTrue(mTrade.isTradeTimeWithin15Mins());
		d.setMinutes(d.getMinutes()-16);
		mTrade.setTradeTime(new Timestamp(d.getTime()));
		System.out.println("Provided Timestamp:"+ mTrade.getTradeTimeInDateFormat());
		Assert.assertNotNull(mTrade.getTradeTimeInDateFormat());
		Assert.assertFalse(mTrade.isTradeTimeWithin15Mins());
	}

}
