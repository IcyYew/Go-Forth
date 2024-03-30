package clans;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import player.Player;
import player.PlayerRepository;

import java.util.List;

/**
 * REST Controller for Clan information.
 *
 */
@RestController
public class ClanController {

    @Autowired
    private ClanRepository clanRepository;

    @Autowired
    private PlayerRepository playerRepository;


    /**
     * Gets the clan with the specified ID from the database.
     * @param clanID The ID of the clan.
     * @return Returns the specified Clan.
     */
    @GetMapping("/clans/getclan/{clanID}")
    public Clan getClan(@PathVariable int clanID) {
        return clanRepository.findById(clanID).orElse(null);
    }

    /**
     * Creates a new clan and adds the player creating the clan to the list of members.
     * @param creationRequest The creation request.
     * @return Returns a string stating that a new clan has been created.
     */
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

    /**
     * Gets the member list for a specific clan using the clan's ID.
     * @param clanID The integer ID for the clan.
     * @return Returns the member list for the clan.
     */
    @GetMapping("/clans/memberlist/{clanID}")
    public List<Player> membersList(@PathVariable int clanID) {
        Clan clan = clanRepository.getById(clanID);

        return clan.getMembers().getMemberList();
    }

    /**
     * Adds a member to the specified clan.
     * @param memberRequest The request to the database for the addition.
     * @return Returns a message saying that the member has been added.
     */
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
    /**
     * Removes a member from the specified clan.
     * @param memberRequest The request to the database for the removal.
     * @return Returns a message saying that the player has been removed.
     */
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

    /**
     * Class for the creation request.
     */
    public static class CreationRequest {
        private int playerID;
        private String clanName;

        /**
         * Constructor for the Creation Request.
         * @param playerID The ID of the player creating the clan.
         * @param clanName The name of the clan being created.
         */
        public CreationRequest(int playerID, String clanName) {
            setClanName(clanName);
            setPlayerID(playerID);
        }

        /**
         * Gets the player ID.
         * @return Returns the player ID.
         */
        public int getPlayerID() {
            return playerID;
        }

        /**
         * Sets the player ID.
         * @param playerID The integer used for the player ID.
         */
        public void setPlayerID(int playerID) {
            this.playerID = playerID;
        }

        /**
         * Gets the clan name.
         * @return Returns the clan name.
         */
        public String getClanName() {
            return clanName;
        }

        /**
         * Sets the clan name.
         * @param clanName The name of the clan.
         */
        public void setClanName(String clanName) {
            this.clanName = clanName;
        }
    }

    /**
     * Class for the member request.
     */
    public static class MemberRequest {
        private int clanID;
        private int playerID;

        /**
         * Constructor for the Member Request.
         * @param clanID Integer ID of the clan being changed.
         * @param playerID Integer ID of the player making the changes.
         */
        public MemberRequest(int clanID, int playerID) {
            setClanID(clanID);
            setPlayerID(playerID);
        }

        /**
         * Gets the clan ID.
         * @return Returns the clan ID.
         */
        public int getClanID() {
            return clanID;
        }

        /**
         * Sets the clan ID
         * @param clanID The integer used for the clan ID.
         */
        public void setClanID(int clanID) {
            this.clanID = clanID;
        }

        /**
         * Gets the player ID.
         * @return Returns the player ID.
         */
        public int getPlayerID() {
            return playerID;
        }

        /**
         * Sets the player ID.
         * @param playerID The integer used for the player ID.
         */
        public void setPlayerID(int playerID) {
            this.playerID = playerID;
        }
    }
}
