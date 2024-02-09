package troops;

public class Warrior extends Troop{
    // Warrior troop is weak to mage troop, basic stats defined, subject to change
    private final TroopTypes weakness = TroopTypes.MAGE;
    private final int power = 7;
    private final int health = 12;
    private final int damage = 2;
    private final int movementSpeed = 3;
    private final int attackSpeed = 3;

    public Warrior() {
        super(TroopTypes.WARRIOR);
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
