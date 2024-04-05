package buildings.resourcebuildings;

import buildings.Building;

/**
 * Class for the Farm building.
 * The farm produces food at a rate which is based on its building level.
 * Upgrading it costs Wood and Stone.
 * The farm contributes to the player's power level.
 */
public class Farm extends Building {

    //Level 1 farm generaates 10 food per second
    private long productionRate = 10;
    private int level = 1;

    private int upgradeCostStone;
    private int upgradeCostWood;

    /**
     * Constructor for the Farm.
     * Creates a Farm of a certain level and power value.
     * @param power The power value of the Farm.
     * @param level The level of the Farm.
     */
    public Farm(double power, int level) {
        super(power, level);
    }

    /**
     * Gets the Farm's level.
     * @return Returns the Farm's level value.
     */
    public int getLevel() {
        return this.level;
    }


    /*public void upgrade() throws Exception {
        if (this.level < 5 && MainBuilding.getLevel >= this.level++) {
            this.level++;
        }
        else if (this.level == 5){
            throw new Exception("Max level reached for building");
        }
        else {
            throw new Exception("Main building level requirement not met");
        }
    }*/

    private void setUpgradeCostSnW() {

    }

    /**
     * Gets the Farm's power value.
     * @return Returns the Farm's power value.
     */
    @Override
    public double getPower() {
        return 0;
    }
}
