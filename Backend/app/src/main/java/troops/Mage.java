package troops;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * The class for the Mage troop extending the Troop class.
 * This class contains the various statistics for the Mage troop, which other classes can use for fights and power calculations.
 * @author Michael Geltz
 */
@Entity
@DiscriminatorValue("MAGE")
public class Mage extends Troop {
    // Mage troop is weak to warrior troop, basic stats defined, subject to change
    private final TroopTypes weakness = TroopTypes.WARRIOR;
    private final int power = 5;
    private final int health = 8;
    private final int damage = 4;
    private final int movementSpeed = 3;
    private final int attackSpeed = 3;

    /**
     * Constructor for the Mage class.
     * This constructor creates a Mage instance using troopManager and quantity parameters.
     *
     * @param troopManager
     * @param quantity     Number of troops created.
     */
    public Mage(TroopManager troopManager, int quantity) {
        super(TroopTypes.MAGE, quantity, troopManager);
        setPower(power);
        setDamage(damage);
        setHealth(health);
        setMovementSpeed(movementSpeed);
        setAttackSpeed(attackSpeed);
    }

    /**
     * Empty constructor for the Mage class.
     * This constructor creates a Mage with default attributes.
     */
    public Mage() {

    }

    /**
     * Gets the troop against which the Mage is weak.
     *
     * @return Returns a TroopTypes representing the troop.
     */
    @Override
    public TroopTypes getWeakness() {
        return this.weakness;
    }

    /**
     * Gets the power of the Mage.
     *
     * @return Returns the Mage's power value.
     */
    @Override
    public int getPower() {
        return this.power;
    }

    /**
     * Gets the health of the Mage.
     *
     * @return Returns the Mage's health value.
     */
    @Override
    public int getHealth() {
        return this.health;
    }

    /**
     * Gets the damage of the Mage.
     *
     * @return Returns the Mage's damage value.
     */
    @Override
    public int getDamage() {
        return this.damage;
    }

    /**
     * Gets the movement speed of the Mage.
     *
     * @return Returns the Mage's movement speed value.
     */
    @Override
    public int getMovementSpeed() {
        return this.movementSpeed;
    }

    /**
     * Gets the attack speed of the Mage.
     *
     * @return Returns the Mage's attack speed value.
     */
    @Override
    public int getAttackSpeed() {
        return this.attackSpeed;
    }
}
