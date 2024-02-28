package resources;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("WOOD")
public class Wood extends Resource{
    public Wood(ResourceManager resourceManager, int quantity) {
        super(ResourceType.WOOD, quantity, resourceManager);
    }

    public Wood() {

    }
}
