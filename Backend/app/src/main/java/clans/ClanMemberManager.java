package clans;

import jakarta.persistence.*;
import player.Player;

import java.util.ArrayList;
import java.util.List;


@Entity
public class ClanMemberManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer clanID;

    @OneToOne(mappedBy = "memberManager", cascade = CascadeType.ALL)
    private Clan clan;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Player> memberManager;

    public ClanMemberManager(Integer clanID) {
        this.clanID = clanID;
        this.memberManager = new ArrayList<>();
    }

    public ClanMemberManager() {

    }

    public List<Player> getMemberList() {
        return memberManager;
    }

    public void addMember(Player player) {
        memberManager.add(player);
    }

    public void removeMember(Player player) {
        memberManager.remove(player);
    }

    @Override
    public String toString() {
        return "ClanMemberManager{" +
                "clanID=" + clanID +
                ", memberManager=" + memberManager +
                '}';
    }
}
