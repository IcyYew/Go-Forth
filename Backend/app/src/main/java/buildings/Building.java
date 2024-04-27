package buildings;

import buildings.resourcebuildings.ResourceBuildingManager;
import buildings.troopBuildings.TroopBuildingManager;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

import java.util.Optional;

/**
 * Abstract class for buildings.
 * @author Ryan Johnson
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer buildingID;

    @Enumerated(EnumType.STRING)
    private BuildingTypes buildingType;

    protected int level;

    protected int stoneUpgradeCost;

    protected int woodUpgradeCost;

    protected int power;

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
        if (this.level < 5) {
            this.level++;
            this.power *= 1.5;
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
