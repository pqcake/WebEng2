package at.ac.tuwien.big.we16.ue2.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;

/**
 * Created by Philipp on 20.04.2016.
 */
public final class CurrencyFormatter {

    private static final Logger LOGGER= LogManager.getLogger(CurrencyFormatter.class);
    //// df = new DecimalFormat("#,##0.00 €");
    private static DecimalFormat nf = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.GERMANY);
   static {
        nf.setParseBigDecimal(true);
    }

    public static String format(BigDecimal b){
        if(b!=null) {
            return nf.format(b);
        }else{
            return null;
        }
    }

    public static BigDecimal parse(String str){
        return (BigDecimal) nf.parse(str,new ParsePosition(0));
    }
}
