package main;


import troops.Archer;
import troops.Cavalry;
import troops.TroopCombatCalculator;
import troops.TroopManager;

import static troops.TroopTypes.*;

public class Main {

    public static void main(String[] args) {
        TroopManager attacker = new TroopManager();
        attacker.addTroop(ARCHER, 100);
        attacker.addTroop(CAVALRY, 100);
        attacker.addTroop(MAGE, 100);
        attacker.addTroop(WARRIOR, 100);
        System.out.println("Pre-battle: ");
        System.out.println("Attacker: ");
        System.out.println(attacker.getTroopNum(ARCHER));
        System.out.println(attacker.getTroopNum(CAVALRY));
        System.out.println(attacker.getTroopNum(MAGE));
        System.out.println(attacker.getTroopNum(WARRIOR));
        System.out.println(attacker.calculateTotalTroopPower());

        TroopManager defender = new TroopManager();
        defender.addTroop(ARCHER, 100);
        defender.addTroop(CAVALRY, 100);
        defender.addTroop(MAGE, 100);
        defender.addTroop(WARRIOR, 0);
        System.out.println("Defender: ");
        System.out.println(defender.getTroopNum(ARCHER));
        System.out.println(defender.getTroopNum(CAVALRY));
        System.out.println(defender.getTroopNum(MAGE));
        System.out.println(defender.getTroopNum(WARRIOR));
        System.out.println(defender.calculateTotalTroopPower());


        TroopCombatCalculator battleSim = new TroopCombatCalculator(attacker, defender);

        System.out.println("\nBattle Result: ");
        System.out.println(battleSim.getResult());
        System.out.println("After Battle attacker: ");
        System.out.println(attacker.getTroopNum(ARCHER));
        System.out.println(attacker.getTroopNum(CAVALRY));
        System.out.println(attacker.getTroopNum(MAGE));
        System.out.println(attacker.getTroopNum(WARRIOR));
        System.out.println(attacker.calculateTotalTroopPower());

        System.out.println("After battle defender: ");
        System.out.println(defender.getTroopNum(ARCHER));
        System.out.println(defender.getTroopNum(CAVALRY));
        System.out.println(defender.getTroopNum(MAGE));
        System.out.println(defender.getTroopNum(WARRIOR));
        System.out.println(defender.calculateTotalTroopPower());





    }
}
