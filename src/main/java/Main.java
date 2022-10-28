import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);
    private static final String log4ConfigFile = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "log4j2.xml";
    final static String sqlCommand = "DROP TABLE IF EXISTS Empleados;" + " CREATE TABLE Empleados (id INT PRIMARY KEY NOT NULL, nombre VARCHAR(75), apellido VARCHAR(80), dni VARCHAR(9), email VARCHAR(80));";
    final static String insert1 = "INSERT INTO Empleados VALUES (1, 'Joni', 'Bordon', '412356789', 'jonykpo420@gmail.com');";
    final static String insert2 = "INSERT INTO Empleados VALUES (2, 'Jonathan', 'Bordon', '412356789', 'jonykpo420@gmail.com');";
    final static String insert3  = "INSERT INTO Empleados VALUES (2, 'Chouni', 'Bordon', '412356789', 'jonykpo420@gmail.com');";

    public static void main(String[] args) throws SQLException {


        Connection con = getConnection();
        var stm = con.createStatement();
        try {
            stm.execute(sqlCommand);
            stm.execute(insert1);
            stm.execute(insert2);
            stm.execute(insert3);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            String sqlQuery = "SELECT * FROM Empleados";
            var res = stm.executeQuery(sqlQuery);
            while (res.next()) {
              logger.info("Empleado: " + res.getInt(1));
            }
        }

        try {
            var id = 2;
            String deleteQuery = "DELETE FROM Empleados WHERE id=2;";
            String selectQuery = "SELECT * FROM Empleados WHERE id=2;";
            var empQuery = stm.executeQuery(selectQuery);
            empQuery.next();
            logger.info("Borrando al empleado de id: " + empQuery.getInt(1) + ", Nombre: " + empQuery.getString(2) + ", Apellido: " + empQuery.getString(3) + ", email: "  + empQuery.getString(5));
            stm.execute(deleteQuery);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }


//
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:h2:~/digital1");
    }

}
