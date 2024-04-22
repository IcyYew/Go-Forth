package buildings;

import buildings.resourcebuildings.ResourceBuilding;
import buildings.resourcebuildings.ResourceBuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import player.Player;
import player.PlayerRepository;
import resources.ResourceType;

import java.util.List;

@RestController
public class BuildingController {

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ResourceBuildingRepository resourceBuildingRepository;

    @GetMapping("/buildings/getall")
    public List<Building> getAllBuildings()
    {
        return buildingRepository.findAll();
    }

    @GetMapping("/buildings/getResourceBuildings")
    public List<ResourceBuilding> getResourceBuildings()
    {
        return resourceBuildingRepository.findAll();
    }

    @GetMapping("/buildings/{id}")
    public Building getBuilding(@PathVariable int buildingID)
    {
        return buildingRepository.findById(buildingID).orElse(null);
    }

    @PostMapping("/buildings/upgrade/{id}")
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

    @GetMapping("/buildings/getResourcesHeld/{id}")
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

    @PostMapping("/buildings/collectResources/{id}")
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
}
