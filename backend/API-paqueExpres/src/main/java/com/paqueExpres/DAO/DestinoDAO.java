package com.paqueExpres.DAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;

import org.json.*;

import com.paqueExpres.util.ConexionDb;

public class DestinoDAO {


    private Connection jdbConnection;
    private JSONArray listaDesino;
    private ConexionDb conexion;
    private final String nameTable = "destino";

    public void conexionExterno(Connection jdbConnection) {
        this.jdbConnection = jdbConnection;
    }

    public void conectar() throws SQLException {
        /// obtener una instancia de la conexion a la base de datos
        conexion = ConexionDb.obtenerInstancia("root", "VictorQuiej135-");
        // obtener la conexion
        jdbConnection = conexion.obtenerConexion();
    }

    public JSONArray getAllDestino() throws SQLException {
        String sqlScript = "SELECT * FROM " + nameTable;
        listaDesino = new JSONArray();

        ///
        try (PreparedStatement statement = jdbConnection.prepareStatement(sqlScript);
                ResultSet resultSet = statement.executeQuery()) {

            /// obtener la lista de clientes y retornarlo
            listaDesino = listarDestino(resultSet);

            // cerrando recursos
            statement.close();
            resultSet.close();

        } finally {
            try {
                /// cerando la conexion a la base de datos
                conexion.cerrarConexion();
            } catch (SQLException e) {
                e.printStackTrace();
                
            }
        }

        return listaDesino;
    }

    public boolean newDestino(BufferedReader datosEnviados) throws IOException, JSONException, SQLException {

        StringBuilder sb = new StringBuilder();
        String line;
        int filasAfectadas = 0;

        while ((line = datosEnviados.readLine()) != null) {
            sb.append(line);
        }

        JSONObject jsonDatos = new JSONObject(sb.toString());

        String sql = "INSERT INTO " + nameTable
                + "(id_destino,nombre, direccion, cuota) VALUES (?,?,?,?,?,?)";

        try (PreparedStatement statement = jdbConnection.prepareStatement(sql)) {

            statement.setString(1, jsonDatos.getString("id_cliente"));
            statement.setString(2, jsonDatos.getString("nombre"));
            statement.setString(4, jsonDatos.getString("direccion"));
            statement.setString(3, jsonDatos.getString("cuota"));
           
            filasAfectadas = statement.executeUpdate();

            /// cerrando recursos:
            statement.close();

        } finally {
            try {
                conexion.cerrarConexion();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        if (filasAfectadas > 0) {
            return true;
        }

        return false;
    }

    private JSONArray listarDestino(ResultSet resultSet) throws SQLException, JSONException {
        // Crear un JSONArray para almacenar los usuarios
        JSONArray listaClientes = new JSONArray();

        while (resultSet.next()) {
            // Crear un JSONObject para cada usuario y añadirlo al JSONArray
            JSONObject usuario = new JSONObject();

            usuario.put("ID", resultSet.getString("id_destino"));
            usuario.put("Nombre", resultSet.getString("nombre"));
            usuario.put("Direccion", resultSet.getString("direccion"));
            usuario.put("Cuota", resultSet.getString("cuota"));
           
            listaClientes.put(usuario);
        }

        return listaClientes;
    }


    
    
}
