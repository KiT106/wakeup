package io.dungdm93.wakeup.models;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    public long id;

    public String name;
    public Integer age;


    @ManyToMany
    @JoinTable(
            name = "employee_address",
            joinColumns = @JoinColumn(name = "employeeId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "addressId", referencedColumnName = "id")
    )
    public List<Address> addresses;

    @Override
    public String toString() {
        return String.format("Employee: [%s, %d]", name, age);
    }
}
