package evidenciavydavkovmysql;
import java.sql.SQLException;
import evidenciavydavkovmysql.service.*;
import evidenciavydavkovmysql.model.*;


public class Main {
    public static void main(String[] args) throws SQLException {
        VydavokService vydavokServiceObjekt = new VydavokService(); // Vytvorili objekt, resp. instanciu triedy VydavokService
        vydavokServiceObjekt.init(); // Realna inicializacia, resp. konekcia na databazu

/*        // Vytvorime prvy vydavok
        Vydavok kofola = new Vydavok("Kofola", 1.69, new Date(System.currentTimeMillis()), Kategoria.Potraviny, "Akciová kofola");

        // Vytvorime druhy vydavok
        String dateString = "2023-09-15";
        Vydavok listokDoKina = new Vydavok("Lístok do kina", 7.00, Date.valueOf(dateString), Kategoria.Kultúra, "Spiderman");

        // Ulozenie vydavku do databazy
        vydavokServiceObjekt.vytvor(kofola); // Vytvorime vydavok, ktory zaroven vlozime do databazy
        vydavokServiceObjekt.vytvor(listokDoKina); // Vytvorime dalsi vydavok, ktory vlozime do db*/


        // Vyber vydavku podla ID
        Vydavok najdenyVydavokPodlaId = vydavokServiceObjekt.vyberPodlaId(30);
        System.out.println("\nNájdený výdavok podla ID je: " + najdenyVydavokPodlaId);
        System.out.println("------------------------");

        if (najdenyVydavokPodlaId != null) {
            najdenyVydavokPodlaId.setPoznamka("James Bond");
            najdenyVydavokPodlaId.setCena(8.5); // Zmena ceny listka
            vydavokServiceObjekt.uprav(najdenyVydavokPodlaId, 3);
        } else
            System.out.println("Záznam s takým ID neexistuje");

        if (najdenyVydavokPodlaId != null) {
            Vydavok upravenyVydavokPodlaId = vydavokServiceObjekt.vyberPodlaId(3);
            System.out.println("\nUpravený výdavok podla ID je: " + upravenyVydavokPodlaId);
            System.out.println("------------------------");
        } else
            System.out.println("Záznam s takým ID neexistuje");


        // Odstran vydavok podla ID
        vydavokServiceObjekt.odstran(1);


        // Vypis vsetky zaznamy
        System.out.println("\nZobrazené všetky záznamy");
        System.out.println("------------------------");
        ArrayList<Vydavok> vsetkyVydavky = vydavokServiceObjekt.vyberVsetky();
        System.out.println(vsetkyVydavky);


        // Spocitaj vsetky vydavky
        System.out.println("\nSúčet všetkých výdavkov");
        System.out.println("------------------------");
        System.out.println(vydavokServiceObjekt.spocitajVsetkyVydavky());


        // Zisti pocet vsetkych vydavkov
        System.out.println("\nPočet všetkých výdavkov");
        System.out.println("------------------------");
        System.out.println(vydavokServiceObjekt.pocetZaznamov());


        // Vypis vydavky podla kategorie
        System.out.println("\nVypíš výdavky podľa kategórie");
        System.out.println("------------------------");

        HashMap<Kategoria, Double> vydavkyPodlaKategorie = vydavokServiceObjekt.spocitajVydavkyPodlaKategorie();
        System.out.println("Celkové výdavky na potraviny: " + vydavkyPodlaKategorie.get(Kategoria.Potraviny));


/*        for (Kategoria konkretnaKategoria : vydavkyPodlaKategorie.keySet()) {
            System.out.println("Kategória: " + konkretnaKategoria + " a Celkové výdavky: " + vydavkyPodlaKategorie.get(konkretnaKategoria));
        }*/

        // Ekvivalent vyssie
        vydavkyPodlaKategorie.entrySet().forEach(System.out::println);

        System.out.println("Koniec");
    }
}
