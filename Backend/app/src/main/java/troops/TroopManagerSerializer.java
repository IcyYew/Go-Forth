package troops;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Serializer class for the TroopManager class.
 * Improves passage of JSON request bodies.
 * @author Michael Geltz
 */
public class TroopManagerSerializer  extends JsonSerializer<TroopManager> {
    @Override
    public void serialize(TroopManager troopManager, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("archerNum", troopManager.getTroopNum(TroopTypes.ARCHER));
        jsonGenerator.writeNumberField("warriorNum", troopManager.getTroopNum(TroopTypes.WARRIOR));
        jsonGenerator.writeNumberField("mageNum", troopManager.getTroopNum(TroopTypes.MAGE));
        jsonGenerator.writeNumberField("cavalryNum", troopManager.getTroopNum(TroopTypes.CAVALRY));
        jsonGenerator.writeNumberField("totalTroopPower", troopManager.calculateTotalTroopPower());
        jsonGenerator.writeEndObject();
    }
}
