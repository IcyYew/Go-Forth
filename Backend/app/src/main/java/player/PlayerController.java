package player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import resources.ResourceManager;
import resources.ResourceRepository;
import resources.ResourceType;
import troops.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



@RestController
public class PlayerController {

    @Autowired
    private PlayerRepository playerRepository;


    // Returns all currently existing players and their info via an ArrayList because I prefer the format
    @GetMapping("/players/getall")
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    @GetMapping("/players/getPlayer/{playerID}")
    public Player getPlayer(@PathVariable int playerID) {
        return playerRepository.findById(playerID).orElse(null);
    }
    // Creates a new player, to test use Postman POST option with url:
    // localhost:8080/players/new/ (then send in a raw JSON body, {"userName" : "(name)", "password" : "(password)"}
    @PostMapping("/players/new")
    public String newPlayer(@RequestBody PlayerCreator created) {
        // Increment playerID, as mentioned elsewhere will eventually be a unique IDing system
        // Generate player object to be passed into the database hashmap
        Player player = new Player();
        player.setUserName(created.getUserName());
        player.setPassword(created.getPassword());
        player = playerRepository.save(player);
        player.setTroops(new TroopManager(player.getPlayerID()));
        player.setResources(new ResourceManager(player.getPlayerID()));
        playerRepository.save(player);
        // Return id of created player
        return "New player of ID: " + player.getPlayerID();
    }

    // Addtroops to a declared player via their ID, to use, use Postman POST option, make sure you already have a player declared and use:
    // localhost:8080/players/addtroops/(playerID)?troopType=(trooptype)&recruited=(num)
    // An example request: localhost:8080/players/addtroops/1?troopType=ARCHER&recruited=100
    @PostMapping("/players/addtroops/{playerID}")
    public Player addTroops(@PathVariable int playerID, @RequestBody TroopRequest troopRequest) {
        Player player = playerRepository.findById(playerID).orElse(null);
        if (player != null) {
            player.troops.addTroop(troopRequest.getTroopType(), troopRequest.getQuantity());
            player.updatePower();
            return playerRepository.save(player);
        }
        else {
            return null;
        }
    }

    // !!!!!!!!!!!!!!!!!!!!!!! Note to self -- decide if when overflow troop removal passed in to deduct to zero or neglect request entirely


    // Removetroops from a declared player via their ID, to use, use Postman POST option, make sure you have a player declared and use:
    // localhost:8080/players/removetroops/(playerID)?troopType=(trooptype)&deaths=(num)
    // Example request: localhost:8080/players/removetroops/1?troopType=ARCHER&deaths=100
    @PostMapping("/players/removetroops/{playerID}")
    public Player removeTroops(@PathVariable int playerID, @RequestBody TroopRequest troopRequest) {
        Player player = playerRepository.findById(playerID).orElse(null);
        if (player != null) {
            player.troops.removeTroop(troopRequest.getTroopType(), troopRequest.getQuantity());
            player.updatePower();
            return playerRepository.save(player);
        }
        else {
            return null;
        }
    }

    // Delete or "ban" a player from the server, effectively removes all information and data of the player from the database
    // To test use Postman DELETE option, make sure the player you are trying to delete exist, use:
    // localhost:8080/players/banPlayer/(playerID)
    // Example request: localhost:8080/players/banPlayer/1
    @DeleteMapping("/players/banplayer/{playerID}")
    public String banPlayer(@PathVariable int playerID) {
        // Remove provided player from db
        playerRepository.deleteById(playerID);
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
        Player p1 = playerRepository.findById(playerID1).orElse(null);
        Player p2 = playerRepository.findById(playerID2).orElse(null);
        if (p1 != null && p2 != null) {
            TroopCombatCalculator tcc = new TroopCombatCalculator(p1.troops, p2.troops);
            p1.updatePower();
            p2.updatePower();
            return tcc.getResult() + "\n" + p1.getPlayerID() + "\n\n" +  p2.getPlayerID();
        }
        else {
            return null;
        }
    }

    public static class PlayerCreator {
        private String userName;
        private String password;


        public PlayerCreator(String userName, String password) {
            setUserName(userName);
            setPassword(password);
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class TroopRequest {
        private TroopTypes troopType;
        private int quantity;

        public TroopRequest(TroopTypes troopType, int quantity) {
            setTroopType(troopType);
            setQuantity(quantity);
        }

        public TroopTypes getTroopType() {
            return troopType;
        }

        public void setTroopType(TroopTypes troopType) {
            this.troopType = troopType;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}
