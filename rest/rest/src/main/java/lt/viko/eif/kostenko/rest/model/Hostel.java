package lt.viko.eif.kostenko.rest.model;

import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Hostel extends RepresentationModel<Hostel> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String address;
    @OneToOne(targetEntity = Admin.class, cascade = CascadeType.ALL)
    private Admin admin;
    @OneToOne(targetEntity = Contacts.class, cascade = CascadeType.ALL)
    private Contacts contacts;
    @OneToMany(
            targetEntity = Room.class,
            cascade = {CascadeType.ALL}
    )
    private List<Room> rooms = new ArrayList<>();
    @OneToMany(
            targetEntity = Client.class,
            cascade = {CascadeType.ALL}
    )
    private List<Client> clients = new ArrayList<>();

    public Hostel() {
    }

    public Hostel(String name, String address) {
        this.name = name;
        this.address = address;
    }

    @Override
    public String toString() {
        return String.format("Hostel: \n" +
                        "\tName: %s\n" +
                        "\tAddress of hostel: %s\n" +
                        "\tContacts: %s\n" +
                        "\tAdmin: %s\n" +
                        "\tRooms: \n %s" +
                        "\tClients: \n%s", this.name, this.address,
                this.contacts, this.admin, constructRoomList(), constructClientList());
    }

    public String constructRoomList(){
        String result = "";
        for (Room room : this.rooms){
            result += room;
        }
        return result;
    }

    public String constructClientList(){
        String result = "";
        for (Client client : this.clients){
            result += client;
        }
        return result;
    }

    /**
     * Getters and setters for all properties
     */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Contacts getContacts() {
        return contacts;
    }

    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
    }

    public Admin getAdmin() { return admin; }

    public void setAdmin(Admin admin) { this.admin = admin; }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Client> getClients() { return clients; }

    public void setClients(List<Client> clients) { this.clients = clients; }
}