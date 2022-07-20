package com.example.invoiceservice.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
public class Invoice {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private BigDecimal debt;

    @NotNull
    private Boolean isPaid;

    private LocalDate date = LocalDate.now();

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "client_id")
    @ToString.Exclude
    private Client client;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Invoice invoice = (Invoice) o;
        return id != null && Objects.equals(id, invoice.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
