package troops;

import static troops.TroopTypes.*;

public class TroopCombatCalculator {
    final double ptsPerDamage = 2.5;
    final double ptsPerHealth = 2.0;
    final double ptsPerAttackSpeed = 3.0;
    final double ptsPerMovementSpeed = 3.0;
    private enum Result  {ATTACKER_WIN, DEFENDER_WIN, TIE}
    Result result = Result.TIE;
    public TroopCombatCalculator(TroopManager attacker, TroopManager defender) {
        battle(attacker, defender);
    }

    // Some kind of points based calculation implementation?
    public void battle(TroopManager attacker, TroopManager defender) {
        double attackerPts = 0;
        double defenderPts = 0;
        int type = 0;
        attackerPts = calculatePlayerPoints(attacker);
        defenderPts = calculatePlayerPoints(defender);
        double ptsDifference = Math.abs(attackerPts - defenderPts);
        if (ptsDifference <= 100 && attackerPts > defenderPts) {
            type = 0;
            result = Result.TIE;
        }
        else if (ptsDifference > 100 && ptsDifference < 900 && attackerPts > defenderPts) {
            type = 1;
            result = Result.ATTACKER_WIN;
        }
        else if (ptsDifference >= 900 && attackerPts > defenderPts) {
            type = 2;
            result = Result.ATTACKER_WIN;
        }
        else if (ptsDifference <= 100 && attackerPts < defenderPts) {
            type = 3;
            result = Result.TIE;
        }
        else if (ptsDifference > 100 && ptsDifference < 900 && attackerPts < defenderPts) {
            type = 4;
            result = Result.DEFENDER_WIN;
        }
        else if (ptsDifference >= 900 && attackerPts < defenderPts) {
             type = 5;
             result = Result.DEFENDER_WIN;
        }
        troopDeaths(attacker, defender, type);
    }
    public String getResult() {
        return result.toString();
    }

    public void troopDeaths(TroopManager attacker, TroopManager defender, int type) {
        switch (type) {
            case 0:
                attacker.removeTroop(ARCHER, (int)(attacker.getTroopNum(ARCHER) * 0.4));
                defender.removeTroop(ARCHER, (int)(defender.getTroopNum(ARCHER) * 0.5));
                attacker.removeTroop(WARRIOR, (int)(attacker.getTroopNum(WARRIOR) * 0.4));
                defender.removeTroop(WARRIOR, (int)(defender.getTroopNum(WARRIOR) * 0.5));
                attacker.removeTroop(MAGE, (int)(attacker.getTroopNum(MAGE) * 0.4));
                defender.removeTroop(MAGE, (int)(defender.getTroopNum(MAGE) * 0.5));
                attacker.removeTroop(CAVALRY, (int)(attacker.getTroopNum(CAVALRY) * 0.4));
                defender.removeTroop(CAVALRY, (int)(defender.getTroopNum(CAVALRY) * 0.5));
                break;
            case 1:
                attacker.removeTroop(ARCHER, (int)(attacker.getTroopNum(ARCHER) * 0.3));
                defender.removeTroop(ARCHER, (int)(defender.getTroopNum(ARCHER) * 0.7));
                attacker.removeTroop(WARRIOR, (int)(attacker.getTroopNum(WARRIOR) * 0.3));
                defender.removeTroop(WARRIOR, (int)(defender.getTroopNum(WARRIOR) * 0.7));
                attacker.removeTroop(MAGE, (int)(attacker.getTroopNum(MAGE) * 0.3));
                defender.removeTroop(MAGE, (int)(defender.getTroopNum(MAGE) * 0.7));
                attacker.removeTroop(CAVALRY, (int)(attacker.getTroopNum(CAVALRY) * 0.3));
                defender.removeTroop(CAVALRY, (int)(defender.getTroopNum(CAVALRY) * 0.7));
                break;
            case 2:
                attacker.removeTroop(ARCHER, (int)(attacker.getTroopNum(ARCHER) * 0.1));
                defender.removeTroop(ARCHER, (int)(defender.getTroopNum(ARCHER) * 0.8));
                attacker.removeTroop(WARRIOR, (int)(attacker.getTroopNum(WARRIOR) * 0.1));
                defender.removeTroop(WARRIOR, (int)(defender.getTroopNum(WARRIOR) * 0.8));
                attacker.removeTroop(MAGE, (int)(attacker.getTroopNum(MAGE) * 0.1));
                defender.removeTroop(MAGE, (int)(defender.getTroopNum(MAGE) * 0.8));
                attacker.removeTroop(CAVALRY, (int)(attacker.getTroopNum(CAVALRY) * 0.1));
                defender.removeTroop(CAVALRY, (int)(defender.getTroopNum(CAVALRY) * 0.8));
                break;
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
            case 5:
                attacker.removeTroop(ARCHER, (int)(attacker.getTroopNum(ARCHER) * 0.8));
                defender.removeTroop(ARCHER, (int)(defender.getTroopNum(ARCHER) * 0.1));
                attacker.removeTroop(WARRIOR, (int)(attacker.getTroopNum(WARRIOR) * 0.8));
                defender.removeTroop(WARRIOR, (int)(defender.getTroopNum(WARRIOR) * 0.1));
                attacker.removeTroop(MAGE, (int)(attacker.getTroopNum(MAGE) * 0.8));
                defender.removeTroop(MAGE, (int)(defender.getTroopNum(MAGE) * 0.1));
                attacker.removeTroop(CAVALRY, (int)(attacker.getTroopNum(CAVALRY) * 0.8));
                defender.removeTroop(CAVALRY, (int)(defender.getTroopNum(CAVALRY) * 0.1));
                break;
        }
    }

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
