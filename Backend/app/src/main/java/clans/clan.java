package clans;

import jakarta.persistence.*;
import player.Player;

import java.util.List;

@Entity
public class clan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer clanID;

    @Column(name="clan-power")
    private double totalClanPower;

    @Column(name="power-rank")
    private int clanPowerRanking;

    @Column(name="num-members")
    private int clanMembersNumber;





    public void setClanID(Integer clanID) {
        this.clanID = clanID;
    }

    public Integer getClanID() {
        return clanID;
    }
}
