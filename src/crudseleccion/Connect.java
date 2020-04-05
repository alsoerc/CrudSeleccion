package crudseleccion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
   
    private static Connect connectToDb;
   
    private Connection driverPostgres = null;
    
    private boolean successQuery = false;
    
    private transient  PreparedStatement preQuery;
    
    private Connect() {
        try {
            //Nombre del JDBC en uso
            Class.forName("org.postgresql.Driver");
            //Cadena de conexión que incluye la ubicación de la base de datos, nombre de usuario y la contraseña para acceder
            driverPostgres = DriverManager.getConnection("jdbc:postgresql://localhost:5432/seleccion", "postgres", "12334");
            //Mensaje cuando se realice la conexión sin problemas
            System.out.println("-Conectado-");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Implementación de patrón singleton
    public static Connect getInstance(){
        if(connectToDb == null)
            connectToDb = new Connect();
        return connectToDb;
    }
    
    public boolean updateTable(String statement,int statementOption, String[] values) {
        try {
            preQuery = driverPostgres.prepareStatement(statement);
            
            switch(statementOption){
                case 0:
                    System.err.println("Datos de DaoJugador" + values[0] + values[1] + values[2]);
                    preQuery.setInt(1, Integer.parseInt(values[0]));
                    preQuery.setString(2, values[1]);
                    preQuery.setInt(3, Integer.parseInt(values[2]));
                    break;
                case 1:
                    preQuery.setInt(1, Integer.parseInt(values[0]));
                    break;
                case 2:
                    preQuery.setString(1,values[1]);
                    preQuery.setInt(2,Integer.parseInt(values[2]));
                    preQuery.setInt(3,Integer.parseInt(values[0]));
                    break;
                default:
                    System.out.println("No elegiste una opción válida");
            }
            if(preQuery.executeUpdate()>0)
                successQuery = true;
            
        } catch (SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return successQuery;
    }
    
    public ResultSet getData(String statement, int statementOption,int id) {
        ResultSet data = null;
        try {
            preQuery = driverPostgres.prepareStatement(statement);
            
            if ( statementOption == 3)
                preQuery.setInt(1, id);
            
            data = preQuery.executeQuery();            
        } catch (SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }    
}