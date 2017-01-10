package stockmarket.example.consts;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.SimpleDateFormat;

public class StockConstants {
	public static class AllowedType{
		public static String allowedType = "AllowedType";
		public static String allowedFormat = "AllowedFormat";
		public static String allowedValues = "AllowedValues";
	}
	
	public static class DateFormat{
		public static String inputDateFormat = "yyyy.MM.dd.HH.mm.ss";
		public static SimpleDateFormat simplDateFormat = new SimpleDateFormat(inputDateFormat);
	}
	
	public static class MBigDecimalScale{
		public static int inputBigDecimalScale = 4;
		public static int outputRounding = BigDecimal.ROUND_HALF_DOWN;
		public static int outputRoundingUP = BigDecimal.ROUND_UP;
		public static BigDecimal setScale(BigDecimal bd){
			/*BigDecimal bd1= new BigDecimal(bd,inputBigDecimalScale,MathContext.DECIMAL32);
			return bd1;*/
			return bd.setScale(inputBigDecimalScale,outputRounding);
			
		}
		public static BigDecimal mDivideWithScale(BigDecimal givenVal , BigDecimal divisor){
			if(givenVal == null || divisor ==null || divisor.equals(BigDecimal.ZERO)){
				return null;
			}else{
				return setScale(givenVal).divide(setScale(divisor), StockConstants.MBigDecimalScale.outputRounding);
			}
			
		}
		public static BigDecimal setScaleUP(BigDecimal bd){
			return bd.setScale(inputBigDecimalScale,outputRoundingUP);
			
		}
		public static BigDecimal mDivideWithScaleUP(BigDecimal givenVal , BigDecimal divisor){
			if(givenVal == null || divisor ==null || divisor.equals(BigDecimal.ZERO)){
				return null;
			}else{
				return setScaleUP(givenVal).divide(setScaleUP(divisor), StockConstants.MBigDecimalScale.outputRoundingUP);
			}
			
		}
	}
}
