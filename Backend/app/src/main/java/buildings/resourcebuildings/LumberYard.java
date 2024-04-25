package buildings.resourcebuildings;

import buildings.BuildingManager;
import buildings.BuildingTypes;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Class for the Lumberyard.
 * @author Michael Geltz.
 */
@Entity
@DiscriminatorValue("LUMBERYARD")
public class LumberYard extends ResourceBuilding{

    public LumberYard(int level, ResourceBuildingManager resourceBuildingManager)
    {
        super(BuildingTypes.LUMBERYARD, level, resourceBuildingManager);
        setResourceProductionRate(10);
    }

    public LumberYard()
    {

    }
}
