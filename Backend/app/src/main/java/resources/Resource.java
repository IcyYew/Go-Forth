package resources;

import jakarta.persistence.*;

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
    public Resource(ResourceType resourceType, int quantity, ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
        this.resourceType = resourceType;
        this.quantity = quantity;
    }

    // Empty constructor to make Jpa happy
    public Resource() {
    }

    public ResourceType getType() {
        return this.resourceType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void addResource(int quantity) {
        this.quantity += quantity;
    }

    // !!!  Not implemented
    public void consume(int amount) {
        if (this.getQuantity() == 0 && !(this.getQuantity() < amount)) {
            this.setQuantity(0);
        }
        else {
            this.setQuantity(this.getQuantity() - amount);
        }
    }
}
