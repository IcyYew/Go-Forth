package troops;


import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Troop {


    // Unqiue ID for each resource entity, not sure if best way of doing this because each player currently has four troop types
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer troopID;


    // Type of troop
    @Enumerated(EnumType.STRING)
    private TroopTypes troopType;

    // power of a troop, think this is useless? not going to delete to be safe
    private int power;

    // Quantity of a troop
    private int quantity;

    // Joins all resources into a single column
    @ManyToOne
    @JoinColumn(name = "troop_manager_id")
    private TroopManager troopManager;



    // Useless stuffs below, not removing for now
    // !!!! implement food consumption
    private int damage;
    private int health;
    private int movementSpeed;
    private int attackSpeed;
    private TroopTypes weakness;



    // Creates troop, with trooptype, quantity, and a manager
    public Troop(TroopTypes troopType, int quantity, TroopManager troopManager) {
        this.troopType = troopType;
        this.quantity = quantity;
        this.troopManager = troopManager;
    }

    // Empty constructor to make Jpa happy
    public Troop() {

    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    // A primitive setup for the weakness system, not sure if it will be implemented any time soon 2/9/2024
    // Still not sure 2/28/2024
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
