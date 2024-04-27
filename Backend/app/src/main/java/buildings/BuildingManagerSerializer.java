package buildings;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class BuildingManagerSerializer extends JsonSerializer<BuildingManager>
{
    @Override
    public void serialize(BuildingManager buildingManager, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException
    {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("mainbuildinglevel", buildingManager.getLevel(BuildingTypes.MAINBUILDING));
        jsonGenerator.writeNumberField("researchbuildinglevel", buildingManager.getLevel(BuildingTypes.RESEARCHBUILDING));
        jsonGenerator.writeEndObject();
    }
}
