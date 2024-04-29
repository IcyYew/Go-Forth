package troops;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * The class for the Cavalry troop.
 * This class contains the various statistics for the Cavalry troop, which other classes can use for fights and power calculations.
 * @author Michael Geltz
 */
@Entity
@DiscriminatorValue("CAVALRY")
public class Cavalry extends Troop {
    // Cavalry class is weak to archer troop, basic stats defined, subject to change
    private final TroopTypes weakness = TroopTypes.ARCHER;
    private final int power = 2;
    private final int health = 10;
    private double damage = 3;
    private final int movementSpeed = 5;
    private final int attackSpeed = 3;

    /**
     * Constructor for the Cavalry class extending the Troop class.
     * This constructor creates a Cavalry instance using troopManager and quantity parameters.
     *
     * @param troopManager
     * @param quantity     Number of troops created.
     */
    public Cavalry(TroopManager troopManager, int quantity) {
        super(TroopTypes.CAVALRY, quantity, troopManager);
        setPower(power);
        setDamage(damage);
        setHealth(health);
        setMovementSpeed(movementSpeed);
        setAttackSpeed(attackSpeed);
    }

    /**
     * Empty constructor for the Cavalry class.
     * This constructor creates a Cavalry with default attributes.
     */
    public Cavalry() {

    }

    /**
     * Gets the troop against which the Cavalry is weak.
     *
     * @return Returns a TroopTypes representing the troop.
     */
    @Override
    public TroopTypes getWeakness() {
        return this.weakness;
    }

    /**
     * Gets the power of the Cavalry.
     *
     * @return Returns the Cavalry's power value.
     */
    @Override
    public int getPower() {
        return this.power;
    }

    /**
     * Gets the health of the Cavalry.
     *
     * @return Returns the Cavalry's health value.
     */
    @Override
    public int getHealth() {
        return this.health;
    }

    /**
     * Gets the damage of the Cavalry.
     *
     * @return Returns the Cavalry's damage value.
     */
    @Override
    public double getDamage() {
        return this.damage;
    }

    /**
     * Gets the movement speed of the Cavalry.
     *
     * @return Returns the Cavalry's movement speed value.
     */
    @Override
    public int getMovementSpeed() {
        return this.movementSpeed;
    }

    /**
     * Gets the attack speed of the Cavalry.
     *
     * @return Returns the Cavalry's attack speed value.
     */
    @Override
    public int getAttackSpeed() {
        return this.attackSpeed;
    }
}
