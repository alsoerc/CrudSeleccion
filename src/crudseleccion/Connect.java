package crudseleccion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alsorc
 */
public class Connect {
    //Variable de instancia propia de ésta clase
    private static Connect c;
    //Declaramos variable para hacer la conexión y es propia del JDBC 
    private Connection cx = null;
    //Constructor de la clase, es privado porque queremos que sólo podamos acceder a él a través del método getInstance()
    private Connect() {
        try {
            //Nombre del JDBC que estamos ocupando
            Class.forName("org.postgresql.Driver");
            //Cadena de conexión que incluye la ubicación de la base de datos, nombre de usuario y la contraseña para acceder
            cx = DriverManager.getConnection("jdbc:postgresql://localhost:5432/seleccion", "postgres", "12334");
            //Este mensaje se muestra cuando se realice la conexión sin problemas
            System.out.println("-Conectado-");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Aquí se presenta el patrón singleton, verificamos que ya se haya creado una instancia de ésta clase para devolverla y en caso
    //contrario la creamos y devolvemos, al hacerlo podemos acceder a los métodos de abajo.
    public static Connect getInstance() {
        if (c == null) {
            c = new Connect();
        }
        return c;
    }
    
    //Método que nos permite ejecutar las sentencias de actualización a nuestra base de datos, recibe una cadena de caracteres (la sentencia) y la ejecuta
    //Statement(Sentencias SQL):  Insert, Delete, Update => Sentencias de actualización
    public boolean execute(String sql) {
        boolean res = false;
        try {
            Statement st = cx.createStatement();
            st.execute(sql);
            res = true;
        } catch (SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
    //Método que recibe una cadena de caracteres para consultar la base de datos, permite obtener uno o varios registros de la base de datos
    //Statement(Sentencia SQL): Select => Sentencia de consulta
    //ResultSet: almacena de forma temporal el resultado de la consulta Select y permite recorrer la información obtenida.
    public ResultSet executeQuery(String sql) {
        ResultSet res = null;
        try {
            Statement st = cx.createStatement();
            res = st.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
}