package com.paqueExpres.DAO;

import java.sql.*;

import org.json.*;

import com.paqueExpres.util.ConexionDb;

public class PackageDAO {

    private Connection jdbcConexion;
    private JSONArray listPackage;
    private ConexionDb conexion;
    private final String nameTable = "paquete";

    public void conexionExterno(Connection jdbcConexion) {
        this.jdbcConexion = jdbcConexion;
    }

    public void conectar() throws SQLException {
        /// obtner una instacia de conexion
        conexion = ConexionDb.obtenerInstancia("root", "VictorQuiej135-");
        // obtener conexion:
        jdbcConexion = conexion.obtenerConexion();
    }

    public JSONArray getAllPackage() throws SQLException {
        String sqlSrcrip = "SELECT * FROM " + nameTable;
        listPackage = new JSONArray();

        try (PreparedStatement statement = jdbcConexion.prepareStatement(sqlSrcrip);
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

    public JSONArray getPackage(String columna, String condicion) throws SQLException {

        String sqlScrip = "SELECT * FROM " + nameTable + "WHERE ? = ?";

        try (PreparedStatement statement = jdbcConexion.prepareStatement(sqlScrip)) {

            statement.setString(1, columna);
            statement.setString(2, condicion);

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

    private JSONArray listarPackge(ResultSet resultset) throws SQLException {

        /// crear un JSONArray para almacenar los paquetes
        JSONArray listPackage = new JSONArray();

        // bucle para ordnear datos:

        while (resultset.next()) {
            /// Crar un JSONObject para cada paquete y añadirlo al JSONArray
            JSONObject paquete = new JSONObject();

            paquete.put("id", resultset.getString("id_paquete"));
            paquete.put("cliente", resultset.getString("id_cliente"));
            paquete.put("destino", resultset.getString("id_destino"));
            paquete.put("ruta", resultset.getString("id_ruta"));
            paquete.put("peso", resultset.getString("peso"));
            paquete.put("descripcion", resultset.getString("descripcion"));
            paquete.put("referencia", resultset.getString("referencia_destino"));
            paquete.put("fechaEntrada", resultset.getString("fecha_entrada"));
            paquete.put("fechaEntrega", resultset.getString("fecha_entrega"));
            
            paquete.put("estado", resultset.getString("estado"));

            listPackage.put(paquete);
        }

        return listPackage;

    }

}
