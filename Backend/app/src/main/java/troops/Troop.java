package troops;


import jakarta.persistence.*;

/**
 * The abstract class representing the different troops.
 * This abstract class serves as a template for the different troops in the game.
 * @author Michael Geltz
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Troop {


    // Unqiue ID for each resource entity, not sure if best way of doing this because each player currently has four troop types
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer troopID;


    // Type of troop
    @Enumerated(EnumType.STRING)
    private TroopTypes troopType;

    // power of a troop, think this is useless? not going to delete to be safe
    private int power;

    // Quantity of a troop
    private int quantity;

    // Joins all resources into a single column
    @ManyToOne
    @JoinColumn(name = "troop_manager_id")
    private TroopManager troopManager;



    // Useless stuffs below, not removing for now
    // !!!! implement food consumption
    private int damage;
    private int health;
    private int movementSpeed;
    private int attackSpeed;
    private TroopTypes weakness;


    /**
     * Constructor for the Troop class.
     * Creates an instance of a Troop using a troopType, quantity, and troopManager.
     * @param troopType The type of troop being created.
     * @param quantity The quantity of troop being created.
     * @param troopManager
     */
    public Troop(TroopTypes troopType, int quantity, TroopManager troopManager) {
        this.troopType = troopType;
        this.quantity = quantity;
        this.troopManager = troopManager;
    }


    /**
     * Empty Constructor for Troop.
     */
    public Troop() {

    }

    /**
     * Gets the quantity of Troop.
     * @return Returns the Troop's quantity value.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of Troop.
     * @param quantity The quantity of Troop.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the TroopType of this troop.
     * @return Returns a TroopType enum value.
     */
    public TroopTypes getTroopType() {
        return troopType;
    }

    /**
     * Sets the troopType of Troop.
     * @param troopType The troopType of Troop.
     */
    public void setTroopType(TroopTypes troopType) {
        this.troopType = troopType;
    }

    /**
     * Sets the power of Troop.
     * @param power The troopType of Troop.
     */
    public void setPower(int power) {
        this.power = power;
    }

    /**
     * Sets the damage of Troop.
     * @param damage The troopType of Troop.
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * Sets the health of Troop.
     * @param health The troopType of Troop.
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Sets the movement speed of Troop.
     * @param movementSpeed The troopType of Troop.
     */
    public void setMovementSpeed(int movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    /**
     * Sets the attack speed of Troop.
     * @param attackSpeed The troopType of Troop.
     */
    public void setAttackSpeed(int attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    /**
     * Gets the weakness of a Troop. Implemented in the concrete class.
     * @return Returns the troop against which this Troop is weak.
     */
    public abstract TroopTypes getWeakness();

    /**
     * Gets the power of a Troop. Implemented in the concrete class.
     * @return Returns the power value.
     */
    public abstract int getPower();

    /**
     * Gets the health of a Troop. Implemented in the concrete class.
     * @return Returns the health value.
     */
    public abstract int getHealth();

    /**
     * Gets the damage of a Troop. Implemented in the concrete class.
     * @return Returns the damage value.
     */
    public abstract int getDamage();

    /**
     * Gets the movement speed of a Troop. Implemented in the concrete class.
     * @return Returns the movement speed value.
     */
    public abstract int getMovementSpeed();

    /**
     * Gets the attack speed of a Troop. Implemented in the concrete class.
     * @return Returns the attack speed value.
     */
    public abstract int getAttackSpeed();

    // A primitive setup for the weakness system, not sure if it will be implemented any time soon 2/9/2024
    // Still not sure 2/28/2024

    /**
     * Method for the weakness system.
     * This method compares two troopTypes and their weaknesses, and returns a value based on the comparison.
     * @param targetTroop The troop this troop is being compared to.
     * @return Returns a double representing the strength of this Troop against the targetTroop.
     */
    public double troopVTroopEffectiveness(Troop targetTroop) {
        TroopTypes targetWeakness = targetTroop.getWeakness();
        if (this.troopType == targetWeakness) {
            return 1.75;
        }
        else if (this.getWeakness() == targetTroop.getTroopType()) {
            return 0.5;
        }
        else {
            return 1.0;
        }
    }
}
