package buildings.resourcebuildings;

import buildings.Building;
import buildings.BuildingManager;
import buildings.BuildingTypes;
import buildings.troopBuildings.TroopBuildingManager;
import resources.Resource;

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
            setResourceProductionRate(level);
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
