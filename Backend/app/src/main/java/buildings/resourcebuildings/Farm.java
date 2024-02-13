package buildings.resourcebuildings;

import buildings.Building;

public class Farm extends Building {

    //Level 1 farm generaates 10 food per second
    private long productionRate = 10;
    private int level = 1;

    private int upgradeCostStone;
    private int upgradeCostWood;

    public Farm(double power, int level) {
        super(power, level);
    }

    public int getLevel() {
        return this.level;
    }
    public void upgrade() throws Exception {
        if (this.level < 5 && MainBuilding.getLevel >= this.level++) {
            this.level++;
        }
        else if (this.level == 5){
            throw new Exception("Max level reached for building");
        }
        else {
            throw new Exception("Main building level requirement not met");
        }
    }

    private void setUpgradeCostSnW() {

    }
    @Override
    public double getPower() {
        return 0;
    }
}
