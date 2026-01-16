package it.unibo.abyssclimber.core.combat;

//Strike and swirl. The 8 (2 types per element) 1 cost moves.
    //They are incomplete and will be filled in the baseMoveAssign method.
public record BaseMove(
    String name,
    int power,
    int acc,
    int type,
    int cost) {}
