package io.dungdm93.wakeup.models;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Product {
    /**
     * 1. Some databases (PostgreSQL, SQL Server) offer a dedicated UUID storage type
     * 2. Otherwise we can store the bits as a byte array (e.g. RAW(16) in Oracle or the standard BINARY(16) type)
     * 3. Alternatively we can use 2 bigint (64-bit) columns, but a composite identifier
     * is less efficient than a single column one
     * 4. We can store the hex value in a CHAR(36) column (e.g 32 hex values and 4 dashes),
     * but this will take the most amount of space, hence it’s the least efficient alternative
     *
     * @see <a href="https://vladmihalcea.com/2014/07/01/hibernate-and-uuid-identifiers/">Hibernate UUID</a>
     */
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2") // UUIDGenerator registered under the “uuid2” type
    @Type(type = "uuid-char")                             // org.hibernate.type.UUIDCharType : 8-4-4-4-12 digit
    // @Column(columnDefinition = "CHAR(36)")
    public UUID id;

    public String name;

    @Override
    public String toString() {
        return String.format("(%s)%s", id, name);
    }
}
