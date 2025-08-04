package edu.bbte.idde.lkim2156.spring.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Webshop extends BaseEntity {
    private LocalDate orderDate;
    private String address;
    private double totalAmount;
    private String paymentMethod;
    private boolean isShipped;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "store_id")
    private Store storeId;
}
