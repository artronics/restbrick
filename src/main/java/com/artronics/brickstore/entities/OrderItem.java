package com.artronics.brickstore.entities;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class OrderItem {
    private int quantity;

    private String product;

    private BigDecimal pricePerItem;

    public OrderItem() {
    }

    public OrderItem(int quantity, String product, BigDecimal pricePerItem) {
        this.quantity = quantity;
        this.product = product;
        this.pricePerItem = pricePerItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public BigDecimal getPricePerItem() {
        return pricePerItem;
    }

    public void setPricePerItem(BigDecimal pricePerItem) {
        this.pricePerItem = pricePerItem;
    }
}
