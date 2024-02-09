package player;

import org.springframework.web.bind.annotation.*;
import troops.Troop;
import troops.TroopCombatCalculator;
import troops.TroopManager;
import troops.TroopTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



@RestController
public class PlayerController {

    HashMap<Integer, Player> playerDataBase = new HashMap<>();

    private int playerID = 0;

    // Returns all currently existing players and their info via an ArrayList because I prefer the format
    @GetMapping("/players/getall")
    public List<Player> getAllPlayers() {
        return new ArrayList<>(playerDataBase.values());
    }

    // Creates a new player, to test use Postman POST option with url:
    // localhost:8080/players/new/ (then send in a raw JSON body, can simply be a string, i.e. Michael), no curly brace required
    @PostMapping("/players/new/")
    public String newPlayer(@RequestBody String userName) {
        // Increment playerID, as mentioned elsewhere will eventually be a unique IDing system
        playerID++;
        // Generate player object to be passed into the database hashmap
        Player player = new Player(new TroopManager(), playerID, 0, userName);
        playerDataBase.put(playerID, player);
        // Return id of created player
        return "New player of ID: " + player.getPlayerID();
    }

    // Addtroops to a declared player via their ID, to use, use Postman POST option, make sure you already have a player declared and use:
    // localhost:8080/players/addtroops/(playerID)?troopType=(trooptype)&recruited=(num)
    // An example request: localhost:8080/players/addtroops/1?troopType=ARCHER&recruited=100
    @PostMapping("/players/addtroops/{playerID}")
    public Player addTroops(@PathVariable int playerID, @RequestParam TroopTypes troopType, @RequestParam int recruited) {
        playerDataBase.get(playerID).troops.addTroop(troopType, recruited);
        // After adding troop(s) to a player, update their power
        playerDataBase.get(playerID).updatePower();
        // return updated player information
        return playerDataBase.get(playerID);
    }

    // !!!!!!!!!!!!!!!!!!!!!!! Note to self -- decide if when overflow troop removal passed in to deduct to zero or neglect request entirely


    // Removetroops from a declared player via their ID, to use, use Postman POST option, make sure you have a player declared and use:
    // localhost:8080/players/removetroops/(playerID)?troopType=(trooptype)&deaths=(num)
    // Example request: localhost:8080/players/removetroops/1?troopType=ARCHER&deaths=100
    @PostMapping("/players/removetroops/{playerID}")
    public Player removeTroops(@PathVariable int playerID, @RequestParam TroopTypes troopType, @RequestParam int deaths) {
        playerDataBase.get(playerID).troops.removeTroop(troopType, deaths);
        // update player power when troops removed
        playerDataBase.get(playerID).updatePower();
        // return updated player information
        return playerDataBase.get(playerID);
    }

    // Delete or "ban" a player from the server, effectively removes all information and data of the player from the database
    // To test use Postman DELETE option, make sure the player you are trying to delete exist, use:
    // localhost:8080/players/banPlayer/(playerID)
    // Example request: localhost:8080/players/banPlayer/1
    @DeleteMapping("/players/banplayer/{playerID}")
    public String banPlayer(@PathVariable int playerID) {
        // Remove provided player from db
        playerDataBase.remove(playerID);
        // Return basic string saying player banned etc
        return "Player of ID: " + playerID +" banned from server & removed from database";
    }

    // fight method can be used to see the result of two existing players armies battling
    // To use this method, use Postman GET option, make sure the two players exist and at least have some troops:
    // localhost:8080/players/fight/(playerID1)/(playerID2)
    // Example request: localhost:8080/players/fight/1/2
    @GetMapping("/players/fight/{playerID1}/{playerID2}")
    public String fight(@PathVariable int playerID1, @PathVariable int playerID2) {
        // Declare new troopcombatcalculator to determine battle result between two players
        // player troop counts updated in the combat calculator
        TroopCombatCalculator tcc = new TroopCombatCalculator(playerDataBase.get(playerID1).troops, playerDataBase.get(playerID2).troops);
        // update both players' power based on their new troop counts
        playerDataBase.get(playerID1).updatePower();
        playerDataBase.get(playerID2).updatePower();

        // return result from tcc and both players' new information
        return tcc.getResult() + "\n" + playerDataBase.get(playerID1) + "\n\n" +  playerDataBase.get(playerID2);

    }

    

}
