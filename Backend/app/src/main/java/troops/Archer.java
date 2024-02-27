package troops;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

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

    public Archer(TroopManager troopManager, int quantity) {
        super(TroopTypes.ARCHER, quantity, troopManager);
        setPower(power);
        setDamage(damage);
        setHealth(health);
        setMovementSpeed(movementSpeed);
        setAttackSpeed(attackSpeed);
    }

    public Archer() {

    }

    @Override
    public TroopTypes getWeakness() {
        return this.weakness;
    }

    @Override
    public int getPower() {
        return this.power;
    }

    @Override
    public int getHealth() {
        return this.health;
    }

    @Override
    public int getDamage() {
        return this.damage;
    }

    @Override
    public int getMovementSpeed() {
        return this.movementSpeed;
    }

    @Override
    public int getAttackSpeed() {
        return this.attackSpeed;
    }


}
