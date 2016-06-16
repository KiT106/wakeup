package io.dungdm93.wakeup;

import io.dungdm93.wakeup.models.Customer;
import io.dungdm93.wakeup.models.Order;
import io.dungdm93.wakeup.models.Product;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WakeUp");
        EntityManager em = emf.createEntityManager();

//        System.out.println("===================== Set up =====================");
//        setUp(em);
//        System.out.println("===================== Update =====================");
//        em.clear();
//        update(em);
        inquiry(em);

        em.close();
        emf.close();
    }

    private static void inquiry(EntityManager em) {
        AuditReader reader = AuditReaderFactory.get(em);
        Customer customer = reader.find(Customer.class, 1, 1);
        System.out.println("Order size: " + customer.orders.size());
        for (Order order : customer.orders) {
            System.out.println("===== Order =====");
            for (Product product : order.products) {
                System.out.println(product.id);
            }
        }
    }

    public static void update(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Product clothes = em.find(Product.class, 2);
        clothes.name = "M2";
        clothes.price = BigDecimal.valueOf(11);

        Customer bondm = em.find(Customer.class, 2);
        bondm.name = "Bon DM";
        bondm.age = 27;
        bondm.address = "Thanh Ha, Hai Duong";

        Order newOrder = new Order();
        newOrder.customer = bondm;
        newOrder.products.add(clothes);

        Customer dungdm = em.find(Customer.class, 1);
        dungdm.orders.remove(dungdm.orders.size() - 1);
        Order oldOrder = dungdm.orders.get(0);
        oldOrder.products.add(clothes);
        oldOrder.products.remove(em.find(Product.class, 4));

        em.persist(newOrder);
        tx.commit();
    }

    public static void setUp(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Customer dungdm = new Customer();
        dungdm.name = "Dang Minh Dung";
        dungdm.age = 24;
        dungdm.address = "Ha Noi";

        Customer bondm = new Customer();
        bondm.name = "Dang Manh Bon";
        bondm.age = 25;
        bondm.address = "Hai Duong";

        Customer trangpth = new Customer();
        trangpth.name = "Pham Thi Hoai Trang";
        trangpth.age = 20;
        trangpth.address = "Thai Binh";

        // ===== Product =====
        Product laptop = new Product();
        laptop.name = "Laptop";
        laptop.category = "Computer";
        laptop.price = BigDecimal.valueOf(100);

        Product clothes = new Product();
        clothes.name = "Blue";
        clothes.category = "Clothes";
        clothes.price = BigDecimal.valueOf(9);

        Product iphone = new Product();
        iphone.name = "iPhone";
        iphone.category = "Smart Phone";
        iphone.price = BigDecimal.valueOf(200);

        Product book = new Product();
        book.name = "Book";
        book.category = "Stationery";
        book.price = BigDecimal.valueOf(5);

        Product bread = new Product();
        bread.name = "Bread";
        bread.category = "Foot";
        bread.price = BigDecimal.valueOf(1);

        // ===== Order =====
        Order order1 = new Order();
        order1.customer = dungdm;
        order1.products = Arrays.asList(laptop, iphone, book);

        Order order2 = new Order();
        order2.customer = bondm;
        order2.products = Arrays.asList(book, bread, iphone);

        Order order3 = new Order();
        order3.customer = dungdm;
        order3.products = Arrays.asList(book, laptop, bread);

        Order order4 = new Order();
        order4.customer = trangpth;
        order4.products = Arrays.asList(bread, book, iphone);

        em.persist(dungdm);
        em.persist(bondm);
        em.persist(trangpth);

        em.persist(laptop);
        em.persist(clothes);
        em.persist(iphone);
        em.persist(book);
        em.persist(bread);

        em.persist(order1);
        em.persist(order2);
        em.persist(order3);
        em.persist(order4);
        tx.commit();
    }
}
