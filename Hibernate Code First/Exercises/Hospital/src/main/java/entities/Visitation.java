package entities;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "visitations")
public class Visitation extends BaseEntity {

    private Date date;
    private String comment;
    private Patient patient;

    public Visitation() {
    }

    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name = "comment")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @ManyToOne(targetEntity = Patient.class)
    @JoinColumn(name = "patient_id" , referencedColumnName = "id")
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

}
