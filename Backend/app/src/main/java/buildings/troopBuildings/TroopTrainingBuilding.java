package buildings.troopBuildings;

public class TroopTrainingBuilding extends buildings.Building {
    double trainingTime; // seconds


    public TroopTrainingBuilding(double power, int level) {
        super(power, level);
    }

    public double getTrainingTime() {
        return this.trainingTime;
    }


}
