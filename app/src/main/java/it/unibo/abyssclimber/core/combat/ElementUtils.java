package it.unibo.abyssclimber.core.combat;

import it.unibo.abyssclimber.model.Creature;
import it.unibo.abyssclimber.model.Tipo;

//Class that manages elemental weaknesses and the appropriate log types.
public final class ElementUtils {

    //Computes if the move is super-effective or not very effective.
    private static double computeEffect (Tipo attacker, Tipo target){
         switch (attacker) {
            case HYDRO:
                if (target == Tipo.FIRE) {return 1.5;}
                else if (target == Tipo.LIGHTNING) {return 0.75;}
                else if (target == Tipo.VOID) {return 0.5;}
                else return 1.0;
        
            case FIRE:
                if (target == Tipo.NATURE) {return 1.5;}
                else if (target == Tipo.HYDRO) {return 0.75;}
                else if (target == Tipo.VOID) {return 0.5;}
                else return 1.0;

            case NATURE:
                if (target == Tipo.LIGHTNING) {return 1.5;}
                else if (target == Tipo.FIRE) {return 0.75;}
                else if (target == Tipo.VOID) {return 0.5;}
                else return 1.0;

            case LIGHTNING:
                if (target == Tipo.HYDRO) {return 1.5;}
                else if (target == Tipo.NATURE) {return 0.75;}
                else if (target == Tipo.VOID) {return 0.5;}
                else return 1.0;

            case Tipo.VOID:
                return 1.5;
                      
            default:
                return 1.0;
        }  
    }

    //Calls computeEffect, the main method to be used.
    public static double getEffect (MoveLoader.Move attacker, Creature target){
        return computeEffect(attacker.getElement(), target.getElement());
    }

    //Calls computeEffect on 2 creature types. Not currently used, kept intentionally for possible future uses.
    public static double getEffect (Creature attacker, Creature target){
        return computeEffect(attacker.getElement(), target.getElement());
    }

    //Determines the log to be printed.
    public static void weakPhrase(double weak, CombatLog log){
        if(weak < 1){
            log.logCombat("It's not very effective.\n", LogType.NORMAL);
        } else if (weak > 1) {
            log.logCombat("It's super effective.\n", LogType.NORMAL);
        } else {

        }

    }
}
