package troops;

public abstract class Troop {
    private TroopTypes troopType;
    private int power;



    // !!!! implement food consumption
    private int damage;
    private int health;
    private int movementSpeed;
    private int attackSpeed;
    private TroopTypes weakness;


    public Troop(TroopTypes troopType) {
        this.troopType = troopType;
    }

    public TroopTypes getTroopType() {
        return troopType;
    }

    public void setTroopType(TroopTypes troopType) {
        this.troopType = troopType;
    }

    public void setPower(int power) {
        this.power = power;
    }


    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setHealth(int health) {
        this.health = health;
    }


    public void setMovementSpeed(int movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public void setAttackSpeed(int attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public abstract TroopTypes getWeakness();
    public abstract int getPower();
    public abstract int getHealth();
    public abstract int getDamage();
    public abstract int getMovementSpeed();
    public abstract int getAttackSpeed();

    public double troopVTroopEffectiveness(Troop targetTroop) {
        TroopTypes targetWeakness = targetTroop.getWeakness();
        if (this.troopType == targetWeakness) {
            return 1.75;
        }
        else if (this.getWeakness() == targetTroop.getTroopType()) {
            return 0.5;
        }
        else {
            return 1.0;
        }
    }
}
