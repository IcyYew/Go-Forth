package troops;

import static troops.TroopTypes.*;

/**
 * The class for Troop Combat Calculation.
 * This class calculates the outcomes of battles in the game.
 * The calculation uses a points system that takes into account the attributes of each type of troop in combat.
 * There are several different potential outcomes for a fight based on the point calculation.
 * @author Michael Geltz
 */
public class TroopCombatCalculator {
    // Player v Player combat is based on a points system calculated with the below values
    final double ptsPerDamage = 2.5;
    final double ptsPerHealth = 2.0;
    final double ptsPerAttackSpeed = 3.0;
    final double ptsPerMovementSpeed = 3.0;
    // Only three results defined here but a tie can result in one favoring the attacker or defender
    private enum Result  {ATTACKER_WIN, DEFENDER_WIN, TIE}
    private int killsp1 = 0;
    private int killsp2 = 0;

    Result result = Result.TIE;

    // Takes in two different players troops, directly calls the battle method, a better way of doing this
    // to be implemented eventually

    /**
     * Constructor for the Troop Combat Calculator.
     * Calls the battle method with the attack and defender parameters.
     * @param attacker TroopManager for the attacking player.
     * @param defender TroopManager for the defending player.
     */
    public TroopCombatCalculator(TroopManager attacker, TroopManager defender) {
        battle(attacker, defender);
    }

    // Battle class to calculate who won a battle, who is attacker and defender is entirely arbitrary

    /**
     * Method which determines the outcome of a battle.
     * First, the point total for each player's army is calculated by calling the calculatePlayerPoints method for each.
     * Next, the point totals are compared and the percentage difference is calculated.
     * Then, the percentage difference is used to determine which of the 5 battle outcomes occurs.
     * Finally, calls the troopDeaths method to determine the losses for each player.
     * @param attacker TroopManager for the attacking player.
     * @param defender TroopManager for the defending player.
     */
    public void battle(TroopManager attacker, TroopManager defender) {
        double attackerPts = 0;
        double defenderPts = 0;
        double ptsDiffPercent = 0;
        // Battle system gives our 5 different "types" of battle result depending on how the points-based calculation fairs
        int type = 0;

        attackerPts = calculatePlayerPoints(attacker);
        defenderPts = calculatePlayerPoints(defender);

        // Take absolute difference of two point values to figure out how advantaged the battle was
        double ptsDifference = Math.abs(attackerPts - defenderPts);
        if(attackerPts > defenderPts) {
            ptsDiffPercent = defenderPts / attackerPts;
        }
        else {
            ptsDiffPercent = attackerPts / defenderPts;
        }
        // The points differences defined in the logic below are not very good, need to do substantial testing or

        // a different method of determining battle types, this is mostly for testing
        // Tie with slight attacker advantage
        if (ptsDiffPercent >= .85 && attackerPts >= defenderPts) {
            type = 0;
            result = Result.TIE;
        }
        // Attacker win
        else if (ptsDiffPercent < .85 && ptsDiffPercent > .35 && attackerPts >= defenderPts) {
            type = 1;
            result = Result.ATTACKER_WIN;
        }
        // Overwhelming attacker victory
        else if (ptsDiffPercent <= .35 && attackerPts >= defenderPts) {
            type = 2;
            result = Result.ATTACKER_WIN;
        }
        // Tie with slight defender advantage
        else if (ptsDiffPercent >= .85 && attackerPts <= defenderPts) {
            type = 3;
            result = Result.TIE;
        }
        // Defender win
        else if (ptsDiffPercent < .85 && ptsDiffPercent > .35 && attackerPts <= defenderPts) {
            type = 4;
            result = Result.DEFENDER_WIN;
        }
        // Overwhelming defender win
        else if (ptsDiffPercent <= .35 && attackerPts <= defenderPts) {
             type = 5;
             result = Result.DEFENDER_WIN;
        }
        // Update player troops based on battle result
        troopDeaths(attacker, defender, type);
    }
    // Returns result enum in String format

    /**
     * Method to return the result of a battle.
     * @return Returns a String result.
     */
    public String getResult() {
        return result.toString();
    }

    // Update troops for both attacker and defender based on battle result or "type"
    // Pretty messy system of doing this, could be made to look nicer in a couple of separate methods but this
    // works and I don't see it as prudent to change for now

