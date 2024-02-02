package player;

import buildings.BuildingManager;
import troops.TroopManager;

public class Player {
    TroopManager troops;
    //BuildingManager buildings;
    private int playerID;
    private double power;
    // ...


    public Player(TroopManager troops, int playerID, double power) {
        this.troops = troops;
        //this.buildings = buildings;
        this.playerID = playerID;
        this.power = power;
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

    public void setPower(double power) {
        this.power = troops.calculateTotalTroopPower();
        // ... more when implemented
    }

    @Override
    public String toString() {
        return "Player{" +
                "troops=" + troops +
                ", playerID=" + playerID +
                ", power=" + power +
                '}';
    }
}
