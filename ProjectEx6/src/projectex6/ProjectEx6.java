/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this tstulate file, choose Tools | Tstulates
 * and open the tstulate in the editor.
 */
package projectex6;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.util.ArrayList;
import java.math.BigDecimal;


/**
 *
 * @author SugarDOT
 */
public class ProjectEx6 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
// insert data
        Student stu1 = new Student(3, "John", 12345.0);
        Student stu2 = new Student(4, "Marry", 45678.0);
        StudentTable.insertStudent(stu1);
        StudentTable.insertStudent(stu2);
// remove & update data
//        Student stu;
//        stu = StudentTable.findStudentById(1);
//        if (stu != null) {
//            stu.setName("Ohm");
//            StudentTable.removeStudent(stu);
//            StudentTable.updateStudent(stu);
//        }
        
        List<Student> stuList = StudentTable.findAllStudent();
        printAllStudent(stuList);
    }

    private static void printAllStudent(List<Student> stuList) {
        for(Student stu : stuList) {
           System.out.print(stu.getId() + " ");
           System.out.print(stu.getName() + " ");
           System.out.println(stu.getGpa() + " ");
       }
    }

    public static void persist(Object object) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjectEx6PU");
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
