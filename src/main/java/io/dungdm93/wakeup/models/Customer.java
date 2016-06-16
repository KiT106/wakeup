package io.dungdm93.wakeup.models;

import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Audited
public class Customer {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    public int id;

    public String name;
    public int age;
    public String address;

    @OneToMany(mappedBy = "customer", cascade = ALL, orphanRemoval = true)
    public List<Order> orders = new ArrayList<>();
}
