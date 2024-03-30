package resources;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import player.Player;
import troops.TroopManager;
import troops.TroopTypes;

/**
 * The Class for the Food Resource.
 * This class contains the food resource and its consumption calculation.
 * @author Michael Geltz
 */
@Entity
@DiscriminatorValue("FOOD")
public class Food extends Resource{
    private int consumptionRate; // per hour

    /**
     * Constructor for Food.
     * Takes a resourceManager and a quantity as input.
     * @param resourceManager
     * @param quantity Quantity of food.
     */
    public Food(ResourceManager resourceManager, int quantity) {
        super(ResourceType.FOOD, quantity, resourceManager);
    }

    /**
     * Empty Constructor for Food.
     */
    public Food() {

    }

    /**
     * Calculates the consumption rate of food for a player based on the types and number of troops in an army.
     * @param player Player for which consumption is being calculated.
     */
    public void calculateConsumptionRate(Player player) {
        TroopManager tTM = player.getTroops();
        this.consumptionRate = 0;
        this.consumptionRate += tTM.getTroopNum(TroopTypes.ARCHER) * 10;
        this.consumptionRate += tTM.getTroopNum(TroopTypes.MAGE) * 10;
        this.consumptionRate += tTM.getTroopNum(TroopTypes.CAVALRY) * 10;
        this.consumptionRate += tTM.getTroopNum(TroopTypes.WARRIOR) * 10;
    }
}
