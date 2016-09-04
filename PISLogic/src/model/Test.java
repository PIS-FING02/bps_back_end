package model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class Test {
     private static EntityManagerFactory factory;

     public static void main(String[] args) {
          factory = Persistence.createEntityManagerFactory("postgresUnit");
          EntityManager em = factory.createEntityManager();
          // Read the existing entries and write to console
          Query q = em.createQuery("SELECT s FROM Sector s");
          List<Sector> userList = q.getResultList();
          for (Sector user : userList) {
               System.out.println(user.getNombre());
          }
          System.out.println("Size: " + userList.size());

          // Create new user
          em.getTransaction().begin();
          Sector s = new Sector();
          s.setNombre("Tom Johnson");
          em.persist(s);
          em.getTransaction().commit();

          em.close();
     }
}