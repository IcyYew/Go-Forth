package troops;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


import static troops.TroopTypes.*;

// Serializer for the troopmanager because it was impossible to pass information into fields with the PlayerController on Postman
@JsonSerialize(using = TroopManagerSerializer.class)
public class TroopManager {
    // Map storing what trooptype a troop is and the quantity of that type possessed
    private long playerId;
    private Map<TroopTypes, Integer> troopsCounts;
    private int archerNum = 0;
    private int warriorNum;
    private int mageNum;
    private int cavalryNum;
    private long totalTroopPower;

    // Constructor for troopmanager just creates an empty hashmap of all trooptypes
    public TroopManager(long playerId) {
        this.playerId = playerId;
        this.troopsCounts = new HashMap<>();
        this.troopsCounts.put(ARCHER, 0);
        this.troopsCounts.put(WARRIOR, 0);
        this.troopsCounts.put(MAGE, 0);
        this.troopsCounts.put(CAVALRY, 0);
    }

    public long getPlayerId() {
        return this.playerId;
    }


    // Returns specific number of a trooptype currently owned
    public int getTroopNum(TroopTypes troopType) {
        return troopsCounts.get(troopType);
    }

    // calculates a troopmanagers total power taking into account each troops different power rating
    public long calculateTotalTroopPower() {
        totalTroopPower = 0;
        totalTroopPower += troopsCounts.get(ARCHER) * new Archer().getPower();
        totalTroopPower += troopsCounts.get(MAGE) * new Mage().getPower();
        totalTroopPower += troopsCounts.get(WARRIOR) * new Warrior().getPower();
        totalTroopPower += troopsCounts.get(CAVALRY) * new Cavalry().getPower();
        return totalTroopPower;
    }

    // Add troop(s) to troopmanager
    public void addTroop(TroopTypes troopType, int quantity) {
        switch (troopType) {
            case ARCHER :
                archerNum += quantity;
                troopsCounts.put(ARCHER, archerNum);
                break;
            case WARRIOR:
                warriorNum += quantity;
                troopsCounts.put(WARRIOR, warriorNum);
                break;
            case MAGE:
                mageNum += quantity;
                troopsCounts.put(MAGE, mageNum);
                break;
            case CAVALRY:
                cavalryNum += quantity;
                troopsCounts.put(CAVALRY, cavalryNum);
                break;
        }

    }
    // Remove troop(s) from troopmanager
    // Basic logic to make sure a specific troop type isnt deducted to a value below zero
    // !!!! Hidden issue here that sets player troop count to negative when a troopmanager is loaded with non-zero value
    public void removeTroop(TroopTypes troopType, int quantity) {
        switch (troopType) {
            case ARCHER:
                if (troopsCounts.get(ARCHER) != 0 && troopsCounts.get(ARCHER) >= quantity) {
                    archerNum-= quantity;
                    troopsCounts.put(ARCHER, archerNum);
                }
                break;
            case WARRIOR:
                if (troopsCounts.get(WARRIOR) != 0 && troopsCounts.get(WARRIOR) >= quantity) {
                    warriorNum -= quantity;
                    troopsCounts.put(WARRIOR, warriorNum);
                }
                break;
            case MAGE:
                if (troopsCounts.get(MAGE) != 0 && troopsCounts.get(MAGE) >= quantity) {
                    mageNum -= quantity;
                    troopsCounts.put(MAGE, mageNum);
                }
                break;
            case CAVALRY:
                if (troopsCounts.get(CAVALRY) != 0 && troopsCounts.get(CAVALRY) >= quantity) {
                    cavalryNum -= quantity;
                    troopsCounts.put(CAVALRY, cavalryNum);
                }
                break;
        }
    }

    @Override
    public String toString() {
        return "TroopManager{" +
                ", archerNum=" + troopsCounts.get(ARCHER) +
                ", warriorNum=" + troopsCounts.get(WARRIOR) +
                ", mageNum=" + troopsCounts.get(MAGE) +
                ", cavalryNum=" + troopsCounts.get(CAVALRY) +
                ", totalTroopPower=" + totalTroopPower +
                '}';
    }
}
