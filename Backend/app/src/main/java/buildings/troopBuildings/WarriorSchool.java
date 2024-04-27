package buildings.troopBuildings;

import buildings.BuildingManager;
import buildings.BuildingTypes;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import troops.TroopTypes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static troops.TroopTypes.ARCHER;
import static troops.TroopTypes.WARRIOR;

@Entity
@DiscriminatorValue("WARRIORSCHOOL")
public class WarriorSchool extends TroopTrainingBuilding{

    public WarriorSchool(int level, TroopBuildingManager troopBuildingManager)
    {
        super(BuildingTypes.WARRIORSCHOOL, level, troopBuildingManager);
        setTrainingCapacity(50);
        setTrainingTime(40);
        setTrainingCost(25);
        setPower(50);
        setStoneUpgradeCost(400);
        setWoodUpgradeCost(400);
    }

    public WarriorSchool()
    {

    }

}
