package buildings.Research;


import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import player.PlayerRepository;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ResearchManager {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer playerID;

    @OneToMany(mappedBy = "researchManager", cascade = CascadeType.ALL)
    private List<Research> researchManager;

    public ResearchManager(Integer playerId) {
        this.playerID = playerId;
        this.researchManager = new ArrayList<>();
        initializeResearch();
    }

    public ResearchManager() {

    }

    private void initializeResearch() {
        researchManager.add(new Research("Training Speed", 0, 1));
        researchManager.add(new Research("Research Cost", 0, 1));
        researchManager.add(new Research("Building Cost", 0, 2));
        researchManager.add(new Research("Attack Bonus", 0, 2));
        researchManager.add(new Research("Training Capacity", 0, 3));
        researchManager.add(new Research("Building Speed", 0, 3));

    }

    public int getLevel(String researchName) {
        for (Research research : researchManager) {
            if (research.getResearchName() == researchName) {
                research.getLevel();
            }
        }
        return 0;
    }



    public void researchLevel(String researchName) {
        for (Research research : researchManager) {
            if (research.getResearchName() == researchName) {
                if (research.getLevel() <= 5) {

                }
            }
        }
    }


    public void checkResearchName(Research research) {
        String researchName = research.getResearchName();
        if (researchName == "Training Speed") {
            trainSpeedHandler(research);
        }
        else if (researchName == "Research Cost") {

        }
        else if (researchName == "Building Cost") {

        }
        else if (researchName == "Attack Bonus") {

        }
        else if (researchName == "Training Capacity") {

        }
        else if (researchName == "Building Speed") {

        }
    }


    public void trainSpeedHandler(Research research) {

    }

    @Override
    public String toString() {
        return "ResearchManager{" +
                "playerID=" + playerID +
                ", researchManager=" + researchManager +
                '}';
    }
}
