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
        for (Building building : buildingManager)
        {
            if (building.getBuildingType() == buildingType)
            {
                return building.getLevel();
            }
        }
        return 0;
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
