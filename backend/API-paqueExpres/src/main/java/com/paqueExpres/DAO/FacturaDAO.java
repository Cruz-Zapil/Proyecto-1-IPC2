package com.paqueExpres.DAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;

import org.json.*;

import com.paqueExpres.util.ConexionDb;

public class FacturaDAO {

    private Connection jdbConnection;
    private JSONArray listaFactura;
    private ConexionDb conexion;
    private final String nameTable = "factura";

    public void conexionExterno(Connection jdbConnection) {
        this.jdbConnection = jdbConnection;
    }

    public void conectar() throws SQLException {
        /// obtener una instancia de la conexion a la base de datos
        conexion = ConexionDb.obtenerInstancia("root", "VictorQuiej135-");
        // obtener la conexion
        jdbConnection = conexion.obtenerConexion();
    }

    public boolean newFactura(BufferedReader datosEnviados) throws IOException, JSONException, SQLException {

        StringBuilder sb = new StringBuilder();
        String line;
        int filasAfectadas = 0;

        while ((line = datosEnviados.readLine()) != null) {
            sb.append(line);
        }

        JSONObject jsonDatos = new JSONObject(sb.toString());

        String sql = "INSERT INTO " + nameTable
                + "(id_cliente, id_recepcionista, fecha, total) VALUES (?,?,?,?)";

        try (PreparedStatement statement = jdbConnection.prepareStatement(sql)) {

            statement.setString(1, jsonDatos.getString("id_cliente"));
            statement.setString(2, jsonDatos.getString("id_recepcionista"));
            statement.setString(3, jsonDatos.getString("fecha"));
            statement.setString(4, jsonDatos.getString("total"));

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

    public JSONArray getAllDetalleFactura() throws SQLException {
        String sqlScript = "SELECT * FROM " + nameTable;
        listaFactura = new JSONArray();

        ///
        try (PreparedStatement statement = jdbConnection.prepareStatement(sqlScript);
                ResultSet resultSet = statement.executeQuery()) {

            /// obtener la lista de clientes y retornarlo
            listaFactura = listarFactura(resultSet);

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

        return listaFactura;
    }

    private JSONArray listarFactura(ResultSet resultSet) throws SQLException, JSONException {
        // Crear un JSONArray para almacenar los usuarios
        JSONArray listaFactura = new JSONArray();

        while (resultSet.next()) {
            // Crear un JSONObject para cada usuario y añadirlo al JSONArray
            JSONObject factura = new JSONObject();

            factura.put("ID", resultSet.getString("id_factura"));
            factura.put("ID Cliente", resultSet.getString("id_cliente"));
            factura.put("ID Recepcionista", resultSet.getString("id_recepcionista"));
            factura.put("Fecha", resultSet.getString("fecha"));
            factura.put("Sub-Total", resultSet.getString("sub_total")); 
            factura.put("Total", resultSet.getString("total"));        

            listaFactura.put(factura);
        }

        return listaFactura;
    }



}
