package resources;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long playerID;

    private String key;
    private String value;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private ResourceType resourceType;

    @ManyToOne
    @JoinColumn(name = "resource_manager_id")
    private ResourceManager resourceManager;

    public Resource(ResourceType resourceType, int quantity, ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
        this.resourceType = resourceType;
        this.quantity = quantity;
    }

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

    public void consume(int amount) {
        if (this.getQuantity() == 0 && !(this.getQuantity() < amount)) {
            this.setQuantity(0);
        }
        else {
            this.setQuantity(this.getQuantity() - amount);
        }
    }
}
