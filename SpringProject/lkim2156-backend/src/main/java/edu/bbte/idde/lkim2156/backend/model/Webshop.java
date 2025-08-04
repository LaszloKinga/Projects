package edu.bbte.idde.lkim2156.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Webshop extends BaseEntity {
    private LocalDate orderDate;
    private String address;
    private double totalAmount;
    private String paymentMethod;
    private boolean isShipped;

    public Webshop(Integer id, LocalDate orderDate, String address, double totalAmount,
                   String paymentMethod, boolean isShipped) {
        super(id);
        this.orderDate = orderDate;
        this.address = address;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.isShipped = isShipped;
    }

}