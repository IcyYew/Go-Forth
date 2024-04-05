package resources;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Class for the Stone resource.
 * @author Michael Geltz
 */
@Entity
@DiscriminatorValue("STONE")
public class Stone extends Resource{
    /**
     * Constructor for Stone.
     * Takes a resourceManager and quantity.
     * @param resourceManager
     * @param quantity Quantity of Stone.
     */
    public Stone(ResourceManager resourceManager, int quantity) {
        super(ResourceType.STONE, quantity, resourceManager);
    }

    /**
     * Empty Constructor for Stone.
     */
    public Stone() {

    }
}
