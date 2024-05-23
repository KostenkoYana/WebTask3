package lt.viko.eif.kostenko.rest.model;

import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

@Entity
public class Client extends RepresentationModel<Client> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    private String name;
    private String lastName;

    public Client() {
    }

    @Override
    public String toString() {
        return String.format("\t\tName of client: %s\n" +
                "\t\tLastname of client: %s\n" +
                "\t\t_______________\n", this.name, this.lastName);
    }

    public Client(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
