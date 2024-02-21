package player;

import buildings.BuildingManager;
import troops.TroopManager;

public class Player {
    // The player class will be the merging of all "managers", could be said to be the manager of managers
    // This is the class that will be "communicating" with a server via the PlayerController class

    TroopManager troops;
    //BuildingManager buildings;
    // Each player has an ID, currently juse starting at 1 and going from there, will eventualyl be some sort of
    // Random number or Hash generation for security
    private int playerID;
    private double power;
    // Each player has a userName, eventually a check to make sure an already existing userName isn't used again will be
    // put in place
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
// ... further developments will take more setters/getters/etc


    // Player constructor, will be expanded as more managers are completed
    public Player(TroopManager troops, int playerID, double power, String userName) {
        //this.buildings = buildings;
        this.playerID = playerID;
        this.troops = troops;
        this.power = power;
        this.userName = userName;
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
                "troops=" + troops +
                ", playerID=" + playerID +
                ", power=" + power +
                '}';
    }
}
