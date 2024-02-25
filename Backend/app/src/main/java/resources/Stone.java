package resources;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("STONE")
public class Stone extends Resource{
    public Stone(ResourceManager resourceManager, int quantity) {
        super(ResourceType.STONE, quantity, resourceManager);
    }

    public Stone() {

    }
}
