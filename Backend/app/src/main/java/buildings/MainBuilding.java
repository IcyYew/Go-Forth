package buildings;

public class MainBuilding extends Building {
    private int resourceNeededStone = 1000;
    private int resourceNeededWood = 1000;

    private int level = 1;
    private double power = 1000;

    public MainBuilding(double power, int level) {
        super(power, level);
    }

    @Override
    public double getPower() {
        return this.power;
    }
}
