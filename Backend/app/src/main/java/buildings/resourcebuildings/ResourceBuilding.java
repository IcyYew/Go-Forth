package buildings.resourcebuildings;

import buildings.Building;
import buildings.BuildingManager;
import buildings.BuildingTypes;
import buildings.troopBuildings.TroopBuildingManager;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import resources.Resource;

@Entity
@DiscriminatorValue("RESOURCEBUILDING")
public abstract class ResourceBuilding extends Building
{
    protected int resourceProductionRate;

    public ResourceBuilding(BuildingTypes buildingTypes, int level, ResourceBuildingManager resourceBuildingManager)
    {
        super(buildingTypes, level, resourceBuildingManager);
    }

    @Override
    public void upgrade() throws Exception {
        if (this.level <= 5) {
            this.level++;
            resourceProductionRate += 10;
            setStoneUpgradeCost(level);
            setWoodUpgradeCost(level);
        }
        else {
            throw new Exception("Max level reached");
        }
    }

    public int getResourceProductionRate()
    {
        return this.resourceProductionRate;
    }

    public void setResourceProductionRate(int resourceProductionRate)
    {
        this.resourceProductionRate = resourceProductionRate;
    }
}
