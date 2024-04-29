package buildings.troopBuildings;

import buildings.BuildingTypes;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@DiscriminatorValue("TROOPBUILDING")
public class TroopTrainingBuilding extends buildings.Building {

    protected double trainingTime;
    protected int trainingCapacity;
    protected double trainingTimeTotal;
    protected int trainingCost;
    protected LocalDateTime trainingTimeStarted;
    protected LocalDateTime trainingTimeFinished;
    @ManyToOne
    @JoinColumn(name = "troop_building_manager")
    private TroopBuildingManager troopBuildingManager;


    public TroopTrainingBuilding(BuildingTypes buildingTypes, int level, TroopBuildingManager troopBuildingManager)
    {
        setBuildingType(buildingTypes);
        setLevel(level);
        setTroopBuildingManager(troopBuildingManager);
        setTrainingTimeStarted(LocalDateTime.now());
        setTrainingTimeFinished(LocalDateTime.now());
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
            this.power *= 1.5;
            setTrainingCapacity(level);
            setTrainingTime(level);
            upgradeStoneUpgradeCost();
            upgradeWoodUpgradeCost();
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

    public TroopBuildingManager getTroopBuildingManager() {
        return troopBuildingManager;
    }

    public void setTroopBuildingManager(TroopBuildingManager troopBuildingManager) {
        this.troopBuildingManager = troopBuildingManager;
    }

    public int getTrainingCost() {
        return trainingCost;
    }

    public void setTrainingCost(int trainingCost) {
        this.trainingCost = trainingCost;
    }
}