    /**
     * Method for updating the troop quantities for each player based on the outcome determined by the battle method.
     * A better outcome for the attacker and therefore a worse outcome for the defender will result in few losses for the attacker and more losses for the defender, and vice versa.
     * @param attacker TroopManager for the attacking player.
     * @param defender TroopManager for the defending player.
     * @param type The outcome determined by the battle Method.
     */
    public void troopDeaths(TroopManager attacker, TroopManager defender, int type) {
        switch (type) {
            // Tie with attacker advantage, attacker loses 40% of troops they had prior to battle, defender loses 50%
            case 0:
                killsp2 += (int) (attacker.getTroopNum(ARCHER) * 0.4);
                attacker.removeTroop(ARCHER, (int)(attacker.getTroopNum(ARCHER) * 0.4));
                killsp1 += (int) (defender.getTroopNum(ARCHER) * 0.5);
                defender.removeTroop(ARCHER, (int)(defender.getTroopNum(ARCHER) * 0.5));
                killsp2 += (int) (attacker.getTroopNum(WARRIOR) * 0.4);
                attacker.removeTroop(WARRIOR, (int)(attacker.getTroopNum(WARRIOR) * 0.4));
                killsp1 += (int) (defender.getTroopNum(WARRIOR) * 0.5);
                defender.removeTroop(WARRIOR, (int)(defender.getTroopNum(WARRIOR) * 0.5));
                killsp2 += (int) (attacker.getTroopNum(MAGE) * 0.4);
                attacker.removeTroop(MAGE, (int)(attacker.getTroopNum(MAGE) * 0.4));
                killsp1 += (int) (defender.getTroopNum(MAGE) * 0.5);
                defender.removeTroop(MAGE, (int)(defender.getTroopNum(MAGE) * 0.5));
                killsp2 += (int) (attacker.getTroopNum(CAVALRY) * 0.4);
                attacker.removeTroop(CAVALRY, (int)(attacker.getTroopNum(CAVALRY) * 0.4));
                killsp1 += (int) (defender.getTroopNum(CAVALRY) * 0.5);
                defender.removeTroop(CAVALRY, (int)(defender.getTroopNum(CAVALRY) * 0.5));
                break;
            // Attacker win, attacker loses 30% of troops they had prior to battle, defender loses 70%
            case 1:
                killsp2 += (int) (attacker.getTroopNum(ARCHER) * 0.3);
                attacker.removeTroop(ARCHER, (int)(attacker.getTroopNum(ARCHER) * 0.3));
                killsp1 += (int) (defender.getTroopNum(ARCHER) * 0.7);
                defender.removeTroop(ARCHER, (int)(defender.getTroopNum(ARCHER) * 0.7));
                killsp2 += (int) (attacker.getTroopNum(ARCHER) * 0.3);
                attacker.removeTroop(WARRIOR, (int)(attacker.getTroopNum(WARRIOR) * 0.3));
                killsp1 += (int) (defender.getTroopNum(ARCHER) * 0.7);
                defender.removeTroop(WARRIOR, (int)(defender.getTroopNum(WARRIOR) * 0.7));
                killsp2 += (int) (attacker.getTroopNum(ARCHER) * 0.3);
                attacker.removeTroop(MAGE, (int)(attacker.getTroopNum(MAGE) * 0.3));
                killsp1 += (int) (defender.getTroopNum(ARCHER) * 0.7);
                defender.removeTroop(MAGE, (int)(defender.getTroopNum(MAGE) * 0.7));
                killsp2 += (int) (attacker.getTroopNum(ARCHER) * 0.3);
                attacker.removeTroop(CAVALRY, (int)(attacker.getTroopNum(CAVALRY) * 0.3));
                killsp1 += (int) (defender.getTroopNum(ARCHER) * 0.7);
                defender.removeTroop(CAVALRY, (int)(defender.getTroopNum(CAVALRY) * 0.7));
                break;
            // Overwhelming attacker win, attacker loses 10% of troops they had prior to battle, defender loses 80%
            case 2:
                attacker.removeTroop(ARCHER, (int)(attacker.getTroopNum(ARCHER) * 0.01));
                defender.removeTroop(ARCHER, defender.getTroopNum(ARCHER));
                attacker.removeTroop(WARRIOR, (int)(attacker.getTroopNum(WARRIOR) * 0.01));
                defender.removeTroop(WARRIOR, defender.getTroopNum(WARRIOR));
                attacker.removeTroop(MAGE, (int)(attacker.getTroopNum(MAGE) * 0.01));
                defender.removeTroop(MAGE, defender.getTroopNum(MAGE));
                attacker.removeTroop(CAVALRY, (int)(attacker.getTroopNum(CAVALRY) * 0.01));
                defender.removeTroop(CAVALRY, defender.getTroopNum(CAVALRY));
                break;
            // Tie with defender advantage, defender loses 40% of troops they had prior to battle, attacker loses 50%
            case 3:
                attacker.removeTroop(ARCHER, (int)(attacker.getTroopNum(ARCHER) * 0.5));
                defender.removeTroop(ARCHER, (int)(defender.getTroopNum(ARCHER) * 0.4));
                attacker.removeTroop(WARRIOR, (int)(attacker.getTroopNum(WARRIOR) * 0.5));
                defender.removeTroop(WARRIOR, (int)(defender.getTroopNum(WARRIOR) * 0.4));
                attacker.removeTroop(MAGE, (int)(attacker.getTroopNum(MAGE) * 0.5));
                defender.removeTroop(MAGE, (int)(defender.getTroopNum(MAGE) * 0.4));
                attacker.removeTroop(CAVALRY, (int)(attacker.getTroopNum(CAVALRY) * 0.5));
                defender.removeTroop(CAVALRY, (int)(defender.getTroopNum(CAVALRY) * 0.4));
                break;
            // Defender win, defender loses 30% of troops they had prior to battle, attacker loses 70%
            case 4:
                attacker.removeTroop(ARCHER, (int)(attacker.getTroopNum(ARCHER) * 0.7));
                defender.removeTroop(ARCHER, (int)(defender.getTroopNum(ARCHER) * 0.3));
                attacker.removeTroop(WARRIOR, (int)(attacker.getTroopNum(WARRIOR) * 0.7));
                defender.removeTroop(WARRIOR, (int)(defender.getTroopNum(WARRIOR) * 0.3));
                attacker.removeTroop(MAGE, (int)(attacker.getTroopNum(MAGE) * 0.7));
                defender.removeTroop(MAGE, (int)(defender.getTroopNum(MAGE) * 0.3));
                attacker.removeTroop(CAVALRY, (int)(attacker.getTroopNum(CAVALRY) * 0.7));
                defender.removeTroop(CAVALRY, (int)(defender.getTroopNum(CAVALRY) * 0.3));
                break;
            // Overwhelming defender win, defender loses 10% of troops they had prior to battle, attacker loses 80%
            case 5:
                attacker.removeTroop(ARCHER, attacker.getTroopNum(ARCHER));
                defender.removeTroop(ARCHER, (int)(defender.getTroopNum(ARCHER) * 0.01));
                attacker.removeTroop(WARRIOR, attacker.getTroopNum(WARRIOR));
                defender.removeTroop(WARRIOR, (int)(defender.getTroopNum(WARRIOR) * 0.01));
                attacker.removeTroop(MAGE, attacker.getTroopNum(MAGE));
                defender.removeTroop(MAGE, (int)(defender.getTroopNum(MAGE) * 0.01));
                attacker.removeTroop(CAVALRY, attacker.getTroopNum(CAVALRY));
                defender.removeTroop(CAVALRY, (int)(defender.getTroopNum(CAVALRY) * 0.01));
                break;
        }
    }

