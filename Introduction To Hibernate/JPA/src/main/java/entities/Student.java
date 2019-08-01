package entities;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "students")
public class Student {
    private int id;
    private String name;
    private Date registrationDate;

    public Student() {
    }

    public Student(String name, Date registrationDate) {
        this.name = name;
        this.registrationDate = registrationDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    @Column(name = "first_name")
    public String getName() {
        return name;
    }

    @Column(name = "registration_date")
    public Date getRegistrationDate() {
        return registrationDate;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

}
