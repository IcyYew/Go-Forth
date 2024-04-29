package clans;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import player.Player;

import java.io.IOException;

public class ClanSerializer extends JsonSerializer<Clan> {

    @Override
    public void serialize(Clan clan, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("clanID", clan.getClanID());
        jsonGenerator.writeStringField("clanName", clan.getClanName());
        jsonGenerator.writeNumberField("totalClanPower", clan.getTotalClanPower());
        jsonGenerator.writeNumberField("clanMembersNumber", clan.getClanMembersNumber());
        jsonGenerator.writeNumberField("clanMembersMax", clan.getClanMembersMax());
        jsonGenerator.writeArrayFieldStart("memberList");
        for (Player player : clan.getMemberManager().getMemberList()) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("playerID", player.getPlayerID());
            jsonGenerator.writeStringField("userName", player.getUserName());
            jsonGenerator.writeNumberField("power", player.getPower());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}
