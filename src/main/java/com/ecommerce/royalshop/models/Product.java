package com.ecommerce.royalshop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "_product")
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String price;
    private String description;
    private String image;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subcategory_id")
    private Subcategory subcategory;

    @ManyToMany(mappedBy = "products", cascade = CascadeType.REMOVE)
    private Collection<Order> orders;

    @JsonIgnore
    public Collection<Order> getOrders() {
        return orders;
    }
}
