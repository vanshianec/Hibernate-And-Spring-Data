import entities.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Configuration cfg = new Configuration();
        cfg.configure();
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        //CREATE DATA

        //Student student = new Student("Pesho", Date.valueOf("2019-02-03"));
        //session.save(student);

        //RETRIEVE DATA

        //Student student = session.get(Student.class, 1);
        //System.out.println(student.getName());
        //System.out.println(student.getRegistrationDate());

        //RETRIEVE DATA BY QUERY

        //List<Student> studentList =
        //        session.createQuery("FROM Student ").list();
        //for (Student student : studentList) {
        //    System.out.println(student.getId());
        //    System.out.println(student.getName());
        //}

        //RETRIEVE DATA BY CRITERIA

        //CriteriaBuilder builder = session.getCriteriaBuilder();
        //CriteriaQuery criteria = builder.createQuery();
        //Root<Student> r = criteria.from(Student.class);
        //criteria.select(r).where(builder.like(r.<String>get("name"), "P%"));
        //List<Student> studentList =
        //        session.createQuery(criteria).getResultList();
        //for (Student student : studentList) {
        //    System.out.println(student.getName());
        //}


        session.getTransaction().commit();
        session.close();
    }
}
