package buildings.troopBuildings;

import troops.TroopManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ArcheryRange {
    private int power = 100;
    private int level = 1;

    private int trainingCapacity = 50;
    private double trainingTime = 30.0; // in seconds

    private double trainingTimeTotal = 0;

    private int stoneUpgradeCost = 500;
    private int woodUpgradeCost = 500;

    private int stoneTrainingCost = 30;
    private int woodTrainingCost = 40;

    private LocalDateTime trainingTimeStarted;
    private LocalDateTime trainingTimeFinished;

    public ArcheryRange(int level) {
        this.level = level;
    }

    public void setTrainingTime(int level) {
        this.trainingTime = this.trainingTime *((1.0/5.0) * (double)level);
    }

    public void setStoneUpgradeCost(int level) {
        this.stoneUpgradeCost = this.stoneUpgradeCost * (int)((8.0/5.0) * (double)level);
    }

    public void setWoodUpgradeCost(int level) {
        this.woodUpgradeCost = this.woodUpgradeCost * (int)((8.0/5.0) * (double)level);
    }

    public void upgradeBuilding(int level) throws Exception {
        if (this.level <= 5) {
            this.level++;
            setTrainingTime(level);
            setStoneUpgradeCost(level);
            setWoodUpgradeCost(level);
        }
        else {
            throw new Exception("Max level reached");
        }
    }

    public double calculateTrainingTime(int quantity) {
        return quantity * this.trainingTime;
    }

    public String trainBatch(int quantity) {
        trainingTimeTotal = calculateTrainingTime(quantity);
        this.trainingTimeStarted = LocalDateTime.now();
        this.trainingTimeFinished = trainingTimeStarted.plusSeconds((long) trainingTimeTotal);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        return trainingTimeFinished.format(format);
    }
}
