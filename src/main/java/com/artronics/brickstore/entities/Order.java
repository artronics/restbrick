package com.artronics.brickstore.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "`order`")
public class Order extends BaseEntity {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnore
    private Customer customer;

    @NotEmpty
    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "order_item")
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<OrderItem> orderItems;

    private boolean dispatched;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public boolean isDispatched() {
        return dispatched;
    }

    public boolean getDispatch() {
        return dispatched;
    }

    public void setDispatched(boolean dispatched) {
        this.dispatched = dispatched;
    }
}
