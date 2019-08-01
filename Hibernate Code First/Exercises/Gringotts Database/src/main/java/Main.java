import entities.Wizard;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.sql.Date;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("wizard");
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();

        Wizard wizard = new Wizard();

        wizard.setFirstName("Pesho");
        wizard.setLastName("The Magician");
        wizard.setAge((short) 23);
        wizard.setDepositAmount(new BigDecimal("2.12"));
        wizard.setDepositCharge(new BigDecimal("2.15"));
        wizard.setDepositInterest(new BigDecimal("4.12"));
        wizard.setDepositExpired(false);
        wizard.setDepositExpirationDate(Date.valueOf("2019-04-05"));
        wizard.setDepositGroup("Some deposit group");
        wizard.setDepositStartDate(Date.valueOf("2018-03-12"));
        wizard.setMagicWandCreator("Misho");
        wizard.setMagicWandSize((short) 2);
        wizard.setNotes("One day I will become hokage");

        em.persist(wizard);

        em.getTransaction().commit();
        em.close();
    }
}
