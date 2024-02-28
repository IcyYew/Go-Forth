package troops;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CAVALRY")
public class Cavalry extends Troop{
    // Cavalry class is weak to archer troop, basic stats defined, subject to change
    private final TroopTypes weakness = TroopTypes.ARCHER;
    private final int power = 2;
    private final int health = 10;
    private final int damage = 3;
    private final int movementSpeed = 5;
    private final int attackSpeed = 3;

    public Cavalry(TroopManager troopManager, int quantity) {
        super(TroopTypes.CAVALRY, quantity, troopManager);
        setPower(power);
        setDamage(damage);
        setHealth(health);
        setMovementSpeed(movementSpeed);
        setAttackSpeed(attackSpeed);
    }

    public Cavalry() {

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
