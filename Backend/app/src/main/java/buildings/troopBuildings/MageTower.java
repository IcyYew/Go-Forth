package buildings.troopBuildings;

import buildings.BuildingManager;
import buildings.BuildingTypes;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@DiscriminatorValue("MAGETOWER")
public class MageTower extends TroopTrainingBuilding{
    int trainingCapacity = 50;
    double trainingTime = 25.0; // in seconds
    int stoneTrainingCost = 30;
    int woodTrainingCost = 40;

    public MageTower(BuildingManager buildingManager, int level)
    {
        super(BuildingTypes.MAGETOWER, level, buildingManager);
    }

}
