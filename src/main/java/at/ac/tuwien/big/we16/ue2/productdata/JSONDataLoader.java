package at.ac.tuwien.big.we16.ue2.productdata;

import at.ac.tuwien.big.we16.ue2.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class JSONDataLoader {

    private static List<Product> products=new LinkedList<>();

    /*public static Book[] getBooks() {
        if (products == null)
            loadProducts();
        return products.getBooks();
    }*/

    private static void loadProducts() {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("products.json");
        Reader reader = new InputStreamReader(is);
        Gson gson = new GsonBuilder().setDateFormat("yyyy,MM,dd,HH,mm,ss,SSS").create();
        products = gson.fromJson(reader, new TypeToken<List<Product>>(){}.getType());
        //TODO fill out products.json and test this
    }


    public class Product {

        private long id;
        private String name;
        private BigDecimal current_bid;
        private User highest_bidder;
        private String img;
        private Date endtime;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public BigDecimal getCurrent_bid() {
            return current_bid;
        }

        public void setCurrent_bid(BigDecimal current_bid) {
            this.current_bid = current_bid;
        }

        public User getHighest_bidder() {
            return highest_bidder;
        }

        public void setHighest_bidder(User highest_bidder) {
            this.highest_bidder = highest_bidder;
        }

        public Date getEndtime() {
            return endtime;
        }

        public void setEndtime(Date endtime) {
            this.endtime = endtime;
        }
    }
}
