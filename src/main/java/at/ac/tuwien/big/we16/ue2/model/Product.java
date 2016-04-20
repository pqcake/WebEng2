package at.ac.tuwien.big.we16.ue2.model;

import java.math.BigDecimal;
import java.util.Date;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}