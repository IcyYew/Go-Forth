package troops;

public class Mage extends Troop{
    // Mage troop is weak to warrior troop, basic stats defined, subject to change
    private final TroopTypes weakness = TroopTypes.WARRIOR;
    private final int power = 5;
    private final int health = 8;
    private final int damage = 4;
    private final int movementSpeed = 3;
    private final int attackSpeed = 3;

    public Mage() {
        super(TroopTypes.MAGE);
        setPower(power);
        setDamage(damage);
        setHealth(health);
        setMovementSpeed(movementSpeed);
        setAttackSpeed(attackSpeed);
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
