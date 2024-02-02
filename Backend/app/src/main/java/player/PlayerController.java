package player;

import org.springframework.web.bind.annotation.*;
import troops.Troop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



@RestController
public class PlayerController {

    HashMap<Integer, Player> playerDataBase = new HashMap<>();
    private List<Player> playerList = new ArrayList<>();

    private int playerID = 0;

    @GetMapping("/players/getall")
    public List<Player> getAllPlayers() {
        playerList = new ArrayList<>(playerDataBase.values());

        return playerList;
    }

    @PostMapping("/players/new")
    public String newPlayer(@RequestBody Player player) {
        playerID++;
        player.setPlayerID(playerID);
        playerDataBase.put(playerID, player);
        return "New player of ID: " + player.getPlayerID();
    }

    @PostMapping("/players/addtroops/{playerID}")
    public Player addTroops(@PathVariable int playerID, Troop troop, int recruited) {
        playerDataBase.get(playerID).troops.addTroop(troop.getTroopType(), recruited);
        return playerDataBase.get(playerID);
    }

    @GetMapping("/players/removetroops/{playerID}")
    public Player removeTroops(@PathVariable int playerID, Troop troop, int deaths) {
        playerDataBase.get(playerID).troops.removeTroop(troop.getTroopType(), deaths);
        return playerDataBase.get(playerID);
    }

    @DeleteMapping("/players/banplayer/{playerID}")
    public String banPlayer(@PathVariable int playerID) {
        playerDataBase.remove(playerID);
        return "Player of ID: " + playerID +" banned from server & removed from database";
    }

}
