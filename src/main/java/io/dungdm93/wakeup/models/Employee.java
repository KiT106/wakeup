package io.dungdm93.wakeup.models;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static io.dungdm93.wakeup.type.JsonStringType.JAVA_TYPE;
import static io.dungdm93.wakeup.type.JsonStringType.SQL_TYPE;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    public long id;

    public String name;
    public Integer age;

    @Type(
            type = "io.dungdm93.wakeup.type.JsonStringType",
            parameters = {
                    @Parameter(name = JAVA_TYPE, value = "io.dungdm93.wakeup.models.Address"),
                    @Parameter(name = SQL_TYPE, value = "2005")
            }
    )
    @Column(name = "ADDRESS", columnDefinition = "VARCHAR")
    public Address address;

    @Override
    public String toString() {
        return String.format("Employee: [%s, %d]", name, age);
    }
}
