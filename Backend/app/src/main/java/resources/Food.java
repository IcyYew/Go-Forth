package resources;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import player.Player;
import troops.TroopManager;
import troops.TroopTypes;

@Entity
@DiscriminatorValue("FOOD")
public class Food extends Resource{
    private int consumptionRate; // per hour

    public Food(ResourceManager resourceManager, int quantity) {
        super(ResourceType.FOOD, quantity, resourceManager);
    }

    public Food() {

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
