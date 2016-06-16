package io.dungdm93.wakeup.models;

import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Audited
public class Order {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    public int id;

    @ManyToOne
    public Customer customer;

    @ManyToMany
    public List<Product> products = new ArrayList<>();
}
