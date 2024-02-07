package troops;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


import static troops.TroopTypes.*;


@JsonSerialize(using = TroopManagerSerializer.class)
public class TroopManager {
    private Map<TroopTypes, Integer> troopsCounts;
    private int archerNum = 0;
    private int warriorNum;
    private int mageNum;
    private int cavalryNum;
    private long totalTroopPower;

    public TroopManager() {
        this.troopsCounts = new HashMap<>();
        this.troopsCounts.put(ARCHER, 0);
        this.troopsCounts.put(WARRIOR, 0);
        this.troopsCounts.put(MAGE, 0);
        this.troopsCounts.put(CAVALRY, 0);

    }

    public int getTroopNum(TroopTypes troopType) {
        int troopNum = 0;
        switch (troopType) {
            case ARCHER, WARRIOR, CAVALRY, MAGE:
                troopNum = troopsCounts.get(troopType);
        }
        return troopNum;
    }

    public long calculateTotalTroopPower() {
        totalTroopPower = 0;
        totalTroopPower += troopsCounts.get(ARCHER) * new Archer().getPower();
        totalTroopPower += troopsCounts.get(MAGE) * new Mage().getPower();
        totalTroopPower += troopsCounts.get(WARRIOR) * new Warrior().getPower();
        totalTroopPower += troopsCounts.get(CAVALRY) * new Cavalry().getPower();
        return totalTroopPower;
    }

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
