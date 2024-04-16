package buildings.resourcebuildings;

import buildings.BuildingTypes;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Class for the Platinum Mine.
 */
@Entity
@DiscriminatorValue("PLATINUMMINE")
public class PlatinumMine extends ResourceBuilding{

    public PlatinumMine(int level, ResourceBuildingManager resourceBuildingManager)
    {
        super(BuildingTypes.PLATINUMMINE, level, resourceBuildingManager);
        setResourceProductionRate(10);
    }
}
