package resources;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import troops.TroopManagerSerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class for the Resource Manager.
 * Creates a single object which represents the entirety of a player's resources for use in other classes.
 * @author Michael Geltz
 */
@JsonSerialize(using = ResourceSerializer.class)
@Entity
public class ResourceManager {


    // ID used to tie resourceManager to a player via their ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer playerID;


    // Resource manager for managing resources
    @OneToMany(mappedBy = "resourceManager", cascade = CascadeType.ALL)
    private List<Resource> resourceManager;



    // Creates new resource manager for a player linking via ID

    /**
     * Constructor for the ResourceManager.
     * Takes a player ID for linking.
     * @param playerId The ID of the player whose resources are represented.
     */
    public ResourceManager(Integer playerId) {
        this.playerID = playerId;
        this.resourceManager = new ArrayList<>();
        initializeResources();
    }

    // Empty constructor to make Jpa happy

    /**
     * Empty Constructor for the Resource Manager.
     */
    public ResourceManager() {

    }

    // Initializes a players resources to the default amount, flexibile, subject to change, etc.

    /**
     * Initializes the player's resources to the default amount.
     */
    private void initializeResources() {
        resourceManager.add(new Wood(this, 1000));
        resourceManager.add(new Food(this, 5000));
        resourceManager.add(new Platinum(this, 500));
        resourceManager.add(new Stone(this, 1000));
    }

    // Goes through resources in a resources manager until it finds it then returns it

    /**
     * Gets a specific resource quantity.
     * @param resourceType The type of the resource.
     * @return Returns the quantity of the specified resource.
     */
    public int getResource(ResourceType resourceType) {
        for (Resource resource : resourceManager) {
            if (resource.getType() == resourceType) {
                return resource.getQuantity();
            }
        }
        // Return 0 if resource doesnt exist, in practice should never happen
        return 0;
    }

    /**
     * Adds a quantity of a specific resource.
     * @param resourceType The type of resource being added.
     * @param quantity The quantity of resource being added.
     */
    // Goes through resources until it finds the resource by type then adds to it
    public void addResource(ResourceType resourceType, int quantity) {
        for (Resource resource : resourceManager) {
            if (resource.getType() == resourceType) {
               resource.setQuantity(resource.getQuantity() + quantity);
               break;
            }
        }
    }

    /**
     * Removes a quantity of a specific resource.
     * @param resourceType The type of resource being removed.
     * @param quantity The quantity of resource being removed.
     */
    // Goes through resources until it finds the resource by type then takes away from to it
    public void removeResource(ResourceType resourceType, int quantity) {
        for (Resource resource : resourceManager) {
            if (resource.getType() == resourceType) {
                int current = resource.getQuantity();
                if (current >= quantity) {
                    resource.setQuantity(current - quantity);
                }
                else {
                }
            }
        }
    }

    /**
     * toString method for displaying the ResourceManager.
     * @return Returns the specific string.
     */
    @Override
    public String toString() {
        return "ResourceManager{" +
                "playerID=" + playerID +
                ", resourceManager=" + resourceManager +
                '}';
    }
}
