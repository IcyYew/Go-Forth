package buildings.Research;


import buildings.BuildingTypes;
import buildings.troopBuildings.TroopTrainingBuilding;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import player.Player;
import player.PlayerRepository;
import troops.Archer;
import troops.Troop;
import troops.TroopManager;
import troops.TroopTypes;

import java.util.ArrayList;
import java.util.List;

import static troops.TroopTypes.*;

@JsonSerialize(using = ResearchSerializer.class)
@Entity
public class ResearchManager {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer playerID;

    @OneToMany(mappedBy = "researchManager", cascade = CascadeType.ALL)
    private List<Research> researchManager;

    public ResearchManager(Integer playerId) {
        setPlayerID(playerId);
        this.researchManager = new ArrayList<>();
        initializeResearch();
    }

    public void setPlayerID(Integer playerID) {
        this.playerID = playerID;
    }

    public ResearchManager() {

    }


    public Research getResearch(String researchName) {
        for (Research research : researchManager) {
            if (research.getResearchName().equals(researchName)) {
                return research;
            }
        }
        return null;
    }

    private void initializeResearch() {
        researchManager.add(new Research("Training Speed", 0, 1, this));
        researchManager.add(new Research("Research Cost", 0, 1, this));
        researchManager.add(new Research("Building Cost", 0, 2, this));
        researchManager.add(new Research("Attack Bonus", 0, 2, this));
        researchManager.add(new Research("Training Capacity", 0, 3, this));

    }

    public int getLevel(String researchName) {
        for (Research research : researchManager) {
            if (research.getResearchName().equals(researchName)) {
                research.getLevel();
            }
        }
        return 0;
    }

    public void updateFields() {
        for (Research research : researchManager) {
            research.setPlatinumCost(this.getResearch("Research Cost").getPlatinumCost());
            research.setLevel(this.getLevel("Research Cost"));
        }
    }


    @Override
    public String toString() {
        return "ResearchManager{" +
                "playerID=" + playerID +
                ", researchManager=" + researchManager +
                '}';
    }
}
