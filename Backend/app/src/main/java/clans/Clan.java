package clans;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import player.Player;
import player.PlayerRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for Clans.
 * Contains table information and getters and setters for the Clan.
 */
@Entity
public class Clan {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer clanID;

    @Column(name="clan-name")
    private String clanName;

    @Column(name="clan-power")
    private double totalClanPower;

    @Column(name="num-members")
    private int clanMembersNumber;

    @Column(name="max-members")
    private int clanMembersMax = 50;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_manager_id")
    private ClanMemberManager memberManager;

    /**
     * Sets a clan member's number to the last number in the member list.
     */
    public void setClanMembersNumber() {
        this.clanMembersNumber = memberManager.getMemberList().size();
    }

    /**
     * Empty constructor for a Clan.
     */
    public Clan() {
    }

    /**
     * Constructor for a Clan.
     * Takes a clan ID, clan name, and a MemberManager.
     * @param clanID This clan's ID.
     * @param clanName This clan's name.
     * @param memberManager Member Manager associated with this clan.
     */
    public Clan(int clanID, String clanName, ClanMemberManager memberManager) {
        setClanID(clanID);
        this.clanName = clanName;
        this.memberManager = memberManager;
    }

    /**
     * Gets the clan's name.
     * @return Returns the clan's name as a string.
     */
    public String getClanName() {
        return clanName;
    }

    /**
     * Sets the clan's name.
     * @param clanName The name for the clan.
     */
    public void setClanName(String clanName) {
        this.clanName = clanName;
    }

    /**
     * Sets the list of members for this clan.
     * @param members The ClanMemberManager associated with this clan.
     */
    public void setMembers(ClanMemberManager members) {
        this.memberManager = members;
    }

    /**
     * Gets the MemberManager object for this clan.
     * @return Returns the Membermanager object for this clan.
     */
    public ClanMemberManager getMembers() {
        return memberManager;
    }

    /**
     * Calculates the combined power of all players in this clan.
     */
    public void calculateTotalClanPower() {
        this.totalClanPower = 0;
        for (Player p : memberManager.getMemberList()) {
            totalClanPower += p.getPower();
        }
    }

    /**
     * Sets the clan's ID.
     * @param clanID The integer ID being used for this clan.
     */
    public void setClanID(Integer clanID) {
        this.clanID = clanID;
    }

    /**
     * Gets the clan's ID.
     * @return Returns the integer ID for this clan.
     */
    public Integer getClanID() {
        return clanID;
    }

    /**
     * toString method for displaying this clan.
     * @return
     */
    @Override
    public String toString() {
        return "Clan{" +
                "clanID=" + clanID +
                ", clanName='" + clanName + '\'' +
                ", totalClanPower=" + totalClanPower +
                ", clanMembersNumber=" + clanMembersNumber +
                ", clanMembersMax=" + clanMembersMax +
                '}';
    }
}
