package troops;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * The class for the Archer troop extending the Troop class.
 * This class contains the various statistics for the Archer troop, which other classes can use for fights and power calculations.
 * @author Michael Geltz
 */
@Entity
@DiscriminatorValue("ARCHER")
public class Archer extends Troop{
    // Archer class is weak to cavalry troop, basic stats defined, subject to change
    private final TroopTypes weakness = TroopTypes.CAVALRY;
    private final int power = 4;
    private final int health = 10;
    private final int damage = 3;
    private final int movementSpeed = 3;
    private final int attackSpeed = 3;

    /**
     * Constructor for the Archer class.
     * This constructor creates an Archer instance using troopManager and quantity parameters.
     * @param troopManager
     * @param quantity Number of troops created.
     */
    public Archer(TroopManager troopManager, int quantity) {
        super(TroopTypes.ARCHER, quantity, troopManager);
        setPower(power);
        setDamage(damage);
        setHealth(health);
        setMovementSpeed(movementSpeed);
        setAttackSpeed(attackSpeed);
    }

    /**
     * Empty constructor for the Archer class.
     * This constructor creates an archer with default attributes.
     */
    public Archer() {

    }

    /**
     * Gets the troop against which the Archer is weak.
     * @return Returns a TroopTypes representing the troop.
     */
    @Override
    public TroopTypes getWeakness() {
        return this.weakness;
    }

    /**
     * Gets the power of the Archer.
     * @return Returns the Archer's power value.
     */
    @Override
    public int getPower() {
        return this.power;
    }

    /**
     * Gets the health of the Archer.
     * @return Returns the Archer's health value.
     */
    @Override
    public int getHealth() {
        return this.health;
    }

    /**
     * Gets the damage of the Archer.
     * @return Returns the Archer's damage value.
     */
    @Override
    public int getDamage() {
        return this.damage;
    }

    /**
     * Gets the movement speed of the Archer.
     * @return Returns the Archer's movement speed value.
     */
    @Override
    public int getMovementSpeed() {
        return this.movementSpeed;
    }

    /**
     * Gets the attack speed of the Archer.
     * @return Returns the Archer's attack speed value.
     */
    @Override
    public int getAttackSpeed() {
        return this.attackSpeed;
    }
}
