package buildings.troopBuildings;

public class ArcheryRange {
    int power = 100;
    int level = 1;

    int trainingCapacity = 50;
    double trainingTime = 30.0; // in seconds

    double trainingTimeTotal = 0;

    int stoneUpgradeCost = 500;
    int woodUpgradeCost = 500;

    int stoneTrainingCost = 30;
    int woodTrainingCost = 40;


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

    public double trainBatch(int quantity) throws  Exception {
        if (quantity > this.trainingCapacity) {
                throw new Exception("Max training capacity exceeded");
        }
        else {
            return calculateTrainingTime(quantity);
        }
    }
}
