package buildings.troopBuildings;

import buildings.BuildingManager;
import buildings.BuildingTypes;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import troops.TroopManager;
import troops.TroopTypes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static troops.TroopTypes.ARCHER;

@Entity
@DiscriminatorValue("ARCHERYRANGE")
public class ArcheryRange extends TroopTrainingBuilding{
    private int trainingCapacity = 50;
    private double trainingTime = 30.0; // in seconds
    private int stoneTrainingCost = 30;
    private int woodTrainingCost = 40;

    public ArcheryRange(int level, TroopBuildingManager troopBuildingManager)
    {
        super(BuildingTypes.ARCHERYRANGE, level, troopBuildingManager);

    }
}
