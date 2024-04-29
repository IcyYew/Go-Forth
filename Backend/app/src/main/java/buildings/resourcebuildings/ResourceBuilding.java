package buildings.resourcebuildings;

import buildings.Building;
import buildings.BuildingManager;
import buildings.BuildingTypes;
import buildings.troopBuildings.TroopBuildingManager;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import resources.Resource;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("RESOURCEBUILDING")
public abstract class ResourceBuilding extends Building
{
    public int resources;
    public int resourceLimit;
    public int resourceProductionRate;
    public LocalDateTime timeLastCollected;

    @ManyToOne
    @JoinColumn(name = "resource_building_manager")
    private ResourceBuildingManager resourceBuildingManager;

    public ResourceBuilding(BuildingTypes buildingTypes, int level, ResourceBuildingManager resourceBuildingManager)
    {
        setBuildingType(buildingTypes);
        setLevel(level);
        setResourceBuildingManager(resourceBuildingManager);
    }

    public ResourceBuilding()
    {

    }

    @Override
    public void upgrade() throws Exception {
        if (this.level <= 5) {
            this.level++;
            this.power *= 1.5;
            this.resourceLimit *= 1.5;
            resourceProductionRate += 1;
            setStoneUpgradeCost(level);
            setWoodUpgradeCost(level);
        }
        else
        {
            throw new Exception("Max level reached");
        }
    }

    public void updateResources()
    {
        LocalDateTime currentTime = LocalDateTime.now();
        Duration timeSinceCall = Duration.between(timeLastCollected, currentTime);
        long seconds = timeSinceCall.getSeconds();
        resources += seconds * resourceProductionRate;
        if (resources >= resourceLimit)
        {
            resources = resourceLimit;
        }
        timeLastCollected = currentTime;
    }

    public int collectResources()
    {
        updateResources();
        int collectedResources = resources;
        resources = 0;
        timeLastCollected = LocalDateTime.now();
        return collectedResources;
    }

    public int getResourceProductionRate()
    {
        return this.resourceProductionRate;
    }

    public void setResourceProductionRate(int resourceProductionRate)
    {
        this.resourceProductionRate = resourceProductionRate;
    }

    public LocalDateTime getTimeLastCollected() {
        return timeLastCollected;
    }

    public void setTimeLastCollected(LocalDateTime timeLastCollected) {
        this.timeLastCollected = timeLastCollected;
    }

    public int getResources() {
        updateResources();
        return resources;
    }

    public void setResources(int resources) {
        this.resources = resources;
    }

    public ResourceBuildingManager getResourceBuildingManager() {
        return resourceBuildingManager;
    }

    public void setResourceBuildingManager(ResourceBuildingManager resourceBuildingManager) {
        this.resourceBuildingManager = resourceBuildingManager;
    }

    public int getResourceLimit() {
        return resourceLimit;
    }

    public void setResourceLimit(int resourceLimit) {
        this.resourceLimit = resourceLimit;
    }
}
