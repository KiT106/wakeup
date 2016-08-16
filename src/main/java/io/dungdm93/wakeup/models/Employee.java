package io.dungdm93.wakeup.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    public long id;

    public String name;
    public Integer age;

    @ManyToOne
    public Address address;

    @Override
    public String toString() {
        return String.format("Employee: [%s, %d]", name, age);
    }
}
