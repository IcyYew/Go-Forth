package buildings;

public abstract class Building {
    private double power;
    private int level;

    public Building(double power, int level) {
        this.power = power;
        this.level = level;

    }

    public int getLevel() {
        return this.level;
    }

    public double getPower() {
        return this.power;

    }

}
