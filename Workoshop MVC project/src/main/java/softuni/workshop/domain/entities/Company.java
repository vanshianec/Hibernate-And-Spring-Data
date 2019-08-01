package softuni.workshop.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "companies")
public class Company extends BaseEntity {

    private String name;
    private Set<Project> projects;

    public Company() {
        this.projects = new HashSet<>();
    }

    @Column(name = "name", nullable = false)
    @Size(min = 3, max = 20, message = "Company name should be between 3 and 20 symbols")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
}
