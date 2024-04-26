package buildings.resourcebuildings;

import buildings.BuildingTypes;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class ResourceBuildingManagerSerializer extends JsonSerializer<ResourceBuildingManager>
{
    @Override
    public void serialize(ResourceBuildingManager resourceBuildingManager, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException
    {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("farmlevel", resourceBuildingManager.getLevel(BuildingTypes.FARM));
        jsonGenerator.writeNumberField("lumberyardlevel", resourceBuildingManager.getLevel(BuildingTypes.LUMBERYARD));
        jsonGenerator.writeNumberField("platinumminelevel", resourceBuildingManager.getLevel(BuildingTypes.PLATINUMMINE));
        jsonGenerator.writeNumberField("quarrylevel", resourceBuildingManager.getLevel(BuildingTypes.QUARRY));
        jsonGenerator.writeEndObject();
    }
}
