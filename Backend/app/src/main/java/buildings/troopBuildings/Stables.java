package buildings.troopBuildings;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Stables {
    int power = 100;
    int level = 1;

    int trainingCapacity = 50;
    double trainingTime = 20.0; // in seconds

    double trainingTimeTotal = 0;

    int stoneUpgradeCost = 500;
    int woodUpgradeCost = 500;

    int stoneTrainingCost = 30;
    int woodTrainingCost = 40;
    private LocalDateTime trainingTimeStarted;
    private LocalDateTime trainingTimeFinished;

    public Stables(int level) {
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
