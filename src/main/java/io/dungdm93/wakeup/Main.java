package io.dungdm93.wakeup;

import io.dungdm93.wakeup.models.Address;
import io.dungdm93.wakeup.models.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WakeUp");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Employee employee = new Employee();
        employee.name = "DungDM";
        employee.age = 24;

        Address address = new Address();
        address.street = "Cau Giay";
        address.city = "Hanoi";
        address.nation = "Vietnam";

        em.persist(employee);
        em.persist(address);
        tx.commit();
        em.close();
        emf.close();
    }
}
