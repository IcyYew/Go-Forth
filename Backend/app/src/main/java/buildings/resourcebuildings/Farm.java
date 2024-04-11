package buildings.resourcebuildings;

import buildings.Building;
import buildings.BuildingManager;
import buildings.BuildingTypes;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Class for the Farm building.
 * The farm produces food at a rate which is based on its building level.
 * Upgrading it costs Wood and Stone.
 * The farm contributes to the player's power level.
 */
@Entity
@DiscriminatorValue("FARM")
public class Farm extends ResourceBuilding {

    public Farm(BuildingManager buildingManager, int level)
    {
        super(BuildingTypes.FARM, level, buildingManager);
    }

}
