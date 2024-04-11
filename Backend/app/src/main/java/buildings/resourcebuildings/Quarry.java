package buildings.resourcebuildings;

import buildings.BuildingManager;
import buildings.BuildingTypes;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Class for the Stone Quarry.
 */
@Entity
@DiscriminatorValue("QUARRY")
public class Quarry extends ResourceBuilding{

    public Quarry(BuildingManager buildingManager, int level)
    {
        super(BuildingTypes.QUARRY, level, buildingManager);
    }
}
