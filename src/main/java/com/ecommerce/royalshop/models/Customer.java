package com.ecommerce.royalshop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="_customer")
public class Customer extends User{
    private  String firstname;
    private String lastname;
    private String address;
    private String city;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.REMOVE)
    private Set<Order> orders = new HashSet<>();

    @JsonIgnore
    public Set<Order> getOrders() {
        return orders;
    }
}
