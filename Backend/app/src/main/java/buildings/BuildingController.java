package buildings;

import buildings.Research.Research;
import buildings.Research.ResearchManager;
import buildings.Research.ResearchRepository;
import buildings.resourcebuildings.ResourceBuilding;
import buildings.resourcebuildings.ResourceBuildingRepository;
import buildings.troopBuildings.TroopBuildingRepository;
import buildings.troopBuildings.TroopTrainingBuilding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import player.Player;
import player.PlayerRepository;
import resources.ResourceType;

import java.util.ArrayList;
import java.util.List;

import static troops.TroopTypes.*;

@RestController
public class BuildingController {

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ResourceBuildingRepository resourceBuildingRepository;

    @Autowired
    private TroopBuildingRepository troopBuildingRepository;

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
    @Autowired
    private OtherBuildingRepository otherBuildingRepository;

    @GetMapping("/buildings/{buildingID}")
    public Building getBuilding(@PathVariable int buildingID)
    {
        return buildingRepository.findById(buildingID).orElse(null);
    }

    @GetMapping("/buildings/getTotalBuildingPower/{playerID}")
    public long getTotalBuildingPower(@PathVariable int playerID)
    {
        Player player = playerRepository.findById(playerID).orElse(null);
        if (player != null)
        {
            long totalPower = 0;
            totalPower += player.troopBuildings.calculateTotalTroopBuildingPower();
            totalPower += player.resourceBuildings.calculateTotalResourceBuildingPower();
            totalPower += player.buildings.calculateTotalOtherBuildingPower();
            return totalPower;
        }
        return 0;
    }

    @PostMapping("/buildings/upgrade/{buildingID}")
    public String upgradeBuilding(@PathVariable int buildingID, @RequestBody BuildingRequest buildingRequest)
    {
        Player player = playerRepository.findById(buildingRequest.getPlayerID()).orElse(null);
        Building building = buildingRepository.findById(buildingRequest.getBuildingID()).orElse(null);
        if (building != null && player != null)
        {
            try {
                building.upgrade();
                buildingRepository.save(building);
                playerRepository.save(player);
                return "Building upgraded to level " + building.getLevel() + ".";
            }
            catch (Exception e)
            {
                return e.getMessage();
            }
        }
        return "Building not found.";
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
    @GetMapping("/buildings/getResourcesHeld/{buildingID}")
    public int getResourcesHeld(@PathVariable int buildingID)
    {
        ResourceBuilding building = resourceBuildingRepository.findById(buildingID).orElse(null);
        if (building != null)
        {
            return building.getResources();
        }
        else
        {
            return 0;
        }
    }

    @PostMapping("/buildings/collectResources/{buildingID}")
    public long collectResources(@PathVariable int buildingID, @RequestBody BuildingRequest buildingRequest)
    {
        ResourceBuilding building = resourceBuildingRepository.findById(buildingRequest.getBuildingID()).orElse(null);
        Player player = playerRepository.findById(buildingRequest.getPlayerID()).orElse(null);
        if (building != null && player != null)
        {
            switch (buildingRequest.getBuildingType())
            {
                case FARM:
                    player.resources.addResource(ResourceType.FOOD, building.collectResources());
                case LUMBERYARD:
                    player.resources.addResource(ResourceType.WOOD, building.collectResources());
                case PLATINUMMINE:
                    player.resources.addResource(ResourceType.PLATINUM, building.collectResources());
                case QUARRY:
                    player.resources.addResource(ResourceType.STONE, building.collectResources());
            }
            buildingRepository.save(building);
            playerRepository.save(player);
        }
        return 0;
    }

    @PostMapping("/buildings/updateResources/{buildingID}")
    public List<Integer> updateResources(@PathVariable int playerID, @RequestBody UpdateRequest updateRequest)
    {
        Player player = playerRepository.findById(updateRequest.getPlayerID()).orElse(null);
        if (player != null)
        {
            List<Integer> list = new ArrayList<>();
            for (ResourceBuilding building : player.resourceBuildings.resourceBuildingManager)
            {
                building.updateResources();
                list.add(building.getResources());
            }
            playerRepository.save(player);
            return list;
        }
        return null;
    }

    public static class BuildingRequest
    {
        private int playerID;
        private int buildingID;
        private BuildingTypes buildingType;

        public BuildingRequest(int playerID, int buildingID, BuildingTypes buildingType)
        {
            setPlayerID(playerID);
            setBuildingID(buildingID);
            setBuildingType(buildingType);
        }

        public int getPlayerID() {
            return playerID;
        }

        public void setPlayerID(int playerID) {
            this.playerID = playerID;
        }

        public int getBuildingID() {
            return buildingID;
        }

        public void setBuildingID(int buildingID) {
            this.buildingID = buildingID;
        }

        public BuildingTypes getBuildingType() {
            return buildingType;
        }

        public void setBuildingType(BuildingTypes buildingType) {
            this.buildingType = buildingType;
        }
    }

    public static class UpdateRequest
    {
        private int playerID;

        public UpdateRequest(int playerID)
        {
            setPlayerID(playerID);
        }

        public int getPlayerID() {
            return playerID;
        }

        public void setPlayerID(int playerID) {
            this.playerID = playerID;
        }
    }
}

