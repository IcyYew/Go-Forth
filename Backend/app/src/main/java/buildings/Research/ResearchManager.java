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
import troops.TroopTypes;

import java.util.ArrayList;
import java.util.List;

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
        researchManager.add(new Research("Building Speed", 0, 3, this));

    }

    public int getLevel(String researchName) {
        for (Research research : researchManager) {
            if (research.getResearchName().equals(researchName)) {
                research.getLevel();
            }
        }
        return 0;
    }

    /*public void updateFields() {
        for (Research research : researchManager) {
            /*research.setPlatinumCost(this.getResearch("Research Cost").getPlatinumCost());
            research.setLevel(this.getLevel("Research Cost"));
        }
    }*/

    public void researchLevel(Research research, Player player) {
        if (research.getLevel() < 5) {
            research.setLevel(research.getLevel() + 1);
            checkResearchName(research, player);
        }
        /*for (Research r : researchManager) {
            if (research.getResearchName().equals(researchName)) {
                if (research.getLevel() < 5) {
                    this.getResearch(researchName).setLevel(research.getLevel() + 1);
                    player = checkResearchName(research, player);
                }
            }
        }*/
    }


    public void checkResearchName(Research research, Player player) {
        String researchName = research.getResearchName();
        if (researchName.equals("Training Speed")) {
            trainSpeedHandler(research, player);
        }
        else if (researchName == "Research Cost") {
            researchCostHandler(research, player);
        }
        else if (researchName == "Building Cost") {

        }
        else if (researchName == "Attack Bonus") {
            attackBonusHandler(research, player);

        }
        else if (researchName == "Training Capacity") {

        }
        else if (researchName == "Building Speed") {

        }
    }

    public void researchCostHandler(Research research, Player player) {
        switch (research.getLevel()) {
            case 1:
                research.setPlatinumCost(Math.floor(research.getPlatinumCost() * .99));
                player.setResearchManager(this);
                break;
            case 2:
                research.setPlatinumCost(Math.floor(research.getPlatinumCost() * .98));
                player.setResearchManager(this);
                break;
            case 3:
                research.setPlatinumCost(Math.floor(research.getPlatinumCost() * .97));
                player.setResearchManager(this);
                break;
            case 4:
                research.setPlatinumCost(Math.floor(research.getPlatinumCost() * .97));
                player.setResearchManager(this);
                break;
            case 5:
                research.setPlatinumCost(Math.floor(research.getPlatinumCost() * .97));
                player.setResearchManager(this);
                break;
        }
    }

    public void attackBonusHandler(Research research, Player player) {
        Troop archer = player.getTroops().getTroop(TroopTypes.ARCHER);
        Troop warrior = player.getTroops().getTroop(TroopTypes.WARRIOR);
        Troop mage = player.getTroops().getTroop(TroopTypes.MAGE);
        Troop cavalry = player.getTroops().getTroop(TroopTypes.CAVALRY);
        switch(research.getLevel()) {
            case 1:
                player.getTroops().getTroop(TroopTypes.MAGE).setDamage(player.getTroops().getTroop(TroopTypes.MAGE).getDamage() * 1.15);
                warrior.setDamage(Math.ceil(warrior.getDamage() * 1.15));
                mage.setDamage(Math.ceil(mage.getDamage() * 1.15));
                cavalry.setDamage(Math.ceil(cavalry.getDamage() * 1.15));
                break;
            case 2:
                archer.setDamage(Math.ceil(archer.getDamage() * 1.15));
                warrior.setDamage(Math.ceil(warrior.getDamage() * 1.15));
                mage.setDamage(Math.ceil(mage.getDamage() * 1.15));
                cavalry.setDamage(Math.ceil(cavalry.getDamage() * 1.15));
                break;
            case 3:
                archer.setDamage(Math.ceil(archer.getDamage() * 1.15));
                warrior.setDamage(Math.ceil(warrior.getDamage() * 1.15));
                mage.setDamage(Math.ceil(mage.getDamage() * 1.15));
                cavalry.setDamage(Math.ceil(cavalry.getDamage() * 1.15));
                break;
            case 4:
                archer.setDamage(Math.ceil(archer.getDamage() * 1.15));
                warrior.setDamage(Math.ceil(warrior.getDamage() * 1.15));
                mage.setDamage(Math.ceil(mage.getDamage() * 1.15));
                cavalry.setDamage(Math.ceil(cavalry.getDamage() * 1.15));
                break;
            case 5:
                archer.setDamage(Math.ceil(archer.getDamage() * 1.15));
                warrior.setDamage(Math.ceil(warrior.getDamage() * 1.15));
                mage.setDamage(Math.ceil(mage.getDamage() * 1.15));
                cavalry.setDamage(Math.ceil(cavalry.getDamage() * 1.15));
                break;
        }
    }


    public void trainSpeedHandler(Research research, Player player) {
        TroopTrainingBuilding archeryRange = player.getTroopBuildings().getTroopTrainingBuilding(BuildingTypes.ARCHERYRANGE);
        TroopTrainingBuilding mageTower = player.getTroopBuildings().getTroopTrainingBuilding(BuildingTypes.MAGETOWER);
        TroopTrainingBuilding warriorSchool = player.getTroopBuildings().getTroopTrainingBuilding(BuildingTypes.WARRIORSCHOOL);
        TroopTrainingBuilding stables = player.getTroopBuildings().getTroopTrainingBuilding(BuildingTypes.STABLES);
        switch (research.getLevel()) {
            case 1:
                mageTower.setTrainingTime(Math.floor(mageTower.getTrainingTime() * .97));
                warriorSchool.setTrainingTime(Math.floor(warriorSchool.getTrainingTime() * .97));
                stables.setTrainingTime(Math.floor(stables.getTrainingTime() * .97));
                archeryRange.setTrainingTime(Math.floor(archeryRange.getTrainingTime() * .97));
                break;
            case 2:
                mageTower.setTrainingTime(Math.floor(mageTower.getTrainingTime() * .97));
                warriorSchool.setTrainingTime(Math.floor(warriorSchool.getTrainingTime() * .97));
                stables.setTrainingTime(Math.floor(stables.getTrainingTime() * .97));
                archeryRange.setTrainingTime(Math.floor(archeryRange.getTrainingTime() * .97));
                break;
            case 3:
                mageTower.setTrainingTime(Math.floor(mageTower.getTrainingTime() * .96));
                warriorSchool.setTrainingTime(Math.floor(warriorSchool.getTrainingTime() * .96));
                stables.setTrainingTime(Math.floor(stables.getTrainingTime() * .96));
                archeryRange.setTrainingTime(Math.floor(archeryRange.getTrainingTime() * .96));
                break;
            case 4:
                mageTower.setTrainingTime(Math.floor(mageTower.getTrainingTime() * .97));
                warriorSchool.setTrainingTime(Math.floor(warriorSchool.getTrainingTime() * .97));
                stables.setTrainingTime(Math.floor(stables.getTrainingTime() * .97));
                archeryRange.setTrainingTime(Math.floor(archeryRange.getTrainingTime() * .97));
                break;
            case 5:
                mageTower.setTrainingTime(Math.floor(mageTower.getTrainingTime() * .96));
                warriorSchool.setTrainingTime(Math.floor(warriorSchool.getTrainingTime() * .96));
                stables.setTrainingTime(Math.floor(stables.getTrainingTime() * .96));
                archeryRange.setTrainingTime(Math.floor(archeryRange.getTrainingTime() * .96));
                break;
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
