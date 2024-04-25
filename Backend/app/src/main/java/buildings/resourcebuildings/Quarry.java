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

    public Quarry(int level, ResourceBuildingManager resourceBuildingManager)
    {
        super(BuildingTypes.QUARRY, level, resourceBuildingManager);
        setResourceProductionRate(10);
    }

    public Quarry()
    {

    }
}
