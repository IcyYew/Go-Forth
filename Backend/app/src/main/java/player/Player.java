package player;

import buildings.BuildingManager;
import jakarta.persistence.*;
import resources.ResourceManager;
import troops.TroopManager;

@Entity
public class Player {
    // The player class will be the merging of all "managers", could be said to be the manager of managers
    // This is the class that will be "communicating" with a server via the PlayerController class

    //BuildingManager buildings;
    // Each player has an ID, currently juse starting at 1 and going from there, will eventualyl be some sort of
    // Random number or Hash generation for security
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer playerID;
    private double power;
    // Each player has a userName, eventually a check to make sure an already existing userName isn't used again will be
    // put in place
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "player")
    ResourceManager resources;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "player")
    TroopManager troops;
    private String userName;
    private String password; // might add encryption, not sure if necessary

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


    // Player constructor, will be expanded as more managers are completed
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
