/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inheritanceorm22;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author SugarDOT 
 */
public class InheritanceORM22 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // insert ค่า
        FulltimeEmployee emp1 = new FulltimeEmployee();
        emp1.setName("John");
        emp1.setSalary(5000);
        ParttimeEmployee emp2 = new ParttimeEmployee();
        emp2.setName("Jane");
        emp2.setHoursWork(4);
        persist(emp1);
        persist(emp2);
        // update ค่า
        ParttimeEmployee pempp = new ParttimeEmployee();
        pempp.setId(1);
        pempp.setName("Jame11");
        pempp.setHoursWork(15);
        persist(pempp);
        // remove ค่า
        removeEmployee(1);
    }
    
    public static void removeEmployee(int id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("InheritanceORM22PU");
        EntityManager em = emf.createEntityManager();
        FulltimeEmployee femp = em.find(FulltimeEmployee.class, id);
        em.getTransaction().begin();
        try {
            em.remove(femp);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
    public static void updateEmployee(FulltimeEmployee fulltimeemployee) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("InheritanceORM22PU");
        EntityManager em = emf.createEntityManager();
        FulltimeEmployee femp = em.find(FulltimeEmployee.class, fulltimeemployee.getId());
        femp.setName(fulltimeemployee.getName());
        femp.setSalary(fulltimeemployee.getSalary());
        em.getTransaction().begin();
        try {
            em.persist(femp);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public static void persist(Object object) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("InheritanceORM22PU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
}
