package buildings;

import buildings.resourcebuildings.ResourceBuilding;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Class for the research building.
 */
@Entity
@DiscriminatorValue("RESEARCHBUILDING")
public class ResearchBuilding extends Building{

    public ResearchBuilding(BuildingManager buildingManager, int level)
    {
        super(BuildingTypes.RESEARCHBUILDING, level, buildingManager);
    }

    public ResearchBuilding()
    {

    }
}
