package Model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Liek implements Serializable {
    static final long serialVersionUID = 4265452L;

    private int id;
    private String nazov;
    private double cena;

    public int getPocet() {
        return pocet;
    }

    public void setPocet(int pocet) {
        this.pocet = pocet;
    }

    private double hmotnost;
    private Date datumExspiracie;
    private int pocet;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazov() {
        return this.nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public double getHmotnost() {
        return hmotnost;
    }

    public void setHmotnost(double hmotnost) {
        this.hmotnost = hmotnost;
    }

    public Date getDatumExspiracie() {
        return datumExspiracie;
    }

    public void setDatumExspiracie(Date datumExspiracie) {
        this.datumExspiracie = datumExspiracie;
    }

    public Liek(int id, String nazov, double cena, double hmotnost, Date datumExspiracie) {
        this.id = id;
        this.nazov = nazov;
        this.cena = cena;
        this.hmotnost = hmotnost;
        this.datumExspiracie = datumExspiracie;
    }

    public Liek(ResultSet rs) throws SQLException {
        this(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getDouble(4), rs.getDate(5));
    }

}
