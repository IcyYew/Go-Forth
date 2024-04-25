package buildings;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for the Building Manager.
 */

@Entity
public class BuildingManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer playerId;

    @OneToMany(mappedBy = "buildingManager", cascade = CascadeType.ALL)
    public List<Building> buildingManager;

    public BuildingManager(Integer playerId)
    {
        this.playerId = playerId;
        this.buildingManager = new ArrayList<>();
        initializeBuildings();
    }

    private void initializeBuildings()
    {
        buildingManager.add(new MainBuilding(this, 1));
        buildingManager.add(new ResearchBuilding(this, 1));
    }

    public BuildingManager()
    {

    }

    public long getPlayerId()
    {
        return this.playerId;
    }

    public int getBuildingLevel(BuildingTypes buildingType)
    {
        for (Building building : buildingManager)
        {
            if (building.getBuildingType() == buildingType)
            {
                return building.getLevel();
            }
        }
        return 0;
    }

    public void upgradeBuilding(BuildingTypes buildingType) throws Exception
    {
        for (Building building : buildingManager)
        {
            if (building.getBuildingType() == buildingType)
            {
                building.upgrade();
            }
        }
    }

    @Override
    public String toString()
    {
        return "BuildingManager{" +
                "playerId=" + playerId +
                ", buildingManager=" + buildingManager +
                '}';
    }
}
