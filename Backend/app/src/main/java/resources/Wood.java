package resources;

public class Wood extends Resource{

    private final ResourceType resourceType = ResourceType.WOOD;
    public Wood(int quantity) {
        super(quantity);
    }
}
