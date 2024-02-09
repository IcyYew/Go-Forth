package troops;

public class Cavalry extends Troop{
    private final TroopTypes weakness = TroopTypes.ARCHER;
    private final int power = 2;
    private final int health = 10;
    private final int damage = 3;
    private final int movementSpeed = 5;
    private final int attackSpeed = 3;

    public Cavalry() {
        super(TroopTypes.CAVALRY);
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
