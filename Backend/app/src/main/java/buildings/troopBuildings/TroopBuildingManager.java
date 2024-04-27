package buildings.troopBuildings;

import buildings.BuildingTypes;
import buildings.resourcebuildings.ResourceBuilding;
import buildings.resourcebuildings.ResourceBuildingManagerSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@JsonSerialize(using = TroopBuildingManagerSerializer.class)
@Entity
public class TroopBuildingManager
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer playerId;

    @OneToMany(mappedBy = "troopBuildingManager", cascade = CascadeType.ALL)
    public List<TroopTrainingBuilding> troopBuildingManager;

    public TroopBuildingManager(Integer playerId) {
        this.playerId = playerId;
        this.troopBuildingManager = new ArrayList<>();
        initializeTroopBuildings();
    }

    public TroopBuildingManager()
    {

    }

    private void initializeTroopBuildings()
    {
        troopBuildingManager.add(new ArcheryRange(1, this));
        troopBuildingManager.add(new MageTower(1, this));
        troopBuildingManager.add(new Stables(1, this));
        troopBuildingManager.add(new WarriorSchool(1, this));
    }

    public int getLevel(BuildingTypes buildingType)
    {
        for (TroopTrainingBuilding troopTrainingBuilding : troopBuildingManager)
        {
            if (troopTrainingBuilding.getBuildingType() == buildingType)
            {
                return troopTrainingBuilding.getLevel();
            }
        }
        return 0;
    }
    public TroopTrainingBuilding getTrainingBuilding(BuildingTypes buildingType) {
        for (TroopTrainingBuilding troopTrainingBuilding : troopBuildingManager) {
            if (troopTrainingBuilding.getBuildingType() == buildingType) {
                return troopTrainingBuilding;
            }
        }
        return null;
    }

    public String trainTroops(BuildingTypes buildingType, int quantity)
    {
        for (TroopTrainingBuilding troopTrainingBuilding : troopBuildingManager)
        {
            if (troopTrainingBuilding.getBuildingType() == buildingType)
            {
                return troopTrainingBuilding.trainBatch(quantity);
            }
        }
        return null;
    }

    public void upgradeBuilding(BuildingTypes buildingType) throws Exception
    {
        for (TroopTrainingBuilding troopTrainingBuilding : troopBuildingManager)
        {
            if (troopTrainingBuilding.getBuildingType() == buildingType)
            {
                troopTrainingBuilding.upgrade();
            }
        }
    }

    public long calculateTotalTroopBuildingPower()
    {
        long power = 0;
        for (TroopTrainingBuilding troopTrainingBuilding : troopBuildingManager)
        {
            power += troopTrainingBuilding.getPower();
        }
        return power;
    }

    @Override
    public String toString()
    {
        return "TroopManager{" +
                "playerId=" + playerId +
                ", troopManager=" + troopBuildingManager +
                '}';
    }
}
