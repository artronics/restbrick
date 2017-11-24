package com.artronics.brickstore.entities;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class Customer extends BaseEntity {
    @OneToMany(mappedBy = "customer")
    private Set<Order> orders;

    private String name;

    public Customer() {
    }

    public Customer(Long id, String name) {
        super(id);
        this.name = name;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
