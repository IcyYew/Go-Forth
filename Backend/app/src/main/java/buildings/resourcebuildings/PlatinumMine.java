package buildings.resourcebuildings;

import buildings.BuildingManager;
import buildings.BuildingTypes;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Class for the Platinum Mine.
 */
@Entity
@DiscriminatorValue("PLATINUMMINE")
public class PlatinumMine extends ResourceBuilding{

    public PlatinumMine(BuildingManager buildingManager, int level)
    {
        super(BuildingTypes.PLATINUMMINE, level, buildingManager);
    }
}
