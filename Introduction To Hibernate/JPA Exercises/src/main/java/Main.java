import contracts.Reader;
import contracts.Writer;
import entities.Address;
import entities.Employee;
import entities.Project;
import entities.Town;
import io.ConsoleReader;
import io.ConsoleWriter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Reader consoleReader = new ConsoleReader();
        Writer consoleWriter = new ConsoleWriter();
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("PersistenceUnit");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        //Ex. 1 Remove Objects
        //removeObjects(em);

        //Ex. 2 Contains Employee
        //containsEmployee(consoleReader, consoleWriter, em);

        //Ex. 3 Employees With Salary Over 50000
        //employeesWithSalaryOver(consoleWriter, em);

        //Ex. 4 Employees From Department
        //employeesFromDepartment(em);

        //Ex. 5 Adding a New Address And Updating Employee
        //addingNewAddressAndUpdatingEmployee(consoleReader, em);

        //Ex. 6 Addresses With Employee Count
        //addressesWithEmployeeCount(em, consoleWriter);

        //Ex. 7 Get Employee With Project
        //getEmployeeWithProject(consoleReader, em, consoleWriter);

        //Ex. 8 Find Latest 10 Projects
        //findLatestTenProjects(consoleWriter, em);

        //Ex. 9 Increase Salaries
        //increaseSalaries(em);

        //Ex. 10 Remove Towns
        //removeTowns(consoleReader, em);

        //Ex. 11 Find Employees By First Name
        //findEmployeesByFirstName(consoleReader, consoleWriter, em);

        //Ex. 12 Employees Maximum Salaries
        //employeesMaxSalaries(consoleWriter, em);

        em.getTransaction().commit();
    }

    private static void employeesMaxSalaries(Writer consoleWriter, EntityManager em) {
        em.createQuery("SELECT e FROM Employee AS e WHERE e.salary NOT BETWEEN 30000 AND 70000 GROUP BY e.department ORDER BY e.salary DESC", Employee.class)
                .getResultList()
                .forEach(e -> consoleWriter.writeLine(String.format("%s - %.2f", e.getDepartment().getName(), e.getSalary())));
    }

    private static void findEmployeesByFirstName(Reader consoleReader, Writer consoleWriter, EntityManager em) throws IOException {
        String pattern = consoleReader.readLine();

        StringBuilder builder = new StringBuilder();
        em.createQuery("SELECT e FROM Employee AS e WHERE e.firstName LIKE :pattern", Employee.class)
                .setParameter("pattern", pattern + "%")
                .getResultList()
                .forEach(e -> builder.append(String.format("%s %s - %s - ($%.2f)%n",
                        e.getFirstName(), e.getLastName(), e.getJobTitle(), e.getSalary())));
        consoleWriter.writeLine(builder.toString().trim());
    }

    private static void removeTowns(Reader consoleReader, EntityManager em) throws IOException {
        String townName = consoleReader.readLine();

        Town town = em.createQuery("SELECT t FROM Town AS t WHERE t.name = :name", Town.class)
                .setParameter("name", townName)
                .getResultList()
                .get(0);

        List<Address> addressesToRemove = new ArrayList<>();
        em.createQuery("SELECT a FROM Address AS a WHERE a.town.id = :id", Address.class)
                .setParameter("id", town.getId())
                .getResultList()
                .forEach(a -> {
                    addressesToRemove.add(a);
                    a.getEmployees().forEach(e -> e.setAddress(null));
                });

        for (Address address : addressesToRemove) {
            em.remove(address);
        }

        em.remove(town);
    }

    private static void increaseSalaries(EntityManager em) {
        em.createQuery("SELECT e FROM Employee AS e " +
                "WHERE e.department.name IN('Engineering', 'Tool Design', 'Marketing', 'Information Services')", Employee.class)
                .getResultList()
                .forEach(e -> {
                    e.setSalary(e.getSalary().multiply(new BigDecimal(1.12)));
                    em.persist(e);
                });
    }

    private static void findLatestTenProjects(Writer consoleWriter, EntityManager em) {
        StringBuilder builder = new StringBuilder();
        em.createQuery("Select p FROM Project AS p ORDER BY p.startDate DESC, p.name", Project.class)
                .setMaxResults(10)
                .getResultList()
                .forEach(p -> builder.append(String.format("Project name: %s%n\tProject Description: %s%n" +
                                "\tProject Start Date: %s%n\tProject End Date: %s%n", p.getName(), p.getDescription(),
                        p.getStartDate(), p.getEndDate())));
        consoleWriter.writeLine(builder.toString().trim());
    }

    private static void getEmployeeWithProject(Reader consoleReader, EntityManager em, ConsoleWriter consoleWriter) throws IOException {
        int employeeId = Integer.parseInt(consoleReader.readLine());
        StringBuilder builder = new StringBuilder();
        Employee e = em.find(Employee.class, employeeId);
        builder.append(String.format("%s %s - %s%n", e.getFirstName(), e.getLastName(), e.getJobTitle()));
        e.getProjects().stream()
                .sorted((a, b) -> a.getName().compareTo(b.getName()))
                .forEach(p -> builder.append(String.format("\t%s%n", p.getName())));
        consoleWriter.writeLine(builder.toString().trim());
    }

    private static void addressesWithEmployeeCount(EntityManager em, ConsoleWriter consoleWriter) {
        StringBuilder addresses = new StringBuilder();

        em.createQuery("SELECT a FROM Address AS a ORDER BY a.employees.size DESC, a.town.id", Address.class)
                .setMaxResults(10)
                .getResultList()
                .forEach(a -> addresses.append(String.format("%s, %s - %d employees%n", a.getText(), a.getTown().getName(), a.getEmployees().size())));
        consoleWriter.writeLine(addresses.toString().trim());
    }

    private static void addingNewAddressAndUpdatingEmployee(Reader consoleReader, EntityManager em) throws IOException {
        String employeeName = consoleReader.readLine();

        Town town = new Town();
        town.setName("Sofia");

        Address address = new Address();
        address.setText("Vitoshka 15");
        address.setTown(town);

        Employee employee = em.createQuery("SELECT e FROM Employee AS e WHERE e.lastName = :name", Employee.class)
                .setParameter("name", employeeName)
                .getResultList().get(0);
        employee.setAddress(address);

        em.persist(town);
        em.persist(address);
        em.persist(employee);
    }

    private static void employeesFromDepartment(EntityManager em) {
        em.createQuery("SELECT e FROM Employee AS e WHERE e.department.name = 'Research and Development' ORDER BY e.salary, e.id",
                Employee.class)
                .getResultList()
                .forEach(e -> System.out.printf("%s %s from %s - $%.2f%n",
                        e.getFirstName(), e.getLastName(), e.getDepartment().getName(), e.getSalary()));
    }

    private static void employeesWithSalaryOver(Writer consoleWriter, EntityManager em) {
        em.createQuery("SELECT e FROM Employee AS e WHERE e.salary > 50000", Employee.class)
                .getResultList()
                .forEach(e -> consoleWriter.writeLine(e.getFirstName()));
    }

    private static void containsEmployee(Reader consoleReader, Writer consoleWriter, EntityManager em) throws IOException {
        String name = consoleReader.readLine();
        consoleWriter.writeLine(name);

        List<Employee> employees = em.createQuery("SELECT e FROM Employee AS e WHERE CONCAT(e.firstName,' ',e.lastName) = :name", Employee.class)
                .setParameter("name", name)
                .getResultList();

        if (!employees.isEmpty()) {
            consoleWriter.writeLine("Yes");
        } else {
            consoleWriter.writeLine("No");
        }
    }

    private static void removeObjects(EntityManager em) {
        List<Town> towns = em.createQuery("SELECT t FROM Town AS t", Town.class).getResultList();

        for (Town town : towns) {
            if (town.getName().length() > 5) {
                em.detach(town);
            }
        }

        towns.stream().filter(t -> em.contains(t))
                .forEach(t -> {
                    t.setName(t.getName().toLowerCase());
                    em.persist(t);
                });
    }
}
