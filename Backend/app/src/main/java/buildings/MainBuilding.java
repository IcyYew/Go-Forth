package buildings;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Class for the Main Building.
 * This is the "town hall" for the player.
 * It can level up, and it contributes to a player's power value.
 * @author Michael Geltz.
 */
@Entity
@DiscriminatorValue("MAINBUILDING")
public class MainBuilding extends Building {

    public MainBuilding(BuildingManager buildingManager, int level)
    {
        super(BuildingTypes.MAINBUILDING, level, buildingManager);
    }

    public MainBuilding() {

    }
}