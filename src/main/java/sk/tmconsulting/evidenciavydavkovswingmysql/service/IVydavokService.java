package sk.tmconsulting.evidenciavydavkovswingmysql.service;

import sk.tmconsulting.evidenciavydavkovswingmysql.model.Kategoria;
import sk.tmconsulting.evidenciavydavkovswingmysql.model.Vydavok;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public interface IVydavokService {
    // CRUD operacie
    void vytvor(Vydavok vydavok) throws SQLException; // Vytvor, resp. vloz do databazy novy vydavok
    Vydavok vyberPodlaId(int id) throws SQLException; // Vyber vydavok podla id, vrati nam vydavok
    void uprav(Vydavok vydavok, int id) throws SQLException; // Uprav, resp. aktualizuj podla id, je vsak potrebne uviest zmeneny vydavok
    void odstran(int id) throws SQLException; // Odstrani vydavok podla id
    ArrayList<Vydavok> vyberVsetky() throws SQLException; // Vyberie vsetky zaznamy a vlozi do ArrayList-u
    double spocitajVsetkyVydavky() throws SQLException; // Spocita vsetky vydavky
    int pocetZaznamov(); // Zisti pocet zaznamov
    HashMap<Kategoria, Double> spocitajVydavkyPodlaKategorie() throws SQLException; // Spocita celkove naklady podla konkretnej kategorie
}
