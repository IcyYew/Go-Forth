package buildings;

import buildings.resourcebuildings.Farm;
import buildings.resourcebuildings.LumberYard;
import buildings.resourcebuildings.PlatinumMine;
import buildings.resourcebuildings.Quarry;
import buildings.troopBuildings.ArcheryRange;
import buildings.troopBuildings.MageTower;
import buildings.troopBuildings.Stables;
import buildings.troopBuildings.WarriorSchool;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for the Building Manager.
 */
@JsonSerialize
@Entity
public class BuildingManager {

    @Id
    @GeneratedValue
    private Integer playerId;

    @OneToMany
    public List<Building> buildingManager;

    public BuildingManager(Integer playerId)
    {
        this.playerId = playerId;
        this.buildingManager = new ArrayList<>();
        initializeBuildings();
    }

    private void initializeBuildings()
    {
        buildingManager.add(new Farm(this, 1));
        buildingManager.add(new LumberYard(this, 1));
        buildingManager.add(new PlatinumMine(this, 1));
        buildingManager.add(new Quarry(this, 1));
        buildingManager.add(new ArcheryRange(this, 1));
        buildingManager.add(new MageTower(this, 1));
        buildingManager.add(new Stables(this, 1));
        buildingManager.add(new WarriorSchool(this, 1));
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

    @Override
    public String toString()
    {
        return "BuildingManager{" +
                "playerId=" + playerId +
                ", buildingManager=" + buildingManager +
                '}';
    }
}
