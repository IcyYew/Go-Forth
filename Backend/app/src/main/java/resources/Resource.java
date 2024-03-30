package resources;

import jakarta.persistence.*;

/**
 * Abstract class representing the different resources.
 * This class serves as a template for different resources in the game.
 * Each resource has a type, quantity, and, in some cases, a passive consumption rate.
 * @author Michael Geltz
 */
@Entity(name="resources")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Resource {

    // Unqiue ID for each resource entity, no the ideal way of doing this because each player has four resources... still works
    // properly via the resource_manager_id connection though
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer resourceID;

    // Amount of a resource
    @Column(name="quantity")
    private int quantity;

    // Type of resource
    @Enumerated(EnumType.STRING)
    @Column(name="resourcetype")
    private ResourceType resourceType;

    // Connects child resource classes into one column
    @ManyToOne
    @JoinColumn(name = "resource_manager_id")
    private ResourceManager resourceManager;

    // Constructor for resources, takes in a resourceType, quantity, and a new resourceManager

    /**
     * Constructor for Resource.
     * Takes a resourceType, quantity, and resourceManager.
     * @param resourceType Type of resource to instantiate.
     * @param quantity Quantity of resource.
     * @param resourceManager
     */
    public Resource(ResourceType resourceType, int quantity, ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
        this.resourceType = resourceType;
        this.quantity = quantity;
    }

    // Empty constructor to make Jpa happy

    /**
     * Empty constructor for Resource.
     */
    public Resource() {
    }

    /**
     * Gets the type of resource. Implemented in concrete classes.
     * @return Returns a resourceType enum value.
     */
    public ResourceType getType() {
        return this.resourceType;
    }

    /**
     * Gets the quantity of a resource. Implemented in concrete classes.
     * @return Returns the resource's quantity value.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of a resource.
     * @param quantity The quantity of the resource.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Adds an integer amount to a resource quantity value.
     * @param quantity The quantity value to be added.
     */
    public void addResource(int quantity) {
        this.quantity += quantity;
    }

    // !!!  Not implemented

    /**
     * Method for the consumption of a resource.
     * This method takes an amount to be consumed and subtracts it from the current quantity.
     * If the current quantity is less than the amount, the quantity is set to 0.
     * @param amount The amount being consumed.
     */
    public void consume(int amount) {
        if (this.getQuantity() == 0 && !(this.getQuantity() < amount)) {
            this.setQuantity(0);
        }
        else {
            this.setQuantity(this.getQuantity() - amount);
        }
    }
}
