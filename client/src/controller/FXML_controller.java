package controller;

import Model.Liek;
import Model.PocetLiekov;
import common.ServerInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FXML_controller implements Initializable {
    int id_kliknuty;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    ServerInterface server;
    Properties settings;
    ResourceBundle bundle;

    @FXML
    private TextField max_cena_lieky;
    @FXML
    private TextArea textDetail;
    @FXML
    private TableView<Liek> vypisovaciaTabulka;
    @FXML
    private TableView<PocetLiekov> zoradovaciaTabulka;
    @FXML
    private TextField nazovLiekuPridaj;
    @FXML
    private TextField cenaPridaj;
    @FXML
    private TextField hmotnostPridaj;
    @FXML
    private DatePicker datepicker;
    @FXML
    private TextField Vyhladanie;
    @FXML
    private Pagination pagination;
    @FXML
    private Button PridanieLieku;
    @FXML
    private Button ZaciatocneNacitanie;
    @FXML
    private Label maxPriceLabel;
    @FXML
    private TableColumn nazov;
    @FXML
    private TableColumn cena;
    @FXML
    private TableColumn hmotnost;
    @FXML
    private TableColumn datumExspiracie;
    @FXML
    private Button zmazanieButton;
    @FXML
    private Button vyhladanieButton;
    @FXML
    private Button zoradenieButton;
    @FXML
    private TableColumn nazovLieku;
    @FXML
    private TableColumn pocetLieku;
    @FXML
    private Button aktualizujButton;


    @FXML
    private void nacitanie() {
        try {
            int from = pagination.getCurrentPageIndex() * 20;
            int to = 20;

            List<Liek> lieky = server.lieky(to, from);

            ObservableList<Liek> data = FXCollections.observableArrayList(lieky);
            vypisovaciaTabulka.setItems(data);

            spracovanieRiadku();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            e.printStackTrace();
        }

    }

    @FXML
    private void vyhladanie() {
        try {
            String vyhladavanyLiek = Vyhladanie.getText();

            List<Liek> lieky = server.vyhladaj(vyhladavanyLiek);

            ObservableList<Liek> data = FXCollections.observableArrayList(lieky);
            vypisovaciaTabulka.setItems(data);

            spracovanieRiadku();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    private void zoradeniePodlaNazvu() {
        List<PocetLiekov> poctyLiekov = null;
        try {
            poctyLiekov = server.zoradPodlaNazvu();
            ObservableList<PocetLiekov> data2 = FXCollections.observableArrayList(poctyLiekov);
            zoradovaciaTabulka.setItems(data2);

            spracovanieRiadku();
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }

    private void spracovanieRiadku() {
        //String vedlajsi_ucinok;
        vypisovaciaTabulka.setRowFactory( tv -> {
            TableRow<Liek> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
                    Liek liek = row.getItem();
                    id_kliknuty = liek.getId();

                    try {

                        String vedlajsi_ucinok = server.vedlajsiUcinokLieku(id_kliknuty);
                        String liecivy_ucinok = server.liecivyUcinokLieku(id_kliknuty);

                        String text = String.format("%s: %d%n"+"%s: %s%n"+"%s: %.2f%n"+"%s: %.2f%n"+"%s: %s%n",
                                bundle.getString("detailID"),
                                liek.getId(),
                                bundle.getString("detailNazov"),
                                liek.getNazov(),
                                bundle.getString("detailCena"),
                                liek.getCena(),
                                bundle.getString("detailHmotnost"),
                                liek.getHmotnost(),
                                bundle.getString("detailDatumExspiracie"),
                                liek.getDatumExspiracie().toString()
                        );

                        if (vedlajsi_ucinok == null)
                            vedlajsi_ucinok = bundle.getString("detailNevyplnene");

                        text += "\n"+bundle.getString("detailVedlajsiUcinok")+": " + vedlajsi_ucinok;

                        if (liecivy_ucinok == null)
                            liecivy_ucinok = bundle.getString("detailNevyplnene");

                        text += "\n"+bundle.getString("detailLiecivyUcinok")+": " + liecivy_ucinok;

                        textDetail.setText(text);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                else if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Liek rowData = row.getItem();
                    id_kliknuty = rowData.getId();
                    nazovLiekuPridaj.setText(rowData.getNazov());
                    cenaPridaj.setText(String.valueOf(rowData.getCena()));
                    hmotnostPridaj.setText(String.valueOf(rowData.getHmotnost()));

                    Date datum = rowData.getDatumExspiracie();
                    LocalDate localDatum = LocalDate.parse(datum.toString(), formatter);
                    datepicker.setValue(localDatum);
                }
            });
            return row ;
        });
    }

    @FXML
    private void vymazanie() {
        try {
            server.vymazLiek(id_kliknuty);
            nacitanie();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    private void pridavanie() {

        String nazov = nazovLiekuPridaj.getText();
        double cena = Double.parseDouble(cenaPridaj.getText());
        double hmotnost = Double.parseDouble(hmotnostPridaj.getText());
        LocalDate datumExspiracie = datepicker.getValue();

        Date datum = Date.from(datumExspiracie.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Liek liek = new Liek(0, nazov, cena, hmotnost, datum);

        try {
            server.pridaj(liek);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            e.printStackTrace();
        }

    }

    @FXML
    private void filtrovanie() {


        int max_cena = Integer.parseInt(max_cena_lieky.getText());

        try {
            List<Liek> lieky = server.filtruj(max_cena);

            ObservableList<Liek> data = FXCollections.observableArrayList(lieky);
            vypisovaciaTabulka.setItems(data);

            spracovanieRiadku();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            e.printStackTrace();
        }

    }

    @FXML
    private void aktualizovanie() {

        LocalDate datumExspiracie = datepicker.getValue();
        Date datum = Date.from(datumExspiracie.atStartOfDay(ZoneId.systemDefault()).toInstant());


        try {
            Liek liek = new Liek(0, nazovLiekuPridaj.getText(), Double.parseDouble(cenaPridaj.getText()), Double.parseDouble(hmotnostPridaj.getText()), datum);
            server.aktualizuj(liek, id_kliknuty);
            nacitanie();
        }
        catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            e.printStackTrace();
        }
    }



    private void nastavJazyk(Locale locale) {

        settings.put("language", locale.getLanguage());
        settings.put("country", locale.getCountry());
        try {
            settings.store(new FileOutputStream("settings.properties"), null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Locale.setDefault(locale);
        bundle = ResourceBundle.getBundle("lang/bundle");

        maxPriceLabel.setText(bundle.getString("maxPriceLabel"));
        nazov.setText(bundle.getString("nazov"));
        cena.setText(bundle.getString("cena"));
        hmotnost.setText(bundle.getString("hmotnost"));
        datumExspiracie.setText(bundle.getString("datumExspiracie"));
        nazovLiekuPridaj.setPromptText(bundle.getString("nazovLiekuPridaj"));
        cenaPridaj.setPromptText(bundle.getString("cenaPridaj"));
        hmotnostPridaj.setPromptText(bundle.getString("hmotnostPridaj"));
        PridanieLieku.setText(bundle.getString("PridanieLieku"));
        ZaciatocneNacitanie.setText(bundle.getString("ZaciatocneNacitanie"));
        datepicker.setPromptText(bundle.getString("datepicker"));
        zmazanieButton.setText(bundle.getString("zmazanieButton"));
        Vyhladanie.setPromptText(bundle.getString("Vyhladanie"));
        vyhladanieButton.setText(bundle.getString("vyhladanieButton"));
        zoradenieButton.setText(bundle.getString("zoradenieButton"));
        nazovLieku.setText(bundle.getString("nazovLieku"));
        pocetLieku.setText(bundle.getString("pocetLieku"));
        aktualizujButton.setText(bundle.getString("aktualizujButton"));
    }


    @FXML
    private void nastavenieSk() {
        nastavJazyk(new Locale("sk", "SK"));
    }

    @FXML
    private void nastavenieEng() {
        nastavJazyk(new Locale("en", "GB"));
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        TableColumn<Liek, ?> firstNameCol = vypisovaciaTabulka.getColumns().get(0);
        firstNameCol.setCellValueFactory(new PropertyValueFactory("nazov"));
        TableColumn<Liek, ?> secondColumn = vypisovaciaTabulka.getColumns().get(1);
        secondColumn.setCellValueFactory(new PropertyValueFactory("cena"));
        TableColumn<Liek, ?> thirdColumn = vypisovaciaTabulka.getColumns().get(2);
        thirdColumn.setCellValueFactory(new PropertyValueFactory("hmotnost"));
        TableColumn<Liek, ?> fourthColumn = vypisovaciaTabulka.getColumns().get(3);
        fourthColumn.setCellValueFactory(new PropertyValueFactory("datumExspiracie"));

        TableColumn<PocetLiekov, ?> firstC = zoradovaciaTabulka.getColumns().get(0);
        firstC.setCellValueFactory(new PropertyValueFactory("nazovLieku"));
        TableColumn<PocetLiekov, ?> secondC = zoradovaciaTabulka.getColumns().get(1);
        secondC.setCellValueFactory(new PropertyValueFactory("pocetLieku"));

        try {
            settings = new Properties();
            InputStream in = new FileInputStream("settings.properties");  //ulozenie jazyka, ktory bol naposledy spusteny
            settings.load(in);
        } catch (Exception e) {
            settings = new Properties(); //ak je to potrebne ulozi sa default slovensky
            settings.put("language", "sk");
            settings.put("country", "SK");
        }

        String language = settings.getProperty("language");
        String country = settings.getProperty("country");
        nastavJazyk(new Locale(language, country));


        Exception exception = null;
        for (int i = 0; i <= 3; i++) {
            try {
                Registry registry = LocateRegistry.getRegistry(1099); // pripoji sa na registry
                server = (ServerInterface) registry.lookup("//localhost/server"); // vyhlada podla mena
            } catch (Exception e) {
                exception = e;
                try {
                    Thread.sleep(250);
                } catch (Exception ignored) {}
            }
        }

        if (exception != null && server == null) {
            new Alert(Alert.AlertType.ERROR, "Nepodarilo sa pripojit na server\n" + exception.getMessage()).show();
            exception.printStackTrace();
        }
    }

}