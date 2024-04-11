package player;

import buildings.BuildingManager;
import jakarta.persistence.*;
import resources.ResourceManager;
import troops.TroopManager;

/**
 * Represents a player
 * @author Michael Geltz
 * @version 1.8
 */
@Entity
@Table(name="player")
public class Player {
    // The player class will be the merging of all "managers", could be said to be the manager of managers
    // This is the class that will be "communicating" with a server via the PlayerController class

    // Each player has an ID, currently just starting at 1 and going from there, will eventually be some sort of
    // Random number or Hash generation for security

    //Identifier for database table, also the only "unqiue" method of differentiating players currently
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer playerID;

    //A players commulative power
    @Column(name="power")
    private double power;
    //Resource manager storing and managing a players resources
    /**
     * Represents a player's resources
     */
    @ManyToOne(cascade = CascadeType.ALL)
    ResourceManager resources;

    //Troop manager storing and managing a players troops
    /**
     * Represents a player's troops
     */
    @ManyToOne(cascade = CascadeType.ALL)
    TroopManager troops;

    @ManyToOne(cascade = CascadeType.ALL)
    BuildingManager buildings;

    @Column(name="clan-permissions-level")
    private Integer clanPermissions = 0;

    public Integer getClanPermissions() {
        return clanPermissions;
    }

    public void setClanPermissions(Integer clanPermissions) {
        this.clanPermissions = clanPermissions;
    }

    public void setClanMembershipID(Integer clanMembershipID) {
        this.clanMembershipID = clanMembershipID;
    }

    @Column(name="clan-member-id")
    private Integer clanMembershipID = 0;

    //A players self-created username
    @Column(name="username")
    private String userName;


    //A players password
    @Column(name="password")
    private String password; // might add encryption, not sure if necessary

    @Column(name="locationX")
    private int locationX;

    @Column(name="locationY")
    private int locationY;

    @Column(name="archerFinalDate")
    private String archerFinalDate = "a";

    @Column(name="mageFinalDate")
    private String mageFinalDate = "m";

    @Column(name="cavalryFinalDate")
    private String cavalryFinalDate = "c";

    @Column(name="warriorFinalDate")
    private String warriorFinalDate = "w";

    //Empty constructor to make Jpa happy

    /**
     * Empty constructor
     */
    public Player() {

    }


    /**
     * Gets a player's password
     * @return A player's password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets a player's password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets a player's username
     * @return
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets a player's username
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    // ... further developments will take more setters/getters/etc


    /**
     * Sets a player's clan membership
     * @param clanMembershipID
     */
    public void setClanMembershipID(int clanMembershipID) {
        this.clanMembershipID = clanMembershipID;
    }

    /**
     * Gets a player's clan membership
     * @return The id of the clan a player is in
     */
    public Integer getClanMembershipID() {
        return clanMembershipID;
    }

    // Player constructor, will be expanded for building manager

    /**
     * Creates a player with resources, troops, a unqiue ID, a power, a username, and a password
     * @param resources
     * @param troops
     * @param playerID
     * @param power
     * @param userName
     * @param password
     */
    public Player(ResourceManager resources, TroopManager troops, BuildingManager buildings, int playerID, double power, String userName, String password, int locationX, int locationY) {
        setResources(resources);
        setTroops(troops);
        setBuildings(buildings);
        //this.buildings = buildings;
        setPlayerID(playerID);
        setPower(power);
        setUserName(userName);
        setPassword(password);
        setLocationX(locationX);
        setLocationY(locationY);
    }

    /**
     * Sets a player's resources
     * @param resources
     */
    public void setResources(ResourceManager resources) {
        this.resources = resources;
    }


    /**
     * Gets a player's resources
     * @return A player's resources
     */
    public ResourceManager getResources() {
        return resources;
    }

    /**
     * Gets a player's troops
     * @return A player's troops
     */
    public TroopManager getTroops() {
        return troops;
    }

    /**
     * Sets a player's troops
     * @param troops
     */
    public void setTroops(TroopManager troops) {
        this.troops = troops;
    }

    /**
     * Gets a player's id
     * @return
     */
    public int getPlayerID() {
        return playerID;
    }

    /**
     * Sets a player's ID
     * @param playerID
     */
    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    /**
     * Gets a player's power
     * @return A player's power
     */
    public double getPower() {
        return power;
    }

    /**
     * Updates a player's power
     */
    public void updatePower() {
        this.power = troops.calculateTotalTroopPower();
    }

    /**
     * Sets a player's power
     * @param power
     */
    public void setPower(double power) {
        this.power = troops.calculateTotalTroopPower();
        // ... more when implemented
    }
    public int getLocationX() {
        return locationX;
    }

    public void setLocationX(int locationX) {
        this.locationX = locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public void setLocationY(int locationY) {
        this.locationY = locationY;
    }

    public String getArcherFinalDate() {
        return archerFinalDate;
    }

    public void setArcherFinalDate(String archerFinalDate) {
        this.archerFinalDate = archerFinalDate;
    }

    public String getMageFinalDate() {
        return mageFinalDate;
    }

    public void setMageFinalDate(String mageFinalDate) {
        this.mageFinalDate = mageFinalDate;
    }

    public String getCavalryFinalDate() {
        return cavalryFinalDate;
    }

    public void setCavalryFinalDate(String cavalryFinalDate) {
        this.cavalryFinalDate = cavalryFinalDate;
    }

    public String getWarriorFinalDate() {
        return warriorFinalDate;
    }

    public void setWarriorFinalDate(String warriorFinalDate) {
        this.warriorFinalDate = warriorFinalDate;
    }

    public BuildingManager getBuildings() {
        return buildings;
    }

    public void setBuildings(BuildingManager buildings) {
        this.buildings = buildings;
    }

    /**
     * Returns a string of player info
     * @return A string of the player's info
     */


    @Override
    public String toString() {
        return "Player{" +
                "username" + userName +
                "password" + password +
                "resources=" + resources +
                "troops=" + troops +
                "buildings=" + buildings +
                ", playerID=" + playerID +
                ", power=" + power +
                '}';
    }
}
