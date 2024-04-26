package buildings;

import buildings.Research.Research;
import buildings.Research.ResearchManager;
import buildings.Research.ResearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import player.Player;
import player.PlayerRepository;

import java.util.List;

import static troops.TroopTypes.*;

@RestController
public class BuildingController {

    @Autowired
    private PlayerRepository playerRepository;



   @GetMapping("/buildings/research/getallresearch/{playerID}")
    public ResearchManager researchList(@PathVariable Integer playerID) {
       return playerRepository.getById(playerID).getResearchManager();
    }

    @PostMapping("/buildings/research/levelresearch/attackbonus")
    public void levelAttackBonus(@RequestBody ResearchLevelRequest researchLevelRequest) {
       Player player = playerRepository.getById(researchLevelRequest.getPlayerID());
       Research research = player.getResearchManager().getResearch(researchLevelRequest.getResearchName());
       if (research.getLevel() < 5) {
           research.setLevel(research.getLevel() + 1);
           player.getTroops().getTroop(ARCHER).setDamage(Math.ceil(player.getTroops().getTroop(ARCHER).getDamage() * 1.15));
           player.getTroops().getTroop(MAGE).setDamage(Math.ceil(player.getTroops().getTroop(MAGE).getDamage() * 1.15));
           player.getTroops().getTroop(WARRIOR).setDamage(Math.ceil(player.getTroops().getTroop(WARRIOR).getDamage() * 1.15));
           player.getTroops().getTroop(CAVALRY).setDamage(Math.ceil(player.getTroops().getTroop(CAVALRY).getDamage() * 1.15));
       }
       player.updatePower();
       playerRepository.save(player);
    }

    @PostMapping("/players/research/levelresearch")
    public void levelResearch(@RequestBody BuildingController.ResearchLevelRequest researchLevelRequest) {
        Player player = playerRepository.getById(researchLevelRequest.getPlayerID());
        Research research = player.getResearchManager().getResearch(researchLevelRequest.getResearchName());
        player.getTroops().getTroop(ARCHER).setDamage(600);
        //player.research.researchLevel(research, player);
        player.getResearchManager().researchLevel(research, player);
        playerRepository.save(player);
        //return playerRepository.getById(researchLevelRequest.getPlayerID()).getResearchManager().getResearch(researchLevelRequest.getResearchName());
    }


    public static class ResearchLevelRequest {
        private String researchName;
        private Integer playerID;

        public ResearchLevelRequest(String researchName, Integer playerID) {
            this.researchName = researchName;
            this.playerID = playerID;
        }

        public String getResearchName() {
            return researchName;
        }

        public void setResearchName(String researchName) {
            this.researchName = researchName;
        }

        public Integer getPlayerID() {
            return playerID;
        }

        public void setPlayerID(Integer playerID) {
            this.playerID = playerID;
        }
    }
}



