package resources;

import jakarta.persistence.*;

@Entity
public abstract class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer playerID;

    private String key;
    private String value;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "resource_manager_id")
    private ResourceManager resourceManager;

    public Resource(int quantity) {
        this.quantity = quantity;
    }

    public Resource() {

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




    @Override
    public String toString() {
        return "Resource{" +
                "quantity=" + quantity +
                '}';
    }
}
