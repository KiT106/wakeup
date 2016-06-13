package io.dungdm93.wakeup;

import io.dungdm93.wakeup.models.Address;
import io.dungdm93.wakeup.models.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WakeUp");
        EntityManager em = emf.createEntityManager();

        setUp(em);

        em.close();
        emf.close();
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

        bon.addresses = Arrays.asList(hanoi, haiduong);
        dung.addresses = Arrays.asList(haiduong, thaibinh);
        trang.addresses = Arrays.asList(thaibinh, hanoi);

        em.persist(hanoi);
        em.persist(haiduong);
        em.persist(thaibinh);
        em.persist(bon);
        em.persist(dung);
        em.persist(trang);
        tx.commit();
    }
}
