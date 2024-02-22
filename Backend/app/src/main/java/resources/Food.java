package resources;

import player.Player;
import troops.TroopManager;
import troops.TroopTypes;

public class Food extends Resource{
    private int consumptionRate; // per hour
    private final ResourceType resourceType = ResourceType.FOOD;

    public Food(int amount, int consumptionRate) {
        super(amount);
        this.consumptionRate = consumptionRate;
    }

    public void calculateConsumptionRate(Player player) {
        TroopManager tTM = player.getTroops();
        this.consumptionRate = 0;
        this.consumptionRate += tTM.getTroopNum(TroopTypes.ARCHER) * 10;
        this.consumptionRate += tTM.getTroopNum(TroopTypes.MAGE) * 10;
        this.consumptionRate += tTM.getTroopNum(TroopTypes.CAVALRY) * 10;
        this.consumptionRate += tTM.getTroopNum(TroopTypes.WARRIOR) * 10;
    }
}
