package troops;

public class Archer extends Troop{
    private final TroopTypes weakness = TroopTypes.CAVALRY;
    private final int power = 4;
    private final int health = 10;
    private final int damage = 3;
    private final int movementSpeed = 3;
    private final int attackSpeed = 3;

    public Archer() {
        super(TroopTypes.ARCHER);
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
