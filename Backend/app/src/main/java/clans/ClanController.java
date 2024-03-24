package clans;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import player.Player;
import player.PlayerRepository;

import java.util.List;

@RestController
public class ClanController {

    @Autowired
    private ClanRepository clanRepository;

    @Autowired
    private PlayerRepository playerRepository;




    @GetMapping("/clans/getclan/{clanID}")
    public Clan getClan(@PathVariable int clanID) {
        return clanRepository.findById(clanID).orElse(null);
    }

    @PostMapping("/clans/createclan")
    public String createClan(@RequestBody CreationRequest creationRequest) {
        Player player = playerRepository.getById(creationRequest.getPlayerID());

        if (player.getClanMembershipID() == 0) {
            Clan clan = new Clan();
            clan.setClanName(creationRequest.getClanName());
            clan = clanRepository.save(clan);
            clan.setMembers(new ClanMemberManager(clan.getClanID()));
            clan.getMembers().addMember(player);
            clan.setClanMembersNumber();
            clan.calculateTotalClanPower();

            player.setClanMembershipID(clan.getClanID());


            clanRepository.save(clan);
            playerRepository.save(player);
            return "New clan of name: " + clan.getClanName();
        }
        else {
            return null;
        }
    }

    @GetMapping("/clans/memberlist/{clanID}")
    public List<Player> membersList(@PathVariable int clanID) {
        Clan clan = clanRepository.getById(clanID);

        return clan.getMembers().getMemberList();
    }

    @PostMapping("/clans/addmember")
    @Transactional
    public String addPlayerToClan(@RequestBody MemberRequest memberRequest) {
        Clan clan = clanRepository.getById(memberRequest.getClanID());
        Player player = playerRepository.getById(memberRequest.getPlayerID());
        if (clan != null && player != null && player.getClanMembershipID() == 0) {
            clan.getMembers().addMember(player);
            player.setClanMembershipID(clan.getClanID());
            clan.calculateTotalClanPower();
            clan.setClanMembersNumber();

            playerRepository.save(player);
            clanRepository.save(clan);
            return "Player " + player.getUserName() + " added to " + clan.getClanName();
        }
        else {
            return null;
        }
    }

    @PostMapping("/clans/removemember")
    @Transactional
    public String removePlayerFromClan(@RequestBody MemberRequest memberRequest) {
        Clan clan = clanRepository.getById(memberRequest.getClanID());
        Player player = playerRepository.getById(memberRequest.getPlayerID());
        if (clan != null && player != null && player.getClanMembershipID() == clan.getClanID()) {
            clan.getMembers().removeMember(player);
            player.setClanMembershipID(0);
            clan.calculateTotalClanPower();
            clan.setClanMembersNumber();

            playerRepository.save(player);
            clanRepository.save(clan);
            if (clan.getMembers().getMemberList().size() != 0) {
                return "Player " + player.getUserName() + " removed from " + clan.getClanID();
            }
            else {
                clanRepository.deleteById(clan.getClanID());
                return "Last player removed from clan, clan deleted";
            }
        }
        else {
            return null;
        }
    }

    public static class CreationRequest {
        private int playerID;
        private String clanName;

        public CreationRequest(int playerID, String clanName) {
            setClanName(clanName);
            setPlayerID(playerID);
        }

        public int getPlayerID() {
            return playerID;
        }

        public void setPlayerID(int playerID) {
            this.playerID = playerID;
        }

        public String getClanName() {
            return clanName;
        }

        public void setClanName(String clanName) {
            this.clanName = clanName;
        }
    }


    public static class MemberRequest {
        private int clanID;
        private int playerID;
        public MemberRequest(int clanID, int playerID) {
            setClanID(clanID);
            setPlayerID(playerID);
        }

        public int getClanID() {
            return clanID;
        }

        public void setClanID(int clanID) {
            this.clanID = clanID;
        }

        public int getPlayerID() {
            return playerID;
        }

        public void setPlayerID(int playerID) {
            this.playerID = playerID;
        }
    }
}
