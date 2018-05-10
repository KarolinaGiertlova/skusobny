public class Naplnanie {
    final int POCET_LEKAROV = 1200000;
    final int POCET_PREDAJNI = 2000000;
    final int POCET_LIEKOV = 20000000;
    final int POCET_OBJEDNAVOK = 1400000;
    final int POCET_ZAMESTNANCOV = 1800000;

      /*

            //vkladanie
//            String sql = "INSERT INTO lekar (ID, meno, prax) " + "VALUES (3, 'Ema Kumanova', 4);";
//            st.executeUpdate(sql);
//
//            st = con.createStatement();
//            sql = "INSERT INTO lekar (ID, meno, prax) " + "VALUES (4, 'Peter Volen', 5);";
//            st.executeUpdate(sql);

            String name;
            Random rand_cislo_prax = new Random();
            int n;
            int poradove_cislo;

            //LEKAR
            st = con.prepareStatement("INSERT INTO lekar (meno, prax_roky) VALUES (?, ?)");
            for (int i=0; i<POCET_LEKAROV; i++) {
                name = faker.name().fullName();
                n = rand_cislo_prax.nextInt(50) + 2;
                st.setString(1,name);
                st.setInt(2, n);
                st.executeUpdate();
            }
            System.out.println("lekar hotovo");

            //ZAMESTNANEC
            st = con.prepareStatement("INSERT INTO zamestnanec (meno, prax_roky) VALUES (?, ?)");
            for (int i=0; i<POCET_ZAMESTNANCOV; i++) {
                name = faker.name().fullName();
                n = rand_cislo_prax.nextInt(50) + 2;
                st.setString(1, name);
                st.setInt(2, n);
                st.executeUpdate();
            }
            System.out.println("zamestnanec hotovo");

            //PREDAJNA
            String adresa;
            String nazov_predajne;
            st = con.prepareStatement("INSERT INTO predajna (nazov, adresa) VALUES (?, ?)");
            for (int i=0; i<POCET_PREDAJNI; i++) {
                nazov_predajne = faker.company().name();
                adresa = faker.address().streetAddress();

                st.setString(1, nazov_predajne);
                st.setString(2, adresa);
                st.executeUpdate();
            }
            System.out.println("predajna hotovo");

            //LIEK
            String nazov_lieku;
            double cena, hmotnost;
            int datum_exspiracie;
            Random rand_cena = new Random();
            Random rand_hmotnost = new Random();
            Random rand_datum_exspiracie = new Random();
            Random rand_sufix = new Random();
            Random rand_prefix = new Random();
            char rand_pismeno1, rand_pismeno2;
            int prefix1, prefix2;
            int sufix;

            SimpleDateFormat dateformat3 = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date datum_exs = new java.util.Date();

            Date datum_exs_zaciatok = dateformat3.parse("30/03/2018");

            Date datum_exs_koniec = dateformat3.parse("30/03/2019");
            st = con.prepareStatement("INSERT INTO liek (nazov, cena, hmotnost_gram, datumExspiracie) VALUES (?, ?, ?, ?)");
            for (int i=0; i<POCET_LIEKOV; i++) {
                datum_exs = faker.date().between(datum_exs_zaciatok, datum_exs_koniec);
                java.sql.Date datum_exs_sql = new java.sql.Date(datum_exs.getTime());
                cena = rand_cena.nextDouble() + rand_cena.nextInt(50) + 2;
                hmotnost = rand_hmotnost.nextDouble() + rand_hmotnost.nextInt(20) + 1;
                hmotnost = BigDecimal.valueOf(hmotnost).setScale(1, RoundingMode.HALF_UP).doubleValue();
                cena = BigDecimal.valueOf(cena).setScale(2, RoundingMode.HALF_UP).doubleValue();
                prefix1 =  rand_prefix.nextInt(25) + 65;
                prefix2 =  rand_prefix.nextInt(25) + 65;
                rand_pismeno1 = (char) prefix1;
                rand_pismeno2 = (char) prefix2;

                sufix = rand_sufix.nextInt(900) + 100;
                nazov_lieku =  rand_pismeno1 + "" + rand_pismeno2 + "-" + sufix;

                st.setString(1, nazov_lieku);
                st.setDouble(2, cena);
                st.setDouble(3, hmotnost);
                st.setDate(4, datum_exs_sql);

                st.executeUpdate();
            }
            System.out.println("liek hotovo");

            //OBJEDNAVKA
            Random rand_faktura = new Random();
            Random rand_sposob_platby = new Random();
            Random rand_id_predajna = new Random();
            int id_predajna;
            double faktura;
            int sposob_platby;
            String pole[] = {"karta", "hotovost"};
            st = con.prepareStatement("INSERT INTO objednavka (faktura, sposobPlatby, ID_predajna) VALUES (?, ?, ?)");
            for (int i = 0; i<POCET_OBJEDNAVOK; i++) {
                faktura = rand_faktura.nextDouble() + rand_faktura.nextInt(150) + 50;
                faktura = BigDecimal.valueOf(faktura).setScale(2, RoundingMode.HALF_UP).doubleValue();
                sposob_platby = rand_sposob_platby.nextInt(2);
                id_predajna = rand_id_predajna.nextInt(POCET_PREDAJNI);

                st.setDouble(1, faktura);
                st.setString(2, pole[sposob_platby]);
                st.setInt(3, id_predajna);
                st.executeUpdate();
            }
            System.out.println("objednavka hotovo");

            //VEDLAJSI_UCINOK
            st = con.prepareStatement("INSERT INTO vedlajsiUcinok (ucinok, pravdepodobnost, jeSkodlivy) VALUES (?, ?, ?)");
            st.setString(1, "vyrazky");
            st.setDouble(2, 0.1);
            st.setString(3, "nie");
            st.executeUpdate();
////
            st.setString(1, "ekzem");
            st.setDouble(2, 0.06);
            st.setString(3, "ano");
            st.executeUpdate();

            st.setString(1, "zapal");
            st.setDouble(2, 0.04);
            st.setString(3, "ano");
            st.executeUpdate();

            st.setString(1, "opuchnutie");
            st.setDouble(2, 0.15);
            st.setString(3, "ano");
            st.executeUpdate();

            st.setString(1, "ospalost");
            st.setDouble(2, 0.3);
            st.setString(3, "nie");
            st.executeUpdate();
//
            st.setString(1, "pocit na vracanie");
            st.setDouble(2, 0.24);
            st.setString(3, "nie");
            st.executeUpdate();

            st.setString(1, "bolesti zaludka");
            st.setDouble(2, 0.19);
            st.setString(3, "ano");
            st.executeUpdate();

            st.setString(1, "hnacka");
            st.setDouble(2, 0.08);
            st.setString(3, "nie");
            st.executeUpdate();

            st.setString(1, "zapcha");
            st.setDouble(2, 0.05);
            st.setString(3, "ano");
            st.executeUpdate();
//
            st.setString(1, "bolest hlavy");
            st.setDouble(2, 0.35);
            st.setString(3, "nie");
            st.executeUpdate();

            st.setString(1, "busenie srdca");
            st.setDouble(2, 0.12);
            st.setString(3, "nie");
            st.executeUpdate();

            st.setString(1, "mierna depresia");
            st.setDouble(2, 0.16);
            st.setString(3, "nie");
            st.executeUpdate();

            st.setString(1, "mierne zvyseny tlak");
            st.setDouble(2, 0.36);
            st.setString(3, "nie");
            st.executeUpdate();
////
            st.setString(1, "stazene dychanie");
            st.setDouble(2, 0.05);
            st.setString(3, "ano");
            st.executeUpdate();

            st.setString(1, "kasel");
            st.setDouble(2, 0.36);
            st.setString(3, "nie");
            st.executeUpdate();

            st.setString(1, "bolest svalov");
            st.setDouble(2, 0.04);
            st.setString(3, "nie");
            st.executeUpdate();

            st.setString(1, "bolest klbov");
            st.setDouble(2, 0.02);
            st.setString(3, "nie");
            st.executeUpdate();
////
            st.setString(1, "porucha funkcie pecene");
            st.setDouble(2, 0.01);
            st.setString(3, "ano");
            st.executeUpdate();

            st.setString(1, "porucha funkcie obliciek");
            st.setDouble(2, 0.01);
            st.setString(3, "ano");
            st.executeUpdate();

            st.setString(1, "navaly uzkosti");
            st.setDouble(2, 0.08);
            st.setString(3, "nie");
            st.executeUpdate();
            System.out.println("vedlajsi ucinok hotovo");


            //LIECIVY UCINOK
            st = con.prepareStatement("INSERT INTO liecivyUcinok (ucinok, doba_liecby_dni) VALUES (?, ?)");
            st.setString(1, "akne");
            st.setInt(2, 5);
            st.executeUpdate();

            st.setString(1, "alergia");
            st.setInt(2, 12);
            st.executeUpdate();
//
            st.setString(1, "nespavost");
            st.setInt(2, 7);
            st.executeUpdate();

            st.setString(1, "chripka");
            st.setInt(2, 6);
            st.executeUpdate();

            st.setString(1, "helikobakter");
            st.setInt(2, 20);
            st.executeUpdate();

            st.setString(1, "chronicka unava");
            st.setInt(2, 8);
            st.executeUpdate();

            st.setString(1, "zapal oka");
            st.setInt(2, 9);
            st.executeUpdate();

            st.setString(1, "dezorientovanost");
            st.setInt(2, 4);
            st.executeUpdate();
//
            st.setString(1, "cisticka fibroza");
            st.setInt(2, 9);
            st.executeUpdate();

            st.setString(1, "opuchnutie");
            st.setInt(2, 2);
            st.executeUpdate();

            st.setString(1, "dermatitida");
            st.setInt(2, 17);
            st.executeUpdate();

            st.setString(1, "gastritida");
            st.setInt(2, 23);
            st.executeUpdate();

            st.setString(1, "srdcovo-cievne tazkosti");
            st.setInt(2, 30);
            st.executeUpdate();

            st.setString(1, "laryngitida");
            st.setInt(2, 26);
            st.executeUpdate();

            st.setString(1, "zapal dutin");
            st.setInt(2, 18);
            st.executeUpdate();
//
            st.setString(1, "zapal mocovych ciest");
            st.setInt(2, 13);
            st.executeUpdate();

            st.setString(1, "infekcia");
            st.setInt(2, 4);
            st.executeUpdate();

            st.setString(1, "zapal ucha");
            st.setInt(2, 11);
            st.executeUpdate();

            st.setString(1, "bolest klbov");
            st.setInt(2, 16);
            st.executeUpdate();

            st.setString(1, "svalova atrofia");
            st.setInt(2, 26);
            st.executeUpdate();
            System.out.println("liecivy ucinok hotovo");


            //LIEK - LIECIVY UCINOK
            Random rand_liek = new Random();
            Random rand_liecivy_ucinok = new Random();
            int cislo_liek, cislo_liecivy_ucinok;
//
            st = con.prepareStatement("INSERT INTO liek_liecivyUcinok (ID_liek, ID_liecivyUcinok) VALUES (?, ?)");
            for (int i = 0; i<1600000; i++) {
                cislo_liek = rand_liek.nextInt(POCET_LIEKOV - 1) + 1;
                //cislo_liek = rand_liek.nextInt(39) + 91;
                cislo_liecivy_ucinok = rand_liecivy_ucinok.nextInt(19) + 1;

                st.setInt(1, cislo_liek);
                st.setInt(2, cislo_liecivy_ucinok);
                st.executeUpdate();
            }
            System.out.println("liek_liecivy ucinok hotovo");


            //LIEK - OBJEDNAVKA
            Random rand_objednavka = new Random();
            int cislo_objednavky;

            st = con.prepareStatement("INSERT INTO liek_objednavka (ID_liek, ID_objednavka) VALUES (?, ?)");
            for (int i = 0; i<1300000; i++) {
                cislo_liek = rand_liek.nextInt(POCET_LIEKOV - 1) + 1;
                cislo_objednavky = rand_objednavka.nextInt(POCET_OBJEDNAVOK - 1) + 1;

                st.setInt(1, cislo_liek);
                st.setInt(2, cislo_objednavky);
                st.executeUpdate();
            }
            System.out.println("liek_objednavka hotovo");


            //LIEK - VEDLAJSI UCINOK
            Random rand_vedlajsi_ucinok = new Random();
            int cislo_vedlajsi_ucinok;
            st = con.prepareStatement("INSERT INTO liek_vedlajsiUcinok (ID_liek, ID_vedlajsiUcinok) VALUES (?, ?)");
            for (int i = 0; i<1100000; i++) {
                cislo_liek = rand_liek.nextInt(POCET_LIEKOV - 1) + 1;
                cislo_vedlajsi_ucinok = rand_vedlajsi_ucinok.nextInt(19) + 1;

                st.setInt(1, cislo_liek);
                st.setInt(2, cislo_vedlajsi_ucinok);
                st.executeUpdate();
            }
            System.out.println("liek_vedlajsi ucinok hotovo");


            //PREDAJNA - ZAMESTNANEC
            Random rand_id_zamestnanec = new Random();
            int cislo_id_predajna, cislo_id_zamestnanec;
            st = con.prepareStatement("INSERT INTO predajna_zamestnanec (ID_predajna, ID_zamestnanec) VALUES (?, ?)");
            for (int i = 0; i<1700000; i++) {
                cislo_id_predajna = rand_id_predajna.nextInt(POCET_PREDAJNI - 1) + 1;
                cislo_id_zamestnanec = rand_id_zamestnanec.nextInt(POCET_ZAMESTNANCOV - 1) + 1;

                st.setInt(1, cislo_id_predajna);
                st.setInt(2, cislo_id_zamestnanec);
                st.executeUpdate();
            }
            System.out.println("predajna_zamestnanec hotovo");


            //PREDPIS
            Random rand_id_lekar = new Random();
            int cislo_id_lekar;
            SimpleDateFormat dateformat4 = new SimpleDateFormat("dd/MM/yyyy");
            //java.util.Date datum_exs = new java.util.Date();
//
            //Date datum_exs_zaciatok = dateformat4.parse("30/03/2018");
            //Date datum_exs_koniec = dateformat4.parse("15/04/2018");

            st = con.prepareStatement("INSERT INTO predpis (ID_lekar, ID_objednavka, datumExspiracie) VALUES (?, ?, ?)");
            for (int i = 0; i<2000000; i++) {
                datum_exs = faker.date().between(datum_exs_zaciatok, datum_exs_koniec);
                java.sql.Date datum_exs_sql = new java.sql.Date(datum_exs.getTime());
                cislo_id_lekar = rand_id_lekar.nextInt(POCET_LEKAROV - 1) + 1;
                cislo_objednavky = rand_objednavka.nextInt(POCET_OBJEDNAVOK - 1) + 1;


                st.setInt(1, cislo_id_lekar);
                st.setInt(2, cislo_objednavky);
                st.setDate(3, datum_exs_sql);
                st.executeUpdate();
            }
            System.out.println("predpis hotovo");



 /*           ResultSet rs = st.executeQuery( "SELECT * FROM lekar;" );
            while ( rs.next() ) {
                int id = rs.getInt("ID");
                String  meno = rs.getString("meno");
                int prax = rs.getInt("prax");

                System.out.println( "ID = " + id );
                System.out.println( "MENO = " + meno );
                System.out.println( "PRAX = " + prax );

                System.out.println();
            }
            rs.close();


            st.close();

            con.commit();
            con.close();
            */
}
