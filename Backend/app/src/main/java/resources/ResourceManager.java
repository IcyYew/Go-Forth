package resources;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import troops.TroopManagerSerializer;

import java.util.HashMap;
@JsonSerialize(using = ResourceSerializer.class)
public class ResourceManager {
    HashMap<ResourceType, Integer> resourceManager;

    //Usefulness of child resource classes isn't currently apparent but they will eventaully have other properties
    private long playerId;

    // Every player starts with quantity of resource described below, allows introduction to building/recruiting/research system
    public ResourceManager(long playerId) {
        this.playerId = playerId;
        resourceManager = new HashMap<>();
        resourceManager.put(ResourceType.WOOD, 1000);
        resourceManager.put(ResourceType.FOOD, 5000);
        resourceManager.put(ResourceType.PLATINUM, 500);
        resourceManager.put(ResourceType.STONE, 1000);
    }

    public int getResource(ResourceType resource) {
        return resourceManager.get(resource);
    }

    @Override
    public String toString() {
        return "ResourceManager{" +
                "WOOD=" + resourceManager.get(ResourceType.WOOD) +
                "STONE=" + resourceManager.get(ResourceType.STONE) +
                "PLATINUM=" + resourceManager.get(ResourceType.PLATINUM) +
                "FOOD=" + resourceManager.get(ResourceType.FOOD) +
                '}';
    }
}
