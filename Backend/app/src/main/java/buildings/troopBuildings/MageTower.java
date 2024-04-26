package buildings.troopBuildings;

import buildings.BuildingManager;
import buildings.BuildingTypes;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import troops.TroopTypes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static troops.TroopTypes.ARCHER;
import static troops.TroopTypes.MAGE;

@Entity
@DiscriminatorValue("MAGETOWER")
public class MageTower extends TroopTrainingBuilding{

    public MageTower(int level, TroopBuildingManager troopBuildingManager)
    {
        super(BuildingTypes.MAGETOWER, level, troopBuildingManager);
        setTrainingCapacity(50);
        setTrainingTime(20);
        setTrainingCost(15);
        setPower(50);
    }

    public MageTower()
    {

    }
}
