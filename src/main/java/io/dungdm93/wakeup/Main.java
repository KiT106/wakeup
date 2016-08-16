package io.dungdm93.wakeup;

import io.dungdm93.wakeup.models.Address;
import io.dungdm93.wakeup.models.Employee;

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

        // Employee
        Employee bon = new Employee();
        bon.name = "Bon Dang Manh";
        bon.age = 25;

        Employee dung = new Employee();
        dung.name = "Dung Dang Minh";
        dung.age = 24;

        Employee trang = new Employee();
        trang.name = "Pham Thi Hoai Trang";
        trang.age = 20;


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

        bon.address = haiduong;
        dung.address = hanoi;
        trang.address = thaibinh;

        em.persist(hanoi);
        em.persist(haiduong);
        em.persist(thaibinh);
        em.persist(bon);
        em.persist(dung);
        em.persist(trang);
        tx.commit();
    }
}
