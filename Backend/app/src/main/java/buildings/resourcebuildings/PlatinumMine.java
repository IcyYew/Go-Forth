package buildings.resourcebuildings;

import buildings.BuildingTypes;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;

/**
 * Class for the Platinum Mine.
 */
@Entity
@DiscriminatorValue("PLATINUMMINE")
public class PlatinumMine extends ResourceBuilding{

    public PlatinumMine(int level, ResourceBuildingManager resourceBuildingManager)
    {
        super(BuildingTypes.PLATINUMMINE, level, resourceBuildingManager);
        setResourceProductionRate(1);
        setTimeLastCollected(LocalDateTime.now());
        setPower(16);
        setStoneUpgradeCost(400);
        setWoodUpgradeCost(400);
        setResourceLimit(1000);
    }

    public PlatinumMine()
    {

    }
}
