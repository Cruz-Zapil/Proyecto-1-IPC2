package com.paqueExpres.DAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;

import org.json.*;

import com.paqueExpres.util.ConexionDb;

public class PackageDAO {

    private Connection jdbConexion;
    private JSONArray listPackage;
    private ConexionDb conexion;
    private final String nameTable = "paquete";

    public void conexionExterno(Connection jdbcConexion) {
        this.jdbConexion = jdbcConexion;
    }

    public void conectar() throws SQLException {
        /// obtner una instacia de conexion
        conexion = ConexionDb.obtenerInstancia("root", "VictorQuiej135-");
        // obtener conexion:
        jdbConexion = conexion.obtenerConexion();
    }

    public JSONArray getAllPackage() throws SQLException {
        String sqlSrcrip = "SELECT * FROM " + nameTable;
        listPackage = new JSONArray();

        try (PreparedStatement statement = jdbConexion.prepareStatement(sqlSrcrip);
                ResultSet resultset = statement.executeQuery()) {

            /// Obtener la lista de paquetes
            listPackage = listarPackge(resultset);
        } finally {
            try {
                /// cerando la conexion a la base de datos
                conexion.cerrarConexion();
            } catch (SQLException e) {
                e.printStackTrace();

            }
        }

        return listPackage;
    }

    public JSONArray getPackage(String condicion) throws SQLException {

        String sqlScrip = "SELECT * FROM " + nameTable + " WHERE id_paquete = ?";

        try (PreparedStatement statement = jdbConexion.prepareStatement(sqlScrip)) {

            statement.setString(1, condicion);

            ResultSet resultSet = statement.executeQuery();

            /// obtener la lista de clientes
            listPackage = listarPackge(resultSet);

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

        return listPackage;
    }

    public boolean newPackage(BufferedReader datosEnviados) throws IOException, JSONException, SQLException {

        StringBuilder sb = new StringBuilder();
        String line;
        int filasAfectadas = 0;

        while ((line = datosEnviados.readLine()) != null) {
            sb.append(line);
        }

        JSONObject jsonDatos = new JSONObject(sb.toString());

        String sql = "INSERT INTO " + nameTable
                + "(id_cliente ,id_destino, id_ruta, peso, descripcion, referencia_desino, estado, fecha_entrada,fecha_entrega ) VALUES (?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement statement = jdbConexion.prepareStatement(sql)) {

            statement.setString(1, jsonDatos.getString("id_cliente"));
            statement.setString(2, jsonDatos.getString("id_destino"));
            statement.setString(3, jsonDatos.getString("id_ruta"));
            statement.setString(4, jsonDatos.getString("peso"));
            statement.setString(5, jsonDatos.getString("descripcion"));
            statement.setString(6, jsonDatos.getString("referencia_destino"));
            statement.setString(7, jsonDatos.getString("estado"));
            statement.setString(8, jsonDatos.getString("fecha_entrada"));
            statement.setString(9, jsonDatos.getString("fecha_entrega"));

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

    /// update usuario

    public boolean updatePackage(BufferedReader datosEviados) throws IOException, SQLException {

        StringBuilder sb = new StringBuilder();
        String line;
        int filasAfectads = 0;

        while ((line = datosEviados.readLine()) != null) {
            sb.append(line);
        }

        JSONObject jsonData = new JSONObject(sb.toString());

        String sql = "UPDATE " + nameTable
                + " SET id_cliente = ?, id_destino = ?, id_ruta=?, peso=?, descripcion=?, referencia_destino=?, estado=?, fecha_entrada=?, fecha_entrega=?  WHERE id_paquete =? ";

        try (PreparedStatement statement = jdbConexion.prepareStatement(sql)) {

            statement.setString(1, jsonData.getString("id_cliente"));
            statement.setString(2, jsonData.getString("id_destino"));
            statement.setString(3, jsonData.getString("id_ruta"));
            statement.setString(4, jsonData.getString("peso"));
            statement.setString(5, jsonData.getString("descripcion"));
            statement.setString(6, jsonData.getString("referencia_destino"));
            statement.setString(7, jsonData.getString("estado"));
            statement.setString(8, jsonData.getString("fecha_entrada"));
            statement.setString(9, jsonData.getString("fecha_entrega"));

            filasAfectads = statement.executeUpdate();

            // cerrando recursos
            statement.close();

        } finally {
            try {

                conexion.cerrarConexion();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (filasAfectads > 0) {
            return true;
        }
        return false;
    }

    private JSONArray listarPackge(ResultSet resultset) throws SQLException {

        /// crear un JSONArray para almacenar los paquetes
        JSONArray listPackage = new JSONArray();

        // bucle para ordnear datos:

        while (resultset.next()) {
            /// Crar un JSONObject para cada paquete y añadirlo al JSONArray
            JSONObject paquete = new JSONObject();

            paquete.put("id_paquete", resultset.getString("id_paquete"));
            paquete.put("id_cliente", resultset.getString("id_cliente"));
            paquete.put("id_destino", resultset.getString("id_destino"));
            paquete.put("id_ruta", resultset.getString("id_ruta"));
            paquete.put("peso", resultset.getString("peso"));
            paquete.put("descripcion", resultset.getString("descripcion"));
            paquete.put("referencia", resultset.getString("referencia_destino"));
            paquete.put("fecha_entrada", resultset.getString("fecha_entrada"));
            paquete.put("fecha_salida", resultset.getString("fecha_entrega"));

            // Obtener el estado del usuario
            int estadoInt = resultset.getInt("estado");
            String estado = (estadoInt == 1) ? "activo" : "inactivo";
            paquete.put("estado", estado);

            listPackage.put(paquete);
        }

        return listPackage;

    }

}
