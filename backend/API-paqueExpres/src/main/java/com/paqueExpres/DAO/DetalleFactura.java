package com.paqueExpres.DAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;

import org.json.*;

import com.paqueExpres.util.ConexionDb;

public class DetalleFactura {

    private Connection jdbConnection;
    private JSONArray listaDetalle;
    private ConexionDb conexion;
    private final String nameTable = "detalle_factura";

    public void conexionExterno(Connection jdbConnection) {
        this.jdbConnection = jdbConnection;
    }

    public void conectar() throws SQLException {
        /// obtener una instancia de la conexion a la base de datos
        conexion = ConexionDb.obtenerInstancia("root", "VictorQuiej135-");
        // obtener la conexion
        jdbConnection = conexion.obtenerConexion();
    }

    public long newDetalleFactura(BufferedReader datosEnviados) throws IOException, JSONException, SQLException {

        StringBuilder sb = new StringBuilder();
        String line;
        int filasAfectadas = 0;
        long idFactura = -1; // Inicializa el ID de la factura

        while ((line = datosEnviados.readLine()) != null) {
            sb.append(line);
        }

        JSONObject jsonDatos = new JSONObject(sb.toString());

        String sql = "INSERT INTO " + nameTable
                + "( id_factura, id_paquete) VALUES (?,?)";

        try (PreparedStatement statement = jdbConnection.prepareStatement(sql)) {

            statement.setString(1, jsonDatos.getString("id_factura"));
            statement.setString(2, jsonDatos.getString("id_paquete"));

            filasAfectadas = statement.executeUpdate();

            // Obtener el ID de la factura creada
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    idFactura = generatedKeys.getLong(1);
                } else {
                    throw new SQLException("No se pudo obtener el ID de la factura creada");
                }
            }

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
            // Devuelve el ID de la factura en lugar de un booleano
            return idFactura;
        }
    
        return -1; // Devuelve -1 si no se pudo crear la factura
    }

    public JSONArray getAllDetalleFactura() throws SQLException {
        String sqlScript = "SELECT * FROM " + nameTable;
        listaDetalle = new JSONArray();

        ///
        try (PreparedStatement statement = jdbConnection.prepareStatement(sqlScript);
                ResultSet resultSet = statement.executeQuery()) {

            /// obtener la lista de clientes y retornarlo
            listaDetalle = listarDetalleFactura(resultSet);

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

        return listaDetalle;
    }

    private JSONArray listarDetalleFactura(ResultSet resultSet) throws SQLException, JSONException {
        // Crear un JSONArray para almacenar los usuarios
        JSONArray listaDetalle = new JSONArray();

        while (resultSet.next()) {
            // Crear un JSONObject para cada usuario y añadirlo al JSONArray
            JSONObject detalle = new JSONObject();

            detalle.put("ID", resultSet.getString("id_detalle_factura"));
            detalle.put("ID Factura", resultSet.getString("id_factura"));
            detalle.put("ID Paquete", resultSet.getString("id_paquete"));

            listaDetalle.put(detalle);
        }

        return listaDetalle;
    }

}
