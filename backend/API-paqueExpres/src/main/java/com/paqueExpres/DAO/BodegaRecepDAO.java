package com.paqueExpres.DAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;

import org.json.*;

import com.paqueExpres.util.ConexionDb;

public class BodegaRecepDAO {

    private Connection jdbConnection;
    private JSONArray listaBodega;
    private ConexionDb conexion;
    private final String nameTable = "bodega_recep";

    public void conexionExterno(Connection jdbConnection) {
        this.jdbConnection = jdbConnection;
    }

    public void conectar() throws SQLException {
        /// obtener una instancia de la conexion a la base de datos
        conexion = ConexionDb.obtenerInstancia("root", "VictorQuiej135-");
        // obtener la conexion
        jdbConnection = conexion.obtenerConexion();
    }

    public JSONArray getAllBodegaRecep() throws SQLException {
        String sqlScript = "SELECT * FROM " + nameTable;
        listaBodega = new JSONArray();

        ///
        try (PreparedStatement statement = jdbConnection.prepareStatement(sqlScript);
                ResultSet resultSet = statement.executeQuery()) {

            /// obtener la lista de clientes y retornarlo
            listaBodega = listarBodega(resultSet);

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

        return listaBodega;
    }


    public JSONArray getBodega(String columna, String condicion) throws SQLException {

        String sqlScrip = "SELECT * FROM " + nameTable + "WHERE ? = ?";

        try (PreparedStatement statement = jdbConnection.prepareStatement(sqlScrip)) {

            statement.setString(1, columna);
            statement.setString(2, condicion);

            ResultSet resultSet = statement.executeQuery();

            /// obtener la lista de clientes
            listaBodega = listarBodega(resultSet);

            /// cerrando recursos.
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

        return listaBodega;
    }



    public boolean newBodega(BufferedReader datosEnviados) throws IOException, JSONException, SQLException {

        StringBuilder sb = new StringBuilder();
        String line;
        int filasAfectadas = 0;

        while ((line = datosEnviados.readLine()) != null) {
            sb.append(line);
        }

        JSONObject jsonDatos = new JSONObject(sb.toString());

        String sql = "INSERT INTO " + nameTable
                + "( id_recepcionista, id_destino, estado) VALUES (?,?,?)";

        try (PreparedStatement statement = jdbConnection.prepareStatement(sql)) {

            statement.setString(1, jsonDatos.getString("id_recepcionista"));
            statement.setString(2, jsonDatos.getString("id_destino"));
            statement.setString(3, jsonDatos.getString("estado"));

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


    private JSONArray listarBodega(ResultSet resultSet) throws SQLException, JSONException {
        // Crear un JSONArray para almacenar los usuarios
        JSONArray listaBodega = new JSONArray();

        while (resultSet.next()) {
            // Crear un JSONObject para cada usuario y añadirlo al JSONArray
            JSONObject bodega = new JSONObject();

            bodega.put("id_bodega", resultSet.getString("id_bodega"));
            bodega.put("id_recepcionista", resultSet.getString("id_recepcionista"));
            bodega.put("id_destino", resultSet.getString("id_destino"));


             // Obtener el estado del usuario
             int estadoInt = resultSet.getInt("estado");
             String estado = (estadoInt == 1) ? "activo" : "inactivo";
             bodega.put("estado", estado);

            listaBodega.put(bodega);
        }

        return listaBodega;
    }

}
