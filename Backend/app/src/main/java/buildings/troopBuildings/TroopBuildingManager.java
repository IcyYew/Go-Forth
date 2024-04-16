package buildings.troopBuildings;

import buildings.BuildingTypes;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class TroopBuildingManager
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer playerId;

    @OneToMany(mappedBy = "troopBuildingManager", cascade = CascadeType.ALL)
    private List<TroopTrainingBuilding> troopBuildingManager;

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

    @Override
    public String toString()
    {
        return "TroopManager{" +
                "playerId=" + playerId +
                ", troopManager=" + troopBuildingManager +
                '}';
    }
}
