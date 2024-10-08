package buildings.resourcebuildings;

import buildings.BuildingManager;
import buildings.BuildingTypes;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;

/**
 * Class for the Stone Quarry.
 */
@Entity
@DiscriminatorValue("QUARRY")
public class Quarry extends ResourceBuilding{

    public Quarry(int level, ResourceBuildingManager resourceBuildingManager)
    {
        super(BuildingTypes.QUARRY, level, resourceBuildingManager);
        setResourceProductionRate(1);
        setTimeLastCollected(LocalDateTime.now());
        setPower(16);
        setStoneUpgradeCost(400);
        setWoodUpgradeCost(400);
        setResourceLimit(1000);
    }

    public Quarry()
    {

    }
}
