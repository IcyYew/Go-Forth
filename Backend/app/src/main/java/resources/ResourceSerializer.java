package resources;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;

public class ResourceSerializer extends JsonSerializer<ResourceManager> {

    @Override
    public void serialize(ResourceManager resourceManager, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("wood", resourceManager.getResource(ResourceType.WOOD));
        jsonGenerator.writeNumberField("stone", resourceManager.getResource(ResourceType.STONE));
        jsonGenerator.writeNumberField("platinum", resourceManager.getResource(ResourceType.PLATINUM));
        jsonGenerator.writeNumberField("food", resourceManager.getResource(ResourceType.FOOD));
        jsonGenerator.writeEndObject();
    }
}
