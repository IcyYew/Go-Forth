package buildings;

import buildings.Research.Research;
import buildings.Research.ResearchManager;
import buildings.resourcebuildings.ResourceBuilding;
import buildings.resourcebuildings.ResourceBuildingRepository;
import buildings.troopBuildings.TroopBuildingRepository;
import buildings.troopBuildings.TroopTrainingBuilding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import player.Player;
import player.PlayerRepository;
import resources.ResourceType;

import javax.print.attribute.standard.MediaSize;
import java.math.BigDecimal;
import java.text.DecimalFormat;
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

    @Autowired
    private OtherBuildingRepository otherBuildingRepository;

   @GetMapping("/buildings/research/getallresearch/{playerID}")
    public List<Research> researchList(@PathVariable Integer playerID) {
       List<Research> researchList = new ArrayList<>();
       ResearchManager researchManager = playerRepository.getById(playerID).getResearchManager();
       researchList.add(researchManager.getResearch("Attack Bonus"));
       researchList.add(researchManager.getResearch("Research Cost"));
       researchList.add(researchManager.getResearch("Training Capacity"));
       researchList.add(researchManager.getResearch("Training Speed"));
       researchList.add(researchManager.getResearch("Building Cost"));
       return researchList;
    }

    @PostMapping("/buildings/research/levelresearch/attackbonus")
    public void levelAttackBonus(@RequestBody ResearchLevelRequest researchLevelRequest) {
       Player player = playerRepository.getById(researchLevelRequest.getPlayerID());
       Research research = player.getResearchManager().getResearch(researchLevelRequest.getResearchName());
       if (research.getLevel() < 5
               && research.getPlatinumCost() <= player.getResources().getResource(ResourceType.PLATINUM)
               && player.getBuildings().getOtherBuilding(BuildingTypes.RESEARCHBUILDING).getLevel() >= 2) {
           research.levelUpResearch(research.getLevel() + 1, player.getResearchManager());
           player.getResources().removeResource(ResourceType.PLATINUM, (int)research.getPlatinumCost());
           player.getTroops().getTroop(ARCHER).setDamage(Math.ceil(player.getTroops().getTroop(ARCHER).getDamage() * (research.getLevel() * 1.05)));
           player.getTroops().getTroop(MAGE).setDamage(Math.ceil(player.getTroops().getTroop(MAGE).getDamage() * (research.getLevel() * 1.05)));
           player.getTroops().getTroop(WARRIOR).setDamage(Math.ceil(player.getTroops().getTroop(WARRIOR).getDamage() * (research.getLevel() * 1.05)));
           player.getTroops().getTroop(CAVALRY).setDamage(Math.ceil(player.getTroops().getTroop(CAVALRY).getDamage() * (research.getLevel() * 1.05)));
       }
       player.updatePower();
       playerRepository.save(player);
    }

    @PostMapping("/buildings/research/levelresearch/buildingcost")
    public void levelBuildingCost(@RequestBody ResearchLevelRequest researchLevelRequest) {
        Player player = playerRepository.getById(researchLevelRequest.getPlayerID());
        Research research = player.getResearchManager().getResearch(researchLevelRequest.getResearchName());
        Double multiplierCalc;
        if (research.getLevel() < 5 && research.getPlatinumCost() <= player.getResources().getResource(ResourceType.PLATINUM)
                && player.getBuildings().getOtherBuilding(BuildingTypes.RESEARCHBUILDING).getLevel() >= 2) {
            research.levelUpResearch(research.getLevel() + 1, player.getResearchManager());
            for (Building building : player.getBuildings().getOtherBuildings()) {
                building.setWoodUpgradeCost((int)(building.getWoodUpgradeCost() * .93));
                building.setStoneUpgradeCost((int)(building.getStoneUpgradeCost() * .93));
                multiplierCalc = Math.pow(.93, research.getLevel());
                BigDecimal bd = new BigDecimal(Double.toString(multiplierCalc));
                bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                multiplierCalc = bd.doubleValue();
                building.setCostMultiplier(multiplierCalc);
            }
            for (Building building : player.getTroopBuildings().getTroopBuildings()) {
                building.setWoodUpgradeCost((int)(building.getWoodUpgradeCost() * .93));
                building.setStoneUpgradeCost((int)(building.getStoneUpgradeCost() * .93));
                multiplierCalc = Math.pow(.93, research.getLevel());
                BigDecimal bd = new BigDecimal(Double.toString(multiplierCalc));
                bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                multiplierCalc = bd.doubleValue();
                building.setCostMultiplier(multiplierCalc);
            }
            for (Building building : player.getResourceBuildings().getResourceBuildings()) {
                building.setWoodUpgradeCost((int)(building.getWoodUpgradeCost() * .93));
                building.setStoneUpgradeCost((int)(building.getStoneUpgradeCost() * .93));
                multiplierCalc = Math.pow(.93, research.getLevel());
                BigDecimal bd = new BigDecimal(Double.toString(multiplierCalc));
                bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                multiplierCalc = bd.doubleValue();
                building.setCostMultiplier(multiplierCalc);
            }
        }
        player.updatePower();
        playerRepository.save(player);
    }

    @PostMapping("/buildings/research/levelresearch/researchcost")
    public void levelResearchCost(@RequestBody ResearchLevelRequest researchLevelRequest) {
       Player player = playerRepository.getById(researchLevelRequest.getPlayerID());
       Research research = player.getResearchManager().getResearch(researchLevelRequest.getResearchName());
       Research attackBonus = player.getResearchManager().getResearch("Attack Bonus");
       Research researchCost = player.getResearchManager().getResearch("Research Cost");
       Research trainingSpeed = player.getResearchManager().getResearch("Training Speed");
       Research buildingCost = player.getResearchManager().getResearch("Building Cost");
       Research trainingCapacity = player.getResearchManager().getResearch("Training Capacity");
       if (research.getLevel() < 5
               && research.getPlatinumCost() <= player.getResources().getResource(ResourceType.PLATINUM)
               && player.getBuildings().getOtherBuilding(BuildingTypes.RESEARCHBUILDING).getLevel() >= 1) {
           research.levelUpResearch(research.getLevel() + 1, player.getResearchManager());
           player.getResources().removeResource(ResourceType.PLATINUM,  (int)research.getPlatinumCost());
           attackBonus.setPlatinumCost(Math.ceil(attackBonus.getPlatinumCost() * .97));
           trainingSpeed.setPlatinumCost(Math.ceil(trainingSpeed.getPlatinumCost() * .97));
           buildingCost.setPlatinumCost(Math.ceil(buildingCost.getPlatinumCost() * .97));
           trainingCapacity.setPlatinumCost(Math.ceil(trainingCapacity.getPlatinumCost() * .97));
       }
       player.updatePower();
       playerRepository.save(player);
    }

    @PostMapping("/buildings/research/levelresearch/trainingspeed")
    public void levelTrainingSpeed(@RequestBody ResearchLevelRequest researchLevelRequest) {
       Player player = playerRepository.getById(researchLevelRequest.getPlayerID());
       Research research = player.getResearchManager().getResearch(researchLevelRequest.getResearchName());
       TroopTrainingBuilding archeryRange = player.getTroopBuildings().getTrainingBuilding(BuildingTypes.ARCHERYRANGE);
       TroopTrainingBuilding mageTower = player.getTroopBuildings().getTrainingBuilding(BuildingTypes.MAGETOWER);
       TroopTrainingBuilding warriorSchool = player.getTroopBuildings().getTrainingBuilding(BuildingTypes.WARRIORSCHOOL);
       TroopTrainingBuilding stables = player.getTroopBuildings().getTrainingBuilding(BuildingTypes.STABLES);
       if (research.getLevel() < 5
               && research.getPlatinumCost() <= player.getResources().getResource(ResourceType.PLATINUM)
               && player.getBuildings().getOtherBuilding(BuildingTypes.RESEARCHBUILDING).getLevel() >= 1) {
           research.levelUpResearch(research.getLevel() + 1, player.getResearchManager());
           player.getResources().removeResource(ResourceType.PLATINUM,  (int)research.getPlatinumCost());
           archeryRange.setTrainingTime(Math.floor(archeryRange.getTrainingTime() * .95));
           mageTower.setTrainingTime(Math.floor(mageTower.getTrainingTime() * .95));
           stables.setTrainingTime(Math.floor(stables.getTrainingTime() * .95));
           warriorSchool.setTrainingTime(Math.floor(warriorSchool.getTrainingTime() * .95));
       }
       player.updatePower();
       playerRepository.save(player);
    }

    @PostMapping("/buildings/research/levelresearch/trainingcapacity")
    public void levelTrainingCapacity(@RequestBody ResearchLevelRequest researchLevelRequest) {
       Player player = playerRepository.getById(researchLevelRequest.getPlayerID());
       Research research = player.getResearchManager().getResearch("Training Capacity");
       TroopTrainingBuilding archeryRange = player.getTroopBuildings().getTrainingBuilding(BuildingTypes.ARCHERYRANGE);
       TroopTrainingBuilding mageTower = player.getTroopBuildings().getTrainingBuilding(BuildingTypes.MAGETOWER);
       TroopTrainingBuilding warriorSchool = player.getTroopBuildings().getTrainingBuilding(BuildingTypes.WARRIORSCHOOL);
       TroopTrainingBuilding stables = player.getTroopBuildings().getTrainingBuilding(BuildingTypes.STABLES);
       if (research.getLevel() < 5
               && research.getPlatinumCost() <= player.getResources().getResource(ResourceType.PLATINUM)
               && player.getBuildings().getOtherBuilding(BuildingTypes.RESEARCHBUILDING).getLevel() >= 3) {
           research.levelUpResearch(research.getLevel() + 1, player.getResearchManager());
           player.getResources().removeResource(ResourceType.PLATINUM,  (int)research.getPlatinumCost());
           archeryRange.setTrainingCapacity(archeryRange.getTrainingCapacity() + 10);
           mageTower.setTrainingCapacity(mageTower.getTrainingCapacity() + 10);
           warriorSchool.setTrainingCapacity(warriorSchool.getTrainingCapacity() + 10);
           stables.setTrainingCapacity(stables.getTrainingCapacity() + 10);
       }
       player.updatePower();
       playerRepository.save(player);
    }

    @GetMapping("/buildings/getPlayerBuildings/{playerID}")
    public List<Building> getPlayerBuildings(@PathVariable int playerID)
    {
        Player player = playerRepository.findById(playerID).orElse(null);
        if (player != null)
        {
            List<Building> list = new ArrayList<>();
            list.addAll(player.resourceBuildings.getResourceBuildings());
            list.addAll(player.troopBuildings.getTroopBuildings());
            list.addAll(player.buildings.getOtherBuildings());
            return list;
        }
        return null;
    }

    @GetMapping("/buildings/getBuilding/{buildingID}")
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

    @PostMapping("/buildings/upgrade")
    public Player upgradeBuilding(@RequestBody BuildingRequest buildingRequest) throws Exception {
        Player player = playerRepository.findById(buildingRequest.getPlayerID()).orElse(null);

        if (player != null)
        {
            Building building = player.getBuildingOfType(buildingRequest.getBuildingType());
            {
                if (building.getLevel() < player.getBuildingOfType(BuildingTypes.MAINBUILDING).getLevel()) {
                    if (player.resources.getResource(ResourceType.WOOD) >= building.getWoodUpgradeCost() &&
                            player.resources.getResource(ResourceType.STONE) >= building.getStoneUpgradeCost()) {
                        player.resources.removeResource(ResourceType.STONE, building.getStoneUpgradeCost());
                        player.resources.removeResource(ResourceType.WOOD, building.getWoodUpgradeCost());
                        building.upgrade();
                        return playerRepository.save(player);
                    } else {
                        throw new Exception("Insufficient resources for upgrade!");
                    }
                }
                else
                {
                    throw new Exception("Upgrade main building first!");
                }
            }
        }
        throw new Exception("Player not found!");
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

    @PostMapping("/buildings/getResourcesHeld")
    public int getResourcesHeld(@RequestBody BuildingRequest buildingRequest)
    {
        Player player = playerRepository.findById(buildingRequest.getPlayerID()).orElse(null);
        if (player != null)
        {
            return player.resourceBuildings.getResources(buildingRequest.getBuildingType());
        }
        return 0;
    }

    @PostMapping("/buildings/collectResources")
    public void collectResources(@RequestBody BuildingRequest buildingRequest)
    {
        Player player = playerRepository.findById(buildingRequest.getPlayerID()).orElse(null);
        if (player != null)
        {
            int amountCollected = player.resourceBuildings.collectResources(buildingRequest.buildingType);
            switch (buildingRequest.getBuildingType())
            {
                case FARM:
                    player.resources.addResource(ResourceType.FOOD, amountCollected);
                    break;
                case LUMBERYARD:
                    player.resources.addResource(ResourceType.WOOD, amountCollected);
                    break;
                case PLATINUMMINE:
                    player.resources.addResource(ResourceType.PLATINUM, amountCollected);
                    break;
                case QUARRY:
                    player.resources.addResource(ResourceType.STONE, amountCollected);
                    break;
            }
            playerRepository.save(player);
        }
    }

    @PostMapping("/buildings/updateResources/{playerID}")
    public Player updateResources(@PathVariable int playerID)
    {
        Player player = playerRepository.findById(playerID).orElse(null);
        if (player != null)
        {
            for (ResourceBuilding building : player.resourceBuildings.resourceBuildingManager)
            {
                building.updateResources();
            }
            return playerRepository.save(player);
        }
        return null;
    }

    public static class BuildingRequest
    {
        private int playerID;
        private BuildingTypes buildingType;

        public BuildingRequest(BuildingTypes buildingType, int playerID)
        {
            setPlayerID(playerID);
            setBuildingType(buildingType);
        }

        public BuildingTypes getBuildingType() {
            return buildingType;
        }

        public void setBuildingType(BuildingTypes buildingType) {
            this.buildingType = buildingType;
        }

        public int getPlayerID() {
            return playerID;
        }

        public void setPlayerID(int playerID) {
            this.playerID = playerID;
        }
    }
}

