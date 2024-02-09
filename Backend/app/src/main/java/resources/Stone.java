package resources;

public class Stone extends Resource{
    private final ResourceType resourceType = ResourceType.STONE;
    public Stone(int quantity) {
        super(quantity);
    }
}