    // Basic points based calculation to derive battle result

    /**
     * This method calculates the player's points total to be used in combat.
     * The total strength of a player's army is calculated using the number of each troop type in the army and the attributes of each troop type.
     * @param player TroopManager for the current player's troops.
     * @return Returns a double representing the player's army's strength.
     */
    public double calculatePlayerPoints(TroopManager player) {
        double playerPts = 0;
        Archer archer = new Archer();
        Warrior warrior = new Warrior();
        Mage mage = new Mage();
        Cavalry cavalry = new Cavalry();

        int archers = player.getTroopNum(ARCHER);
        int warriors = player.getTroopNum(WARRIOR);
        int mages = player.getTroopNum(MAGE);
        int cavalryNum = player.getTroopNum(CAVALRY);

        // Player points updated by the muliplication of the following three parameters for all troop types:
        // 1) amount of (insert troop type) possessed by the player
        // 2) (insert troop types)'s stat
        // 3) points awarded for that stat
        playerPts += (archers * archer.getAttackSpeed() * ptsPerAttackSpeed) + (archers * archer.getHealth() * ptsPerHealth) + (archers * archer.getDamage() * ptsPerDamage) +
                (archers * archer.getMovementSpeed() * ptsPerMovementSpeed);
        playerPts += (warriors * warrior.getAttackSpeed() * ptsPerAttackSpeed) + (warriors * warrior.getHealth() * ptsPerHealth) + (warriors * warrior.getDamage() * ptsPerDamage) +
                (warriors * warrior.getMovementSpeed() * ptsPerMovementSpeed);
        playerPts += (mages * mage.getAttackSpeed() * ptsPerAttackSpeed) + (mages * mage.getHealth() * ptsPerHealth) + (mages * mage.getDamage() * ptsPerDamage) +
                (mages * mage.getMovementSpeed() * ptsPerMovementSpeed);
        playerPts += (cavalryNum * cavalry.getAttackSpeed() * ptsPerAttackSpeed) + (cavalryNum * cavalry.getHealth() * ptsPerHealth) + (cavalryNum * cavalry.getDamage() * ptsPerDamage) +
                (cavalryNum * cavalry.getMovementSpeed() * ptsPerMovementSpeed);

        return playerPts;

    }


    //seems a bit too complex atm, maybe implement later
    public double calculateWeaknessVariable(TroopManager attacker, TroopManager defender) {
        int archers = attacker.getTroopNum(ARCHER);
        int warriors = attacker.getTroopNum(WARRIOR);
        int mages = attacker.getTroopNum(MAGE);
        int cavalryNum = attacker.getTroopNum(CAVALRY);

        return 0;
    }
}
