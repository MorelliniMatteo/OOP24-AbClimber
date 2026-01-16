package it.unibo.abyssclimber.model;

public enum Classe {
    CAVALIERE("Knight", 250, 25, 1, 30, 20, 20, 1.5),
    MAGO("Mage", 250, 1, 25, 20, 30, 20, 1.5),
    SOLDATO("Soldier", 300, 20, 20, 25, 25, 25, 2.0);

    private final String name;
    private final int cMaxHP;
    private final int cATK;
    private final int cMATK;
    private final int cDEF;
    private final int cMDEF;
    private final int cCrit;
    private final double cCritDMG;

    Classe(String name, int maxhp, int atk, int matk, int def, int mdef, int crit, double critdmg) {
        this.name = name;
        this.cMaxHP = maxhp;
        this.cATK = atk;
        this.cMATK = matk;
        this.cDEF = def;
        this.cMDEF = mdef;
        this.cCrit = crit;
        this.cCritDMG = critdmg;
    }

    public String getName() {
        return name;
    }

    public int getcMaxHP() {
        return cMaxHP;
    }

    public int getcATK() {
        return cATK;
    }

    public int getcMATK() {
        return cMATK;
    }

    public int getcDEF() {
        return cDEF;
    }

    public int getcMDEF() {
        return cMDEF;
    }

    public int getcCrit() {
        return cCrit;
    }

    public double getcCritDMG() {
        return cCritDMG;
    }
}
