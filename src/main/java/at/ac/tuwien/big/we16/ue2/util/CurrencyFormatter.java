package at.ac.tuwien.big.we16.ue2.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Philipp on 20.04.2016.
 */
public class CurrencyFormatter {

    private static final Logger LOGGER= LogManager.getLogger(CurrencyFormatter.class);

    public static String format(BigDecimal b){
        if(b!=null) {
            //DecimalFormat df = new DecimalFormat("#,##0.00 €");
            NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.GERMANY);
            return nf.format(b);
        }else{
            return null;
        }
    }
}
