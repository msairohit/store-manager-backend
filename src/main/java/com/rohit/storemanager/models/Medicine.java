package com.rohit.storemanager.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "MEDICINE")
@Data
@NoArgsConstructor
public class Medicine {

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonIgnore
    @Column(name = "ID")
    @Id
    private Long id;

    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    @Column(name = "COMPANY")
    private String company;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "MANUFACTURE_DATE", nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date manufactureDate = new Date();

    @Column(name = "EXPIRY_DATE", nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date expiryDate = new Date();

    /**
     * Number of sheets/bottles of this particular medicine
     */
    @Column(name = "QUANTITY", nullable = false)
    private Double quantity = (double) 0;

    /**
     * Selling price of the medicine
     */
    @Column(name = "PRICE", nullable = false)
    private Double price = (double) 0;

    @Column(name = "MRP")
    private Double mrp;

    @Column(name = "BATCH_ID")
    private String batchId;

    @Transient
    private boolean addedToCart;

    @Transient
    private Double cartQuantity;

    @Column(nullable = true, length = 64)
    private String photoName;

    @Transient
    private MultipartFile photo;

}
