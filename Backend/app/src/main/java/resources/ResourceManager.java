package resources;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import troops.TroopManagerSerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@JsonSerialize(using = ResourceSerializer.class)
@Entity
public class ResourceManager {


    //Usefulness of child resource classes isn't currently apparent but they will eventaully have other properties
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer playerID;

    @OneToMany(mappedBy = "resourceManager", cascade = CascadeType.ALL)
    private List<Resource> resourceManager;



    // Every player starts with quantity of resource described below, allows introduction to building/recruiting/research system
    public ResourceManager(Integer playerId) {
        this.playerID = playerId;
        this.resourceManager = new ArrayList<>();
        initializeResources();
    }

    public ResourceManager() {

    }

    private void initializeResources() {
        resourceManager.add(new Wood(this, 1000));
        resourceManager.add(new Food(this, 5000));
        resourceManager.add(new Platinum(this, 500));
        resourceManager.add(new Stone(this, 1000));
    }

    public int getResource(ResourceType resourceType) {
        for (Resource resource : resourceManager) {
            if (resource.getType() == resourceType) {
                return resource.getQuantity();
            }
        }
        // Return 0 if resource doesnt exist, in practice should never happen
        return 0;
    }

    public void addResource(ResourceType resourceType, int quantity) {
        for (Resource resource : resourceManager) {
            if (resource.getType() == resourceType) {
               resource.setQuantity(resource.getQuantity() + quantity);
               break;
            }
        }
    }

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
            break;
        }
    }

    @Override
    public String toString() {
        return "ResourceManager{" +
                "playerID=" + playerID +
                ", resourceManager=" + resourceManager +
                '}';
    }
}
