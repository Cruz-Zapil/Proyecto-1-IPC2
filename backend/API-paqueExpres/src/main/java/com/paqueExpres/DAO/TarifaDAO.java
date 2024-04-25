package com.paqueExpres.DAO;

import java.sql.*;

import org.json.*;

import com.paqueExpres.util.ConexionDb;

public class TarifaDAO {

    private Connection jdbConnection;
    private JSONArray listaTarifa;
    private ConexionDb conexion;
    private final String nameTable = "tarifa";

    public void conexionExterno(Connection jdbConnection) {
        this.jdbConnection = jdbConnection;
    }

    public void conectar() throws SQLException {
        /// obtener una instancia de la conexion a la base de datos
        conexion = ConexionDb.obtenerInstancia("root", "VictorQuiej135-");
        // obtener la conexion
        jdbConnection = conexion.obtenerConexion();
    }

    public JSONArray getAllCliente() throws SQLException {
        String sqlScript = "SELECT * FROM " + nameTable;
        listaTarifa = new JSONArray();

        ///
        try (PreparedStatement statement = jdbConnection.prepareStatement(sqlScript);
                ResultSet resultSet = statement.executeQuery()) {

            /// obtener la lista de clientes y retornarlo
            listaTarifa = listarTarifa(resultSet);

            // cerrando recursos
            statement.close();
            resultSet.close();

        } finally {
            try {
                /// cerando la conexion a la base de datos
                conexion.cerrarConexion();
            } catch (SQLException e) {
                e.printStackTrace();
                ;
            }
        }

        return listaTarifa;
    }

    private JSONArray listarTarifa(ResultSet resultSet) throws JSONException, SQLException {
      
       // Crear un JSONArray para almacenar los usuarios
       JSONArray listaTarifa = new JSONArray();

       while (resultSet.next()) {
           // Crear un JSONObject para cada usuario y añadirlo al JSONArray
           JSONObject usuario = new JSONObject();

           usuario.put("ID", resultSet.getString("id_cliente"));
           usuario.put("Tipo de Tarifa", resultSet.getString("tipo_tarifa"));
           usuario.put("Moneda", resultSet.getString("moneda"));
           usuario.put("Cantidad", resultSet.getString("can"));

           listaTarifa.put(usuario);
       }

       return listaTarifa;
        
   
    }



}
