package io.dungdm93.wakeup.models;

import io.dungdm93.wakeup.type.JsonBinaryType;
import io.dungdm93.wakeup.type.JsonStringType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Map;

import static javax.persistence.GenerationType.IDENTITY;

@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    public long id;

    public String name;
    public Integer age;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    public Map<String, Address> addresses;

    @Override
    public String toString() {
        return String.format("Employee: [%s, %d]", name, age);
    }
}
