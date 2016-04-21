package at.ac.tuwien.big.we16.ue2.productdata;

import at.ac.tuwien.big.we16.ue2.model.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

public class JSONDataLoader {

    private static final Logger LOGGER= LogManager.getLogger(JSONDataLoader.class);
    private static List<Product> products;

    public static List<Product> getProducts() {
        LOGGER.debug("getProducts() called");
        if (products == null)
            loadProducts();
        return products;
    }

    private static void loadProducts() {
        LOGGER.debug("loadProducts() called");
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("products.json");
        Reader reader = new InputStreamReader(is);
        Gson gson = new GsonBuilder().setDateFormat("yyyy,MM,dd,HH,mm,ss,SSS").create();
        products = gson.fromJson(reader, new TypeToken<List<Product>>(){}.getType());
        LOGGER.debug(products.size()+" products loaded");

    }
}
