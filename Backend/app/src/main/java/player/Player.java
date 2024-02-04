package player;

import buildings.BuildingManager;
import troops.TroopManager;

public class Player {
    TroopManager troops;
    //BuildingManager buildings;
    private int playerID;
    private double power;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
// ...


    public Player(TroopManager troops, int playerID, double power, String userName) {
        this.troops = troops;
        //this.buildings = buildings;
        this.playerID = playerID;
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
