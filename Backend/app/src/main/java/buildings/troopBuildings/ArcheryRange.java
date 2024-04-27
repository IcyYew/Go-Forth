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
public class ArcheryRange extends TroopTrainingBuilding {


    public ArcheryRange(int level, TroopBuildingManager troopBuildingManager)
    {
        super(BuildingTypes.ARCHERYRANGE, level, troopBuildingManager);
        setTrainingCapacity(50);
        setTrainingTime(10);
        setTrainingCost(10);
        setPower(50);
    }

    public ArcheryRange()
    {

    }



    @Override
    public double getTrainingTime() {
        return super.getTrainingTime();
    }

    @Override
    public void setTrainingTime(double trainingTime) {
        super.setTrainingTime(trainingTime);
    }

    @Override
    public int getTrainingCapacity() {
        return super.getTrainingCapacity();
    }

    @Override
    public void setTrainingCapacity(int trainingCapacity) {
        super.setTrainingCapacity(trainingCapacity);
    }
}
