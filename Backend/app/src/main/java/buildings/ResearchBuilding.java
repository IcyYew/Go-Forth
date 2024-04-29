package buildings;

import buildings.resourcebuildings.ResourceBuilding;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Class for the research building.
 */
@Entity
@DiscriminatorValue("RESEARCHBUILDING")
public class ResearchBuilding extends OtherBuilding{

    public ResearchBuilding(BuildingManager buildingManager, int level)
    {
        setBuildingType(BuildingTypes.RESEARCHBUILDING);
        setBuildingManager(buildingManager);
        setLevel(level);
        setPower(50);
        setStoneUpgradeCost(400);
        setWoodUpgradeCost(400);
    }

    public ResearchBuilding()
    {

    }
}
