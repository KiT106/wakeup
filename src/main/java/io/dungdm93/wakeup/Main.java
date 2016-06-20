package io.dungdm93.wakeup;

import io.dungdm93.wakeup.models.Address;
import io.dungdm93.wakeup.models.Employee;
import io.dungdm93.wakeup.models.Product;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Root;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WakeUp");
        EntityManager em = emf.createEntityManager();

        setUp(em);

        em.close();
        emf.close();
    }

    public static void inquiry(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> q = cb.createQuery(Employee.class);
        Root<Employee> employee = q.from(Employee.class);
        ListJoin<Employee, Address> address = employee.joinList("addresses");

        q.select(employee).distinct(true)
                .where(cb.like(address.get("city"), "%Binh%"));
        TypedQuery<Employee> query = em.createQuery(q);
        query.getResultList().forEach(System.out::println);
    }

    public static void setUp(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Product laptop = new Product();
        laptop.name = "ASUS Q500A";

        em.persist(laptop);
        tx.commit();
    }
}
