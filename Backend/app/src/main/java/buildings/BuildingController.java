package buildings;

import buildings.Research.Research;
import buildings.Research.ResearchManager;
import buildings.Research.ResearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import player.Player;
import player.PlayerRepository;

import java.util.List;

@RestController
public class BuildingController {

    @Autowired
    private PlayerRepository playerRepository;



   @GetMapping("/buildings/research/getallresearch/{playerID}")
    public ResearchManager researchList(@PathVariable Integer playerID) {
       return playerRepository.getById(playerID).getResearchManager();
    }

    @PostMapping("/buildings/research/levelresearch")
    public Research levelResearch(@RequestBody ResearchLevelRequest researchLevelRequest) {
       ResearchManager researchManager = playerRepository.getById(researchLevelRequest.getPlayerID()).getResearchManager();
       String researchName = researchLevelRequest.getResearchName();
       Research research = researchManager.getResearch(researchName);
       Player player = researchManager.researchLevel(research, playerRepository.getById(researchLevelRequest.getPlayerID()));
       playerRepository.save(player);
       return playerRepository.getById(researchLevelRequest.getPlayerID()).getResearchManager().getResearch(researchName);
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



