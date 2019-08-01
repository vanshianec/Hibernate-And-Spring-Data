package entities;

import java.sql.Date;

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

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }
}
