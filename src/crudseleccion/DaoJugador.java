package crudseleccion;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Jugador;


/**
 *
 * @author alsorc
 */
public class DaoJugador implements IDao<Jugador>{
    
    private Connect connect = Connect.getInstance();
    
        private String getStatement(int statementOption){
        String[] statements = new String[]{
                            "INSERT INTO jugadores  VALUES (?,?,?);",
                            "DELETE FROM jugadores WHERE (id = ?);",
                            "UPDATE jugadores SET nombre = ?, edad = ? WHERE (id = ?);",
                            "SELECT * FROM jugadores WHERE (id = ?);",
                            "SELECT * FROM jugadores;"};

        return statements[statementOption];
    }

    @Override
    public boolean insert(Jugador t) {
        String [] values = {String.valueOf(t.getId()), t.getNombre(), String.valueOf(t.getEdad())};
        System.err.println("Datos de DaoJugador" + values[0] + values[1] + values[2]);
        return connect.updateTable(getStatement(0), 0, values);
    }

    @Override
    public boolean delete(Jugador t) {
        String [] values = {String.valueOf(t.getId())};
        return connect.updateTable(getStatement(1), 1, values);
    }

    @Override
    public boolean update(Jugador t) {
        String [] values = {String.valueOf(t.getId()), t.getNombre(), String.valueOf(t.getEdad())};
        return connect.updateTable(getStatement(2), 2, values);
       
    }

    @Override
    public List<Jugador> getRecords() {
      List<Jugador> playerList = new ArrayList<>();
      ResultSet data = connect.getData(getStatement(4), 4, 0 ); 
        try {
            while(data.next()){
                Jugador jugador = new Jugador();
                jugador.setId(data.getInt(1));
                jugador.setNombre(data.getString(2));
                jugador.setEdad(data.getInt(3));
                playerList.add(jugador);
            } 
        } catch (SQLException ex) {
            Logger.getLogger(DaoJugador.class.getName()).log(Level.SEVERE, null, ex);
        }
      return playerList;  
    }

    @Override
    public Jugador getOneRecord(Jugador t) {
        Jugador jugador = new Jugador();
        ResultSet data = connect.getData(getStatement(3), 3, t.getId());
        try {
            while(data.next()){
                jugador.setId(data.getInt(1));
                jugador.setNombre(data.getString(2));
                jugador.setEdad(data.getInt(3));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoJugador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jugador;
    }
    
    
}
