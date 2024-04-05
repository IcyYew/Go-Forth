package buildings;

/**
 * Abstract class for buildings.
 * @author Michael Geltz
 */
public abstract class Building {
    private double power;
    private int level;

    /**
     * Constructor for a building.
     * Takes a level value and a power value.
     * @param power Power value for the building.
     * @param level Level value for the building.
     */
    public Building(double power, int level) {
        this.power = power;
        this.level = level;

    }

    /**
     * Gets the level for the building.
     * @return Returns the level value.
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * Gets the power for the building.
     * @return Returns the power value.
     */
    public double getPower() {
        return this.power;

    }

}
