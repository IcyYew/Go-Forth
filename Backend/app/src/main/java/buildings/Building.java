package buildings;

import buildings.resourcebuildings.ResourceBuildingManager;
import buildings.troopBuildings.TroopBuildingManager;
import jakarta.persistence.*;

/**
 * Abstract class for buildings.
 * @author Michael Geltz
 */

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer buildingID;

    @Enumerated(EnumType.STRING)
    private BuildingTypes buildingType;

    protected int level;
    protected int stoneUpgradeCost;
    protected int woodUpgradeCost;
    protected int power;

    @ManyToOne
    @JoinColumn(name = "building_manager")
    private BuildingManager buildingManager;

    private TroopBuildingManager troopBuildingManager;

    private ResourceBuildingManager resourceBuildingManager;

    /**
     * Constructor for a building.
     * Takes a level value and a power value.
     * @param level Level value for the building.
     */
    public Building(BuildingTypes buildingType, int level, BuildingManager buildingManager) {
        this.buildingType = buildingType;
        this.level = level;
        this.buildingManager = buildingManager;
    }

    public Building(BuildingTypes buildingType, int level, TroopBuildingManager troopBuildingManager) {
        this.buildingType = buildingType;
        this.level = level;
        this.troopBuildingManager = troopBuildingManager;
    }

    public Building(BuildingTypes buildingType, int level, ResourceBuildingManager resourceBuildingManager) {
        this.buildingType = buildingType;
        this.level = level;
        this.resourceBuildingManager = resourceBuildingManager;
    }

    public Building()
    {

    }

    /**
     * Gets the level for the building.
     * @return Returns the level value.
     */
    public int getLevel() {
        return this.level;
    }
    /**
     * Gets the power for the building.
     * @return Returns the power value.
     */

    public void setLevel(int level)
    {
        this.level = level;
    }

    public BuildingTypes getBuildingType()
    {
        return buildingType;
    }

    public void setBuildingType(BuildingTypes buildingType)
    {
        this.buildingType = buildingType;
    }

    public void upgrade() throws Exception {
        if (this.level < 5 && getLevel() >= this.level++) {
            this.level++;
        }
        else if (this.level == 5){
            throw new Exception("Max level reached for building");
        }
        else {
            throw new Exception("Main building level requirement not met");
        }
    }

    public int getStoneUpgradeCost() {
        return stoneUpgradeCost;
    }

    public void setStoneUpgradeCost(int stoneUpgradeCost) {
        this.stoneUpgradeCost = this.stoneUpgradeCost * (int)((8.0/5.0) * (double)level);
    }

    public int getWoodUpgradeCost() {
        return woodUpgradeCost;
    }

    public void setWoodUpgradeCost(int woodUpgradeCost) {
        this.woodUpgradeCost = this.woodUpgradeCost * (int)((8.0/5.0) * (double)level);
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }
}
