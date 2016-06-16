package io.dungdm93.wakeup.models;

import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Audited
public class Product {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    public int id;

    public String name;
    public BigDecimal price;
    public String category;

    @ManyToMany(mappedBy = "products")
    public List<Order> orders = new ArrayList<>();
}
