package resources;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Class for the Platinum resource.
 * @author Michael Geltz
 */
@Entity
@DiscriminatorValue("PLATINUM")
public class Platinum extends Resource{


    /**
     * Constructor for Platinum.
     * Takes a resourceManager and quantity.
     * @param resourceManager
     * @param quantity Quantity of Platinum.
     */
    public Platinum(ResourceManager resourceManager, int quantity) {
        super(ResourceType.PLATINUM, quantity, resourceManager);
    }

    /**
     * Empty constructor for Platinum.
     */
    public Platinum() {

    }
}
