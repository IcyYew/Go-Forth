package resources;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PLATINUM")
public class Platinum extends Resource{


    public Platinum(ResourceManager resourceManager, int quantity) {
        super(ResourceType.PLATINUM, quantity, resourceManager);
    }

    public Platinum() {

    }
}
