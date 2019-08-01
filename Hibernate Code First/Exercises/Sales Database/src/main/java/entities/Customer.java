package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "customers")
public class Customer extends BaseEntity{

    private String name;
    private String email;
    private int creditCardNumber;
    private Set<Sale> sales;

    public Customer() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCreditCardNumber(int creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    @Column(name = "credit_card_number")
    public int getCreditCardNumber() {
        return creditCardNumber;
    }


    public void setSales(Set<Sale> sales) {
        this.sales = sales;
    }

    @OneToMany(targetEntity = Sale.class, mappedBy = "customer")
    public Set<Sale> getSales() {
        return sales;
    }
}
