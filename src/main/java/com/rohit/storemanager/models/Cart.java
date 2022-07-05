package com.rohit.storemanager.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CART", uniqueConstraints = {@UniqueConstraint(columnNames = {"USER_NAME", "PRODUCT_NAME"})})
@Data
@NoArgsConstructor
public class Cart {

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonIgnore
    @Column(name = "ID")
    @Id
    private Long id;

    @Column(name = "USER_NAME", nullable = false)
    //FIXME: If the same user tries to add the same product and then we should get DB exception, then we can simply add
    // get the quantity for the same and add the quantity and total cost to it.
    private String userName = "SYSTEM";

    @Column(name = "PRODUCT_NAME", nullable = false)
    private String productName;

    @Column(name = "DESCRIPTION")
    private String description;

    /**
     * Number of sheets/bottles of this particular medicine
     */
    @Column(name = "QUANTITY")
    private Double quantity = (double) 1;

    /**
     * Selling price of the medicine
     */
    @Column(name = "PRICE", nullable = false)
    private Double price;

    @Column(name = "TOTAL_COST", nullable = false)
    private Double totalCost;

}
