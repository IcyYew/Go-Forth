package buildings.troopBuildings;

import buildings.BuildingManager;
import buildings.BuildingTypes;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@DiscriminatorValue("STABLES")
public class Stables extends TroopTrainingBuilding{
    int trainingCapacity = 50;
    double trainingTime = 20.0; // in seconds
    int stoneTrainingCost = 30;
    int woodTrainingCost = 40;

    public Stables(BuildingManager buildingManager, int level)
    {
        super(BuildingTypes.STABLES, level, buildingManager);
    }

}
