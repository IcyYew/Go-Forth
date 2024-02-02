package coms309.people;


import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Provides the Definition/Structure for the people row
 *
 * @author Vivek Bengre
 */

public class Person {

    private String firstName;

    private String lastName;

    private String address;

    private String telephone;
    private int ID;
    private String creationTime;

    public Person(){
        
    }

    public Person(String firstName, String lastName, String address, String telephone, int count, String currentTime){
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.telephone = telephone;
        this.ID = count;
        this.creationTime = currentTime;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setID(int count) {
        this.ID = count;
    }

    public int getID() {
        return this.ID;
    }
    public void setCreationTime(String ldt) {
        this.creationTime = ldt;
    }

    public String getCreationTime() {
        return this.creationTime;
    }

    @Override
    public String toString() {
        return firstName + " " 
               + lastName + " "
               + address + " "
               + telephone + " "
                + creationTime + " "
                + ID;
    }
}
