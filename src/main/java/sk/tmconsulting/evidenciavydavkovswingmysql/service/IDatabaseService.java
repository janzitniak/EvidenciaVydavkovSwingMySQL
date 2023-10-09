package evidenciavydavkovmysql.service;

import java.sql.SQLException;

public interface IDatabaseService {
    void init() throws SQLException; // Inicializacia databazy
}
