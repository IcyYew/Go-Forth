package troops;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * The class for the Warrior troop extending the Troop class.
 * This class contains the various statistics for the Warrior troop, which other classes can use for fights and power calculations.
 * @author Michael Geltz
 */
@Entity
@DiscriminatorValue("WARRIOR")
public class Warrior extends Troop{
    // Warrior troop is weak to mage troop, basic stats defined, subject to change
    private final TroopTypes weakness = TroopTypes.MAGE;
    private final int power = 7;
    private final int health = 12;
    private final int damage = 2;
    private final int movementSpeed = 3;
    private final int attackSpeed = 3;

    /**
     * Constructor for the Warrior class.
     * This constructor creates a Warrior instance using troopManager and quantity parameters.
     * @param troopManager
     * @param quantity Number of troops created.
     */
    public Warrior(TroopManager troopManager, int quantity) {
        super(TroopTypes.WARRIOR, quantity, troopManager);
        setPower(power);
        setDamage(damage);
        setHealth(health);
        setMovementSpeed(movementSpeed);
        setAttackSpeed(attackSpeed);
    }

    /**
     * Empty constructor for the Warrior class.
     * This constructor creates a Warrior with default attributes.
     */
    public Warrior() {

    }

    /**
     * Gets the troop against which the Warrior is weak.
     * @return Returns a TroopTypes representing the troop.
     */
    @Override
    public TroopTypes getWeakness() {
        return this.weakness;
    }

    /**
     * Gets the power of the Warrior.
     * @return Returns the Warrior's power value.
     */
    @Override
    public int getPower() {
        return this.power;
    }

    /**
     * Gets the health of the Warrior.
     * @return Returns the Warrior's health value.
     */
    @Override
    public int getHealth() {
        return this.health;
    }

    /**
     * Gets the damage of the Warrior.
     * @return Returns the Warrior's damage value.
     */
    @Override
    public int getDamage() {
        return this.damage;
    }

    /**
     * Gets the movement speed of the Warrior.
     * @return Returns the Warrior's movement speed value.
     */
    @Override
    public int getMovementSpeed() {
        return this.movementSpeed;
    }

    /**
     * Gets the attack speed of the Warrior.
     * @return Returns the Warrior's attack speed value.
     */
    @Override
    public int getAttackSpeed() {
        return this.attackSpeed;
    }

}
