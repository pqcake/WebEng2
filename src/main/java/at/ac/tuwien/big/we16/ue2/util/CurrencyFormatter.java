package at.ac.tuwien.big.we16.ue2.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Philipp on 20.04.2016.
 */
public class CurrencyFormatter {
    public static String format(BigDecimal b){
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.GERMAN);
        DecimalFormat df = (DecimalFormat)nf;
        return df.format(b);
    }
}
