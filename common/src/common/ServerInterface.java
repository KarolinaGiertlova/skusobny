package common;

import Model.Liek;
import Model.PocetLiekov;
//import org.graalvm.compiler.lir.sparc.SPARCArithmetic;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServerInterface extends Remote { // rozhranie, v ktorom su definovane dostupne metody
    // vsetky metody musia throws RemoteException;
    Liek liekPodlaId(int id) throws RemoteException;
    List<Liek> lieky(int limit, int offset) throws RemoteException;
    String vedlajsiUcinokLieku(int id) throws RemoteException;
    String liecivyUcinokLieku(int id) throws RemoteException;
    void vymazLiek(int id) throws RemoteException;
    List<Liek> vyhladaj(String nazov) throws RemoteException;
    List<PocetLiekov> zoradPodlaNazvu() throws RemoteException;
    void pridaj(Liek liek) throws RemoteException;
    List<Liek> filtruj(double max_cena) throws RemoteException;
    void aktualizuj(Liek liek, int id_kliknuty) throws RemoteException;
}
