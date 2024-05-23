package lt.viko.eif.kostenko.rest.model;

import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

@Entity
public class Contacts extends RepresentationModel<Contacts> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String mail;
    private String phone;

    /**
     * Default constructor.
     */
    public Contacts() {
    }

    /**
     * Parameterized constructor to initialize contact information with specified attributes.
     * @param mail  The email address of the hostel.
     * @param phone The phone number of the hostel.
     */
    public Contacts(String mail, String phone) {
        this.mail = mail;
        this.phone = phone;
    }

    /**
     * Generates a string representation of the contact information.
     * @return A string containing the email address and phone number of the hostel.
     */
    @Override
    public String toString() {
        return String.format("\n\t\tMail: %s\n" +
                "\t\tPhone: %s", this.mail, this.phone);
    }

    /**
     * Retrieves the ID of the contact information.
     * @return The ID of the contact information.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the contact information.
     * @param id The ID of the contact information.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the email address of the hostel.
     * @return The email address of the hostel.
     */
    public String getMail() {
        return mail;
    }

    /**
     * Sets the email address of the hostel.
     * @param mail The email address of the hostel.
     */
    public void setMail(String mail) {
        this.mail = this.mail;
    }

    /**
     * Retrieves the phone number of the hostel.
     * @return The phone number of the hostel.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the hostel.
     * @param phone The phone number of the hostel.
     */
    public void setPhone(String phone) {
        this.phone = this.phone;
    }
}
