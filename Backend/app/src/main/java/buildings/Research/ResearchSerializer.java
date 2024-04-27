package buildings.Research;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class ResearchSerializer extends JsonSerializer<ResearchManager> {
    @Override
    public void serialize(ResearchManager researchManager, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("trainingSpeedLevel", researchManager.getLevel("Training Speed"));
        jsonGenerator.writeNumberField("researchCostLevel", researchManager.getLevel("Research Cost"));
        jsonGenerator.writeNumberField("buildingCostLevel", researchManager.getLevel("Building Cost"));
        jsonGenerator.writeNumberField("attackBonusLevel", researchManager.getLevel("Attack Bonus"));
        jsonGenerator.writeNumberField("trainingCapacityLevel", researchManager.getLevel("Training Capacity"));
        jsonGenerator.writeNumberField("buildingSpeedLevel", researchManager.getLevel("Building Speed"));
        jsonGenerator.writeEndObject();
    }
}
