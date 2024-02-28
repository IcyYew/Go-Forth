package player;

import buildings.BuildingManager;
import jakarta.persistence.*;
import resources.ResourceManager;
import troops.TroopManager;

@Entity
@Table(name="player")
public class Player {
    // The player class will be the merging of all "managers", could be said to be the manager of managers
    // This is the class that will be "communicating" with a server via the PlayerController class

    // Each player has an ID, currently just starting at 1 and going from there, will eventually be some sort of
    // Random number or Hash generation for security

    //Identifier for database table, also the only "unqiue" method of differentiating players currently
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer playerID;

    //A players commulative power
    @Column(name="power")
    private double power;
    //Resource manager storing and managing a players resources
    @ManyToOne(cascade = CascadeType.ALL)
    ResourceManager resources;

    //Troop manager storing and managing a players troops
    @ManyToOne(cascade = CascadeType.ALL)
    TroopManager troops;

    //A players self-created username
    @Column(name="username")
    private String userName;


    //A players password
    @Column(name="password")
    private String password; // might add encryption, not sure if necessary

    //Empty constructor to make Jpa happy
    public Player() {

    }


    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    // ... further developments will take more setters/getters/etc


    // Player constructor, will be expanded for building manager
    public Player(ResourceManager resources, TroopManager troops, int playerID, double power, String userName, String password) {
        setResources(resources);
        setTroops(troops);
        //this.buildings = buildings;
        setPlayerID(playerID);
        setPower(power);
        setUserName(userName);
        setPassword(password);
    }

    public void setResources(ResourceManager resources) {
        this.resources = resources;
    }


    public ResourceManager getResources() {
        return resources;
    }

    public TroopManager getTroops() {
        return troops;
    }

    public void setTroops(TroopManager troops) {
        this.troops = troops;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public double getPower() {
        return power;
    }

    public void updatePower() {
        this.power = troops.calculateTotalTroopPower();
    }

    public void setPower(double power) {
        this.power = troops.calculateTotalTroopPower();
        // ... more when implemented
    }


    @Override
    public String toString() {
        return "Player{" +
                "username" + userName +
                "password" + password +
                "resources=" + resources +
                "troops=" + troops +
                ", playerID=" + playerID +
                ", power=" + power +
                '}';
    }
}
