package player;

import org.springframework.web.bind.annotation.*;
import troops.Troop;
import troops.TroopManager;
import troops.TroopTypes;

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
        return new ArrayList<>(playerDataBase.values());
    }

    @PostMapping("/players/new/")
    public String newPlayer(@RequestBody String userName) {
        playerID++;
        Player player = new Player(new TroopManager(), playerID, 0, userName);
        playerDataBase.put(playerID, player);
        return "New player of ID: " + player.getPlayerID();
    }

    @PostMapping("/players/addtroops/{playerID}")
    public Player addTroops(@PathVariable int playerID, @RequestParam TroopTypes troopType, @RequestParam int recruited) {
        playerDataBase.get(playerID).troops.addTroop(troopType, recruited);
        playerDataBase.get(playerID).updatePower();
        return playerDataBase.get(playerID);
    }

    @PostMapping("/players/removetroops/{playerID}")
    public Player removeTroops(@PathVariable int playerID, @RequestParam TroopTypes troopType, @RequestParam int deaths) {
        playerDataBase.get(playerID).troops.removeTroop(troopType, deaths);
        playerDataBase.get(playerID).updatePower();
        return playerDataBase.get(playerID);
    }

    @DeleteMapping("/players/banplayer/{playerID}")
    public String banPlayer(@PathVariable int playerID) {
        playerDataBase.remove(playerID);
        return "Player of ID: " + playerID +" banned from server & removed from database";
    }

}
