package buildings.troopBuildings;

import buildings.BuildingManager;
import buildings.BuildingTypes;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@DiscriminatorValue("WARRIORSCHOOL")
public class WarriorSchool extends TroopTrainingBuilding{
    int trainingCapacity = 50;
    double trainingTime = 15.0; // in seconds
    int stoneTrainingCost = 30;
    int woodTrainingCost = 40;

    public WarriorSchool(BuildingManager buildingManager, int level)
    {
        super(BuildingTypes.WARRIORSCHOOL, level, buildingManager);
    }
}
