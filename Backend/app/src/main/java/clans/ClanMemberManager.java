package clans;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import player.Player;

import java.util.ArrayList;
import java.util.List;


/**
 * Class for the Clan Member Manager.
 * Represents the attributes of the clan for use in other classes.
 */
@Entity
public class ClanMemberManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer clanID;


    @OneToMany(cascade = CascadeType.ALL)
    private List<Player> memberManager;

    /**
     * Constructor for the Clan Member Manager.
     * Takes a clan ID and creates a new member list.
     * @param clanID The integer ID for this clan.
     */
    public ClanMemberManager(Integer clanID) {
        this.clanID = clanID;
        this.memberManager = new ArrayList<>();
    }

    /**
     * Empty constructor for Clans.
     */
    public ClanMemberManager() {

    }

    public Integer getClanID() {
        return clanID;
    }

    public void setClanID(Integer clanID) {
        this.clanID = clanID;
    }

    /**
     * Gets the list of clan members.
     * @return Returns the list of clan members.
     */
    public List<Player> getMemberList() {
        return memberManager;
    }

    /**
     * Adds a player to the clan member list.
     * @param player The player being added.
     */
    public void addMember(Player player) {
        memberManager.add(player);
    }

    /**
     * Removes a player from the clan member list.
     * @param player The player being removed.
     */
    public void removeMember(Player player) {
        memberManager.remove(player);
    }

    /**
     * toString method for displaying the Clan Member Manager.
     * @return
     */
    @Override
    public String toString() {
        return "ClanMemberManager{" +
                "clanID=" + clanID +
                ", memberManager=" + memberManager +
                '}';
    }
}
