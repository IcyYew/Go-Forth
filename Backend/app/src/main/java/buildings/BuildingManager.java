package buildings;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for the Building Manager.
 */

@JsonSerialize(using = BuildingManagerSerializer.class)
@Entity
public class BuildingManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer playerId;

    @OneToMany(mappedBy = "buildingManager", cascade = CascadeType.ALL)
    public List<OtherBuilding> buildingManager;

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
        for (OtherBuilding building : buildingManager)
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
        for (OtherBuilding building : buildingManager)
        {
            if (building.getBuildingType() == buildingType)
            {
                building.upgrade();
            }
        }
    }

    public int getLevel(BuildingTypes buildingType)
    {
        for (OtherBuilding building : buildingManager)
        {
            if (building.getBuildingType() == buildingType)
            {
                return building.getLevel();
            }
        }
        return 0;
    }

    public List<OtherBuilding> getOtherBuildings()
    {
        return buildingManager;
    }

    public long calculateTotalOtherBuildingPower()
    {
        long power = 0;
        for (OtherBuilding otherBuilding : buildingManager)
        {
            power += otherBuilding.getPower();
        }
        return power;
    }

    public OtherBuilding getOtherBuilding(BuildingTypes buildingType)
    {
        for (OtherBuilding otherBuilding : buildingManager)
        {
            if (otherBuilding.getBuildingType() == buildingType)
            {
                return otherBuilding;
            }
        }
        return null;
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
