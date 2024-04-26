package buildings;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@DiscriminatorValue("OTHERBUILDING")
public abstract class OtherBuilding extends Building
{
    @ManyToOne
    @JoinColumn(name = "building_manager")
    private BuildingManager buildingManager;

    public OtherBuilding(BuildingTypes buildingTypes, int level, BuildingManager buildingManager)
    {
        setBuildingType(buildingTypes);
        setLevel(level);
        setBuildingManager(buildingManager);
    }

    public OtherBuilding()
    {

    }

    public BuildingManager getBuildingManager() {
        return buildingManager;
    }

    public void setBuildingManager(BuildingManager buildingManager) {
        this.buildingManager = buildingManager;
    }
}
