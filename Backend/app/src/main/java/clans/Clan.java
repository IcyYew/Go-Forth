package clans;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import player.Player;
import player.PlayerRepository;

import java.util.ArrayList;
import java.util.List;

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


    public void setClanMembersNumber() {
        this.clanMembersNumber = memberManager.getMemberList().size();
    }

    public Clan() {
    }

    public Clan(int clanID, String clanName, ClanMemberManager memberManager) {
        setClanID(clanID);
        this.clanName = clanName;
        this.memberManager = memberManager;
    }

    public String getClanName() {
        return clanName;
    }

    public void setClanName(String clanName) {
        this.clanName = clanName;
    }

    public void setMembers(ClanMemberManager members) {
        this.memberManager = members;
    }

    public ClanMemberManager getMembers() {
        return memberManager;
    }

    public void calculateTotalClanPower() {
        this.totalClanPower = 0;
        for (Player p : memberManager.getMemberList()) {
            totalClanPower += p.getPower();
        }
    }

    public void setClanID(Integer clanID) {
        this.clanID = clanID;
    }

    public Integer getClanID() {
        return clanID;
    }

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
