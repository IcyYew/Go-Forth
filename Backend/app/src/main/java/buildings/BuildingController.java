package buildings;

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
import java.util.ArrayList;
import java.util.List;

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

    @PostMapping("/buildings/upgrade/{playerID}")
    public String upgradeBuilding(@PathVariable int playerID, @RequestBody BuildingRequest buildingRequest)
    {
        Player player = playerRepository.findById(playerID).orElse(null);
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

    @PostMapping("/buildings/collectResources/{playerID}")
    public int collectResources(@PathVariable int playerID, @RequestBody BuildingRequest buildingRequest)
    {
        ResourceBuilding building = resourceBuildingRepository.findById(buildingRequest.getBuildingID()).orElse(null);
        Player player = playerRepository.findById(playerID).orElse(null);
        if (building != null && player != null)
        {
            int amountCollected = building.collectResources();
            switch (buildingRequest.getBuildingType())
            {
                case FARM:
                    player.resources.addResource(ResourceType.FOOD, amountCollected);
                case LUMBERYARD:
                    player.resources.addResource(ResourceType.WOOD, amountCollected);
                case PLATINUMMINE:
                    player.resources.addResource(ResourceType.PLATINUM, amountCollected);
                case QUARRY:
                    player.resources.addResource(ResourceType.STONE, amountCollected);
            }
            buildingRepository.save(building);
            playerRepository.save(player);
            return amountCollected;
        }
        return 0;
    }

    @PostMapping("/buildings/updateResources/{playerID}")
    public List<Integer> updateResources(@PathVariable int playerID)
    {
        Player player = playerRepository.findById(playerID).orElse(null);
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
        private int buildingID;
        private BuildingTypes buildingType;

        public BuildingRequest(int buildingID, BuildingTypes buildingType)
        {
            setBuildingID(buildingID);
            setBuildingType(buildingType);
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
}
