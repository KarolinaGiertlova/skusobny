package Model;

import java.io.Serializable;

public class PocetLiekov implements Serializable { // Serializable - da sa posielat cez RMI
    static final long serialVersionUID = 42813568L; // Serializable musi obsahovat tuto premennu, cislo moze byt nahodne
    private String nazovLieku;
    private int pocetLieku;

    public String getNazovLieku() {
        return nazovLieku;
    }

    public void setNazovLieku(String nazovLieku) {
        this.nazovLieku = nazovLieku;
    }

    public int getPocetLieku() {
        return pocetLieku;
    }

    public void setPocetLieku(int pocetLieku) {
        this.pocetLieku = pocetLieku;
    }

    public PocetLiekov(String nazovLieku, int pocetLieku) {
        this.nazovLieku = nazovLieku;
        this.pocetLieku = pocetLieku;
    }
}
