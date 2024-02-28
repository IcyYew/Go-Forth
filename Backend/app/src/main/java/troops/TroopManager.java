package troops;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;


import static troops.TroopTypes.*;

// Serializer for the troopmanager because it was impossible to pass information into fields with the PlayerController on Postman
@JsonSerialize(using = TroopManagerSerializer.class)
@Entity
public class TroopManager {
    // ID linking troops to a player via ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer playerId;

    // Troop manager for managing troops
    @OneToMany(mappedBy = "troopManager", cascade = CascadeType.ALL)
    private List<Troop> troopManager;

    // A troop managers total troop power, useless in practice, temporarily in DB table
    private long totalTroopPower;

    // Constructor for troopmanager, initalizes troops for a player of playerId, in practice this should only occur once per player
    public TroopManager(Integer playerId) {
        this.playerId = playerId;
        this.troopManager = new ArrayList<>();
        initializeTroops();
    }

    // Initializes troop to 0 for a player, subject to change
    private void initializeTroops() {
        troopManager.add(new Warrior(this, 0));
        troopManager.add(new Archer(this, 0));
        troopManager.add(new Mage(this, 0));
        troopManager.add(new Cavalry(this, 0));
    }

    // Empty constructor to make Jpa happy
    public TroopManager() {

    }

    public long getPlayerId() {
        return this.playerId;
    }


    // Returns specific number of a trooptype currently owned
    public int getTroopNum(TroopTypes troopType) {
        for (Troop troop : troopManager) {
            if (troop.getTroopType() == troopType) {
                return troop.getQuantity();
            }
        }
        return 0;
    }

    // calculates a troopmanagers total power taking into account each troops different power rating
    public long calculateTotalTroopPower() {
        totalTroopPower = 0;
        totalTroopPower += getTroopNum(ARCHER) * new Archer().getPower();
        totalTroopPower += getTroopNum(MAGE) * new Mage().getPower();
        totalTroopPower += getTroopNum(WARRIOR) * new Warrior().getPower();
        totalTroopPower += getTroopNum(CAVALRY) * new Cavalry().getPower();
        return totalTroopPower;
    }

    // Add troop(s) to troopmanager
    public void addTroop(TroopTypes troopType, int quantity) {
        for (Troop troop : troopManager) {
            if (troop.getTroopType() == troopType) {
                troop.setQuantity(troop.getQuantity() + quantity);
            }
        }
    }
    // Remove troop(s) from troopmanager
    // Basic logic to make sure a specific troop type isnt deducted to a value below zero
    // !!!! Hidden issue here that sets player troop count to negative when a troopmanager is loaded with non-zero value
    public void removeTroop(TroopTypes troopType, int quantity) {
        for (Troop troop : troopManager) {
            if (troop.getTroopType() == troopType) {
                int current = troop.getQuantity();
                if (current >= quantity) {
                    troop.setQuantity(current - quantity);
                    break;
                }
                troop.setQuantity(0);
            }
        }
    }


    @Override
    public String toString() {
        return "TroopManager{" +
                "playerId=" + playerId +
                ", troopManager=" + troopManager +
                ", totalTroopPower=" + totalTroopPower +
                '}';
    }
}
