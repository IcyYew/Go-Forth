package buildings.troopbuildings;

public class ArcheryRange {
    int power = 100;
    int level = 1;

    int trainingCapacity = 50;
    double trainingTime = 30.0; // in seconds

    int stoneUpgradeCost = 500;
    int woodUpgradeCost = 500;

    int stoneTrainingCost = 30;
    int woodTrainingCost = 40;


    public void setTrainingTime(int level) {
        this.trainingTime = this.trainingTime * ((1/5) * level);
    }

    public void setStoneUpgradeCost(int level) {
        this.stoneUpgradeCost = this.stoneUpgradeCost * ((8/5) * level);
    }

    public void setWoodUpgradeCost(int level) {
        this.woodUpgradeCost = this.woodUpgradeCost * ((8/5) * level);
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

    public void trainBatch(int quantity) throws  Exception {
        if (quantity > this.trainingCapacity) {
                throw new Exception("Max training capacity exceeded");
        }
    }
}
