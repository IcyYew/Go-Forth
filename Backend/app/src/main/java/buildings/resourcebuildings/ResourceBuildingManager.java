package buildings.resourcebuildings;

import buildings.BuildingTypes;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ResourceBuildingManager
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer playerId;

    @OneToMany(mappedBy = "resourceBuildingManager", cascade = CascadeType.ALL)
    public List<ResourceBuilding> resourceBuildingManager;

    public ResourceBuildingManager(Integer playerId)
    {
        this.playerId = playerId;
        this.resourceBuildingManager = new ArrayList<>();
        initializeResourceBuildings();
    }

    public ResourceBuildingManager()
    {

    }

    private void initializeResourceBuildings()
    {
        resourceBuildingManager.add(new Farm(1, this));
        resourceBuildingManager.add(new LumberYard(1, this));
        resourceBuildingManager.add(new PlatinumMine(1, this));
        resourceBuildingManager.add(new Quarry(1, this));
    }

    public int getLevel(BuildingTypes buildingType)
    {
        for (ResourceBuilding resourceBuilding : resourceBuildingManager)
        {
            if (resourceBuilding.getBuildingType() == buildingType)
            {
                return resourceBuilding.getLevel();
            }
        }
        return 0;
    }

    public void upgradeBuilding(BuildingTypes buildingType) throws Exception
    {
        for (ResourceBuilding resourceBuilding : resourceBuildingManager)
        {
            if (resourceBuilding.getBuildingType() == buildingType)
            {
                resourceBuilding.upgrade();
            }
        }
    }
}
