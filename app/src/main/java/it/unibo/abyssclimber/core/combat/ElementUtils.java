package it.unibo.abyssclimber.core.combat;

import it.unibo.abyssclimber.core.combat.MoveLoader.Move;
import it.unibo.abyssclimber.model.Creature;
import it.unibo.abyssclimber.model.Tipo;

public final class ElementUtils {

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

    public static double getEffect (MoveLoader.Move attacker, Creature target){
        return computeEffect(attacker.getElement(), target.getElement());
    }

    public static double getEffect (Creature attacker, Creature target){
        return computeEffect(attacker.getElement(), target.getElement());
    }

    public static void weakPhrase(double weak){
        if(weak < 1){
            System.out.println("It's not very effective.\n");
        } else if (weak > 1) {
            System.out.println("It's super effective!\n");
        } else {

        }

    }
}
