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
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class JSONDataLoader {

    private static final Logger LOGGER= LogManager.getLogger(JSONDataLoader.class);
    //private static List<Product> products;
    private static Map<Long,Product> productMap;

    public static Map<Long,Product> getProducts() {
        LOGGER.debug("getProducts() called");
        if (productMap == null)
            loadProducts();
        return productMap;
    }

    private static void loadProducts() {
        LOGGER.debug("loadProducts() called");
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("products.json");
        Reader reader = new InputStreamReader(is);
        Gson gson = new GsonBuilder().setDateFormat("yyyy,MM,dd,HH,mm,ss,SSS").create();
        List<Product>products = gson.fromJson(reader, new TypeToken<List<Product>>(){}.getType());
        productMap=new ConcurrentSkipListMap<>();
        for(Product p:products){
            productMap.put(p.getId(),p);
            LOGGER.debug("Adding product {} {} to map",p.getId(),p.getName());
        }
        LOGGER.debug(products.size()+" products loaded");

    }
}
