package buildings.troopBuildings;

import buildings.BuildingManager;
import buildings.BuildingTypes;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import troops.TroopTypes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static troops.TroopTypes.ARCHER;
import static troops.TroopTypes.CAVALRY;

@Entity
@DiscriminatorValue("STABLES")
public class Stables extends TroopTrainingBuilding{

    private TroopTypes trainsWhat = CAVALRY;
    private int trainingCapacity = 50;
    private double trainingTime = 20.0; // in seconds
    private int stoneTrainingCost = 30;
    private int woodTrainingCost = 40;

    public Stables(BuildingManager buildingManager, int level)
    {
        super(BuildingTypes.STABLES, level, buildingManager);
    }

}
