package resources;

public class Food extends Resource{
    private int consumptionRate;

    public Food(int amount, int consumptionRate) {
        super(amount);
        this.consumptionRate = consumptionRate;
    }



}
