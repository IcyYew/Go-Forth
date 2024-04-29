package buildings;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Class for the Main Building.
 * This is the "town hall" for the player.
 * It can level up, and it contributes to a player's power value.
 * @author Michael Geltz.
 */
@Entity
@DiscriminatorValue("MAINBUILDING")
public class MainBuilding extends OtherBuilding {

    public MainBuilding(BuildingManager buildingManager, int level)
    {
        setBuildingType(BuildingTypes.MAINBUILDING);
        setBuildingManager(buildingManager);
        setLevel(level);
        setPower(64);
        setStoneUpgradeCost(400);
        setWoodUpgradeCost(400);
    }

    public MainBuilding()
    {

    }
}