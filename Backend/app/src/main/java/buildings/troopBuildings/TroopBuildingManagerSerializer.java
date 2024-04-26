package buildings.troopBuildings;

import buildings.BuildingTypes;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class TroopBuildingManagerSerializer extends JsonSerializer<TroopBuildingManager>
{
    @Override
    public void serialize(TroopBuildingManager troopBuildingManager, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException
    {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("archeryrangelevel", troopBuildingManager.getLevel(BuildingTypes.ARCHERYRANGE));
        jsonGenerator.writeNumberField("magetowerlevel", troopBuildingManager.getLevel(BuildingTypes.MAGETOWER));
        jsonGenerator.writeNumberField("stableslevel", troopBuildingManager.getLevel(BuildingTypes.STABLES));
        jsonGenerator.writeNumberField("warriorschoollevel", troopBuildingManager.getLevel(BuildingTypes.WARRIORSCHOOL));
        jsonGenerator.writeEndObject();
    }
}
