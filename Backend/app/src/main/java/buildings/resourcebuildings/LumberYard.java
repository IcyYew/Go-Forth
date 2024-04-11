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

    public LumberYard(BuildingManager buildingManager, int level)
    {
        super(BuildingTypes.LUMBERYARD, level, buildingManager);
    }

}
