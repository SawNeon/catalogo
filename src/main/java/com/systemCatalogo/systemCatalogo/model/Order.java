package com.systemCatalogo.systemCatalogo.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class Order {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String unidadeEscolar;

    @OneToMany
    @JoinColumn(name = "order_id")
    private List<OrderItem> orderItems;

    private Double total;

    public Order() {}

    public Order(String unidadeEscolar, List<OrderItem> orderItems, Double total) {
        this.unidadeEscolar = unidadeEscolar;
        this.orderItems = orderItems;
        this.total = total;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnidadeEscolar() {
        return unidadeEscolar;
    }

    public void setUnidadeEscolar(String unidadeEscolar) {
        this.unidadeEscolar = unidadeEscolar;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
