package buildings;

/**
 * Class for the Main Building.
 * This is the "town hall" for the player.
 * It can level up, and it contributes to a player's power value.
 * @author Michael Geltz.
 */
public class MainBuilding extends Building {
    private int resourceNeededStone = 1000;
    private int resourceNeededWood = 1000;

    private int level = 1;
    private double power = 1000;

    /**
     * Constructor for the Main Building.
     * Takes a power value and a level value.
     * @param power The power value for the main building.
     * @param level The level value for the main building.
     */
    public MainBuilding(double power, int level) {
        super(power, level);
    }

    /**
     * Gets the power for the main building.
     * @return Returns the power value of the main building.
     */
    @Override
    public double getPower() {
        return this.power;
    }
}
