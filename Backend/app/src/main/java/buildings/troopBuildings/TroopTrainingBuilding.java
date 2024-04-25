package buildings.troopBuildings;

import buildings.BuildingTypes;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@DiscriminatorValue("TROOPBUILDING")
public class TroopTrainingBuilding extends buildings.Building {

    protected double trainingTime;
    protected int trainingCapacity;
    protected double trainingTimeTotal;
    protected int stoneTrainingCost;
    protected int woodTrainingCost;
    protected LocalDateTime trainingTimeStarted;
    protected LocalDateTime trainingTimeFinished;
    protected TroopBuildingManager troopBuildingManager;

    public TroopTrainingBuilding(BuildingTypes buildingTypes, int level, TroopBuildingManager troopBuildingManager)
    {
        super(buildingTypes, level, troopBuildingManager);
    }

    public TroopTrainingBuilding()
    {

    }

    public String trainBatch(int quantity) {
        trainingTimeTotal = calculateTrainingTime(quantity);
        this.trainingTimeStarted = LocalDateTime.now();
        this.trainingTimeFinished = trainingTimeStarted.plusSeconds((long) trainingTimeTotal);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        return trainingTimeFinished.format(format);
    }

    @Override
    public void upgrade() throws Exception {
        if (this.level <= 5) {
            this.level++;
            setTrainingCapacity(level);
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

    public double getTrainingTime() {
        return trainingTime;
    }

    public void setTrainingTime(double trainingTime) {
        this.trainingTime = trainingTime;
    }

    public int getTrainingCapacity() {
        return trainingCapacity;
    }

    public void setTrainingCapacity(int trainingCapacity) {
        this.trainingCapacity = trainingCapacity;
    }

    public double getTrainingTimeTotal() {
        return trainingTimeTotal;
    }

    public void setTrainingTimeTotal(double trainingTimeTotal) {
        this.trainingTimeTotal = trainingTimeTotal;
    }

    public int getStoneTrainingCost() {
        return stoneTrainingCost;
    }

    public void setStoneTrainingCost(int stoneTrainingCost) {
        this.stoneTrainingCost = stoneTrainingCost;
    }

    public int getWoodTrainingCost() {
        return woodTrainingCost;
    }

    public void setWoodTrainingCost(int woodTrainingCost) {
        this.woodTrainingCost = woodTrainingCost;
    }

    public LocalDateTime getTrainingTimeStarted() {
        return trainingTimeStarted;
    }

    public void setTrainingTimeStarted(LocalDateTime trainingTimeStarted) {
        this.trainingTimeStarted = trainingTimeStarted;
    }

    public LocalDateTime getTrainingTimeFinished() {
        return trainingTimeFinished;
    }

    public void setTrainingTimeFinished(LocalDateTime trainingTimeFinished) {
        this.trainingTimeFinished = trainingTimeFinished;
    }
}
