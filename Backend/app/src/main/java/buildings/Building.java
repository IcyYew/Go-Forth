package buildings;

public abstract class Building {
    private double power;
    private int level;

    public Building(double power, int level) {
        this.power = power;
        this.level = level;
    }

    public abstract double getPower();

}
