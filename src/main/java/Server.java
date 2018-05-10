import Model.Liek;
import Model.PocetLiekov;
import Model.Pripojenie;
import common.ServerInterface;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Server extends UnicastRemoteObject implements ServerInterface {
    private static final Logger log = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) throws RemoteException {
        PropertyConfigurator.configure(Server.class.getResource("log4j.properties"));
        Registry registry = LocateRegistry.createRegistry(1099); // registry - zoznam RMI publikovanych objektov
        registry.rebind("//localhost/server", new Server()); // vytvorim instanciu Server a publikujem na danej adrese
        log.info("Server spusteny");
    }

    private Server() throws RemoteException {

    }

    @Override
    public Liek liekPodlaId(int id) throws RemoteException {
        return null;
    }

    @Override
    public List<Liek> lieky(int limit, int offset) throws RemoteException {

        try {
            ResultSet rs = Pripojenie.executeQuery("SELECT * FROM liek ORDER BY id ASC LIMIT " + limit + " OFFSET " + offset);

            List<Liek> lieky = new ArrayList<>();

            while(rs.next()) {
                Liek liek = new Liek(rs);
                lieky.add(liek);
            }
            log.info("Nacitanie liekov");
            return lieky;

        } catch (Exception e) {
            log.error("Chyba nacitania liekov", e);
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public String vedlajsiUcinokLieku(int id) throws RemoteException {
        try {
            ResultSet rs = Pripojenie.executeQuery("SELECT v.ucinok AS vedlajsi_ucinok FROM liek l " +
                    "JOIN liek_vedlajsiUcinok lv ON l.ID = lv.ID_liek " +
                    "JOIN vedlajsiUcinok v ON lv.ID_vedlajsiUcinok = v.ID " +
                    "WHERE l.ID = " + id );

            String vedlajsi_ucinok = null;
            if (rs.next()) {
                vedlajsi_ucinok = rs.getString(1);
            }
            return vedlajsi_ucinok;

        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public String liecivyUcinokLieku(int id) throws RemoteException {
        try {
            ResultSet rs = Pripojenie.executeQuery("SELECT lu.ucinok AS liecivy_ucinok FROM liek l " +
                    "JOIN liek_liecivyUcinok ll ON l.ID = ll.ID_liek " +
                    "JOIN liecivyUcinok lu ON ll.ID_liecivyUcinok = lu.ID " +
                    "WHERE l.ID = " + id );

            String liecivy_ucinok = null;
            if (rs.next()) {
                liecivy_ucinok = rs.getString(1);
            }

            return liecivy_ucinok;

        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public void vymazLiek(int id) throws RemoteException {
        try {
            Connection con = Pripojenie.createConnection();
            PreparedStatement st = con.prepareStatement("DELETE FROM liek * WHERE id = ?");
            st.setInt(1, id);
            st.executeUpdate();
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public List<Liek> vyhladaj(String nazov) throws RemoteException {
        try {
            List<Liek> lieky = new ArrayList<>();

            ResultSet rs;
            if (nazov.contains("%"))
                rs = Pripojenie.executeQuery("SELECT * FROM liek WHERE nazov ILIKE '" + nazov + "' LIMIT 100");
            else
                rs = Pripojenie.executeQuery("SELECT * FROM liek WHERE nazov = '" + nazov + "' LIMIT 100");

            while(rs.next()) {
                Liek liek = new Liek(rs);
                lieky.add(liek);
            }
            if (lieky.size() == 0)
                log.warn("Ziadne vysledky vyhladania liekov");

            return lieky;

        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public List<PocetLiekov> zoradPodlaNazvu() throws RemoteException {
        List<PocetLiekov> poctyLiekov = new ArrayList<>();

        ResultSet rs;
        try {
            rs = Pripojenie.executeQuery("SELECT nazov, COUNT(*) FROM liek GROUP BY nazov LIMIT 100");

            while(rs.next()) {
                PocetLiekov pocetLieku = new PocetLiekov(rs.getString(1), rs.getInt(2));
                poctyLiekov.add(pocetLieku);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
        return poctyLiekov;
    }

    @Override
    public void pridaj(Liek liek) throws RemoteException {

        try {
            Connection con = Pripojenie.createConnection();
            PreparedStatement st = con.prepareStatement("INSERT INTO liek (nazov, cena, hmotnost_gram, datumExspiracie) VALUES (?, ?, ?, ?)");

            java.sql.Date datum_exs_sql = new java.sql.Date(liek.getDatumExspiracie().getTime());

            st.setString(1, liek.getNazov());
            st.setDouble(2, liek.getCena());
            st.setDouble(3, liek.getHmotnost());
            st.setDate(4, datum_exs_sql);

            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }

    }

    @Override
    public List<Liek> filtruj(double max_cena) throws RemoteException {

        List<Liek> lieky = new ArrayList<>();

        ResultSet rs;
        try {
            rs = Pripojenie.executeQuery("SELECT * FROM liek WHERE cena < " + max_cena + " ORDER BY id ASC LIMIT 200");
            while (rs.next()) {
                Liek liek = new Liek(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getDouble(4), rs.getDate(5));
                lieky.add(liek);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }

        return lieky;
    }

    @Override
    public void aktualizuj(Liek liek, int id_kliknuty) throws RemoteException {
        try {
            Connection con = Pripojenie.createConnection();
            PreparedStatement st = con.prepareStatement("UPDATE liek SET nazov=?, cena=?, " +
                    "hmotnost_gram=?, datumExspiracie=? WHERE id = " + id_kliknuty);

            java.sql.Date datum_exs_sql = new java.sql.Date(liek.getDatumExspiracie().getTime());

            st.setString(1, liek.getNazov());
            st.setDouble(2, liek.getCena());
            st.setDouble(3, liek.getHmotnost());
            st.setDate(4, datum_exs_sql);
            st.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
    }
}
