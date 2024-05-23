package lt.viko.eif.kostenko.rest.model;

import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

@Entity
public class Admin extends RepresentationModel<Admin> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String lastName;
    private int expirence;

    public Admin() {
    }

    public Admin(String name, String lastName, int expirence) {
        this.name = name;
        this.lastName = lastName;
        this.expirence = expirence;
    }

    @Override
    public String toString() {
        return String.format("\n\t\tName: %s\n" +
                "\t\tLastname: %s\n" +
                "\t\tExpirince: %s", this.name, this.lastName, this.expirence);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getExpirence() {
        return expirence;
    }

    public void setExpirence(int expirence) {
        this.expirence = expirence;
    }
}
