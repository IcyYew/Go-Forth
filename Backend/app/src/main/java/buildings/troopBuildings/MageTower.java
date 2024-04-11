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

    private TroopTypes trainsWhat = MAGE;
    private int trainingCapacity = 50;
    private double trainingTime = 25.0; // in seconds
    private int stoneTrainingCost = 30;
    private int woodTrainingCost = 40;

    public MageTower(BuildingManager buildingManager, int level)
    {
        super(BuildingTypes.MAGETOWER, level, buildingManager);
    }

}
