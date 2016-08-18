package io.dungdm93.wakeup;

import io.dungdm93.wakeup.models.Address;
import io.dungdm93.wakeup.models.Employee;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WakeUp");
        EntityManager em = emf.createEntityManager();

        setUp(em);
        inquiry(em);

        em.close();
        emf.close();
    }

    public static void inquiry(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> q = cb.createQuery(Employee.class);
        Root<Employee> employee = q.from(Employee.class);

        q.select(employee).distinct(true)
                .where(cb.equal(employee.get("id"), 1L));
        TypedQuery<Employee> query = em.createQuery(q);

        Employee e = query.getSingleResult();
        System.out.println(e);
        e.addresses.forEach((type, address) -> System.out.printf("%s = %s%n", type, address));
    }

    public static void setUp(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // Employee
        Employee dung = new Employee();
        dung.name = "Dung Dang Minh";
        dung.age = 24;

        Address hanoi = new Address();
        hanoi.street = "Cau Giay";
        hanoi.city = "Ha Noi";
        hanoi.nation = "Vietnam";

        Address haiduong = new Address();
        haiduong.street = "Thanh Ha";
        haiduong.city = "Hai Duong";
        haiduong.nation = "Vietnam";

        Address thaibinh = new Address();
        thaibinh.street = "Tien Hai";
        thaibinh.city = "Thai Binh";
        thaibinh.nation = "Vietnam";

        dung.addresses = new HashMap<>();
        dung.addresses.put("Home", haiduong);
        dung.addresses.put("Work", hanoi);
        dung.addresses.put("Wife", thaibinh);

        em.persist(dung);
        tx.commit();
    }
}
