package io.dungdm93.wakeup.models;

public class Address {
    public String street;
    public String city;
    public String nation;

    @Override
    public String toString() {
        return String.format("Address: %s %s, %s", street, city, nation);
    }
}
