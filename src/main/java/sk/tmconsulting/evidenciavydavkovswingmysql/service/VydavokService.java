package sk.tmconsulting.evidenciavydavkovswingmysql.service;

import evidenciavydavkovmysql.model.*;
import evidenciavydavkovmysql.service.*;
import sk.tmconsulting.evidenciavydavkovswingmysql.model.Kategoria;
import sk.tmconsulting.evidenciavydavkovswingmysql.model.Vydavok;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class VydavokService implements IVydavokService, IDatabaseService {

    private Connection conn; // Platnost premennej conn, resp. databazovej konekcie v ramci celej triedy

    @Override
    public void init() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/vydavky_db";
        String username = "root";
        String password = "password";
        System.out.println("Connecting database...");

        conn = DriverManager.getConnection(url, username, password);
        System.out.println("Database connected!");
    }

    @Override
    public void vytvor(Vydavok vydavok) throws SQLException {
        String query = "INSERT INTO vydavok (nazov, cena, datum, kategoria, poznamka) VALUES (?, ?, ?, ?, ?)";
        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = conn.prepareStatement(query);

        preparedStmt.setString(1, vydavok.getNazov());
        preparedStmt.setDouble(2, vydavok.getCena());
        preparedStmt.setDate(3, vydavok.getDatum());
        preparedStmt.setString(4, vydavok.getKategoria().toString());
        preparedStmt.setString(5, vydavok.getPoznamka());

        // execute the preparedstatement
        preparedStmt.execute();
        preparedStmt.close();
    }

    @Override
    public Vydavok vyberPodlaId(int id) throws SQLException {
        String query = "SELECT nazov, cena, kategoria, datum, poznamka FROM vydavok WHERE id = ?";
        // create the java statement
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt(1, id);
        // execute the query, and get a java resultset
        ResultSet rs = preparedStmt.executeQuery(); // Tu sa uz nepise query ako argument v metode executeQuery
        if (rs.next())
            //return new Vydavok(rs.getString("nazov"), rs.getDouble("cena"), rs.getString("kategoria"),
            return new Vydavok(
                    id,
                    rs.getString("nazov"),
                    rs.getDouble("cena"),
                    rs.getDate("datum"),
                    Kategoria.valueOf(rs.getString("kategoria")),
                    rs.getString("poznamka")
            );
        preparedStmt.close();
        return null;
    }

    @Override
    public void uprav(Vydavok vydavok, int id) throws SQLException {
        // create the java mysql update preparedstatement
        String query = "UPDATE vydavok SET nazov = ?, cena = ?, datum = ?, kategoria = ?, poznamka = ? WHERE id = ?";
        PreparedStatement preparedStmt = conn.prepareStatement(query);

        preparedStmt.setString(1, vydavok.getNazov());
        preparedStmt.setDouble(2, vydavok.getCena());
        preparedStmt.setDate(3, vydavok.getDatum());
        preparedStmt.setString(4, vydavok.getKategoria().toString());
        preparedStmt.setString(5, vydavok.getPoznamka());
        preparedStmt.setInt(6, id);

        // execute the java preparedstatement
        preparedStmt.executeUpdate();
        preparedStmt.close();
    }

    @Override
    public void odstran(int id) throws SQLException {
        String query = "DELETE FROM vydavok WHERE id = ?"; // Odstranujeme z vydavku zaznam, resp. riadok, kt. ID je 1
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt(1, id);
        preparedStmt.execute();
        preparedStmt.close();
    }

    @Override
    public ArrayList<Vydavok> vyberVsetky() throws SQLException {
        ArrayList<Vydavok> vydavky = new ArrayList<>();
        // our SQL SELECT query.
        // if you only need a few columns, specify them by name instead of using "*"
        String query = "SELECT * FROM vydavok";
        // create the java statement
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        // execute the query, and get a java resultset
        ResultSet rs = preparedStmt.executeQuery();
        // iterate through the java resultset
        while (rs.next()) {
            vydavky.add(
                    new Vydavok(
                            rs.getInt("id"),
                            rs.getString("nazov"),
                            rs.getDouble("cena"),
                            rs.getDate("datum"),
                            Kategoria.valueOf(rs.getString("kategoria")),
                            rs.getString("poznamka")
                    )
            );
        }
        preparedStmt.close();
        return vydavky;
    }

    @Override
    public double spocitajVsetkyVydavky() {
        String query = "SELECT SUM(cena) FROM vydavok";
        // create the java statement
        try (
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                ResultSet rs = preparedStmt.executeQuery()) { // Tu sa uz nepise query ako argument v metode executeQuery
            if (rs.next()) {
                return rs.getDouble("SUM(cena)");
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            // Sami osetrujeme co v pripade, ak vynimka
        }

        return 0;
    }

    @Override
    public int pocetZaznamov() {
        String query = "SELECT COUNT(id) FROM vydavok";
        // create the java statement
        try (
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                ResultSet rs = preparedStmt.executeQuery()) { // Tu sa uz nepise query ako argument v metode executeQuery
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            // Sami osetrujeme co v pripade, ak vynimka
        }
        return 0;
    }

    @Override
    public HashMap<Kategoria, Double> spocitajVydavkyPodlaKategorie() throws SQLException {
        HashMap<Kategoria, Double> vydavkyPodlaKategorie = new HashMap<>();
        String query = "SELECT kategoria, SUM(cena) AS cena_podla_kategorie FROM vydavok GROUP BY kategoria";
        // create the java statement
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        // execute the query, and get a java resultset
        ResultSet rs = preparedStmt.executeQuery();
        // iterate through the java resultset
        while (rs.next()) {
            vydavkyPodlaKategorie.put(Kategoria.valueOf(rs.getString("kategoria")), rs.getDouble("cena_podla_kategorie"));
        }
        preparedStmt.close();
        return vydavkyPodlaKategorie;
    }
}
