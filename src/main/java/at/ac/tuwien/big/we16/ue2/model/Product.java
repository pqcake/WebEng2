package at.ac.tuwien.big.we16.ue2.model;

import at.ac.tuwien.big.we16.ue2.util.CurrencyFormatter;
import com.google.gson.Gson;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Product {

    private long id;
    private String name;
    private BigDecimal current_bid;
    private User highest_bidder;
    private String img;
    private Date endtime;
    private SimpleDateFormat dt = new SimpleDateFormat("yyyy,MM,dd,HH,mm,ss,SSS");

    private boolean allReadySent = false;

    public boolean isAllReadySent() {
        return allReadySent;
    }

    public void setAllReadySent(boolean allReadySent) {
        this.allReadySent = allReadySent;
    }

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFormattedCurrentBid(){
       return CurrencyFormatter.format(current_bid);
    }

    public String getFormattedEndtime(){
        return dt.format(endtime);
    }

    public boolean isExpired(){
        return endtime.before(new Date());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return id == product.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", current_bid=" + current_bid +
                ", highest_bidder=" + highest_bidder +
                ", img='" + img + '\'' +
                ", endtime=" + endtime +
                '}';
    }

    public String toJason() {
        Gson gs = new Gson();
        return gs.toJson(this,Product.class);
    }
}