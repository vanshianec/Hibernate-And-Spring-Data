import entities.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Date;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("school");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        //SAVE objects

        //Student student = new Student("Teo", Date.valueOf("2018-03-09"));
        //em.persist(student);

        //READ DATA

        //Student student = em.find(Student.class,1);
        //System.out.println(student.getName());

        //DELETE objects

        //Student student = em.find(Student.class,2);
        //em.remove(student);

        em.getTransaction().commit();
    }
}
