package resources;

public class Food extends Resource{
    private int consumptionRate;
    private final ResourceType resourceType = ResourceType.FOOD;

    public Food(int amount, int consumptionRate) {
        super(amount);
        this.consumptionRate = consumptionRate;
    }



}
