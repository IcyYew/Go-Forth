package buildings;

public class MainBuilding extends Building {
    private int resourceNeededStone = 1000;
    private int resourceNeededWood = 1000;

    private int level = 1;

    @Override
    public double getPower() {
        return this.power;
    }
}
