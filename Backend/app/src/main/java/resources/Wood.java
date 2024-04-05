package resources;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Class for the Wood Resource.
 * @author Michael Geltz
 */
@Entity
@DiscriminatorValue("WOOD")
public class Wood extends Resource{
    /**
     * Constructor for Wood.
     * @param resourceManager
     * @param quantity Quantity of wood.
     */
    public Wood(ResourceManager resourceManager, int quantity) {
        super(ResourceType.WOOD, quantity, resourceManager);
    }

    /**
     * Empty constructor for Wood.
     */
    public Wood() {

    }
}
