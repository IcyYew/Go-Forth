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

    public Stables(int level, TroopBuildingManager troopBuildingManager)
    {
        super(BuildingTypes.STABLES, level, troopBuildingManager);
        setTrainingCapacity(50);
        setTrainingTime(30);
        setStoneTrainingCost(40);
        setWoodTrainingCost(30);
    }

    public Stables()
    {

    }
}
