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

            paquete.put("ID", resultset.getString("id_paquete"));
            paquete.put("Cliente", resultset.getString("id_cliente"));
            paquete.put("Destino", resultset.getString("id_destino"));
            paquete.put("Ruta", resultset.getString("id_ruta"));
            paquete.put("Peso", resultset.getString("peso"));
            paquete.put("Descripcion", resultset.getString("descripcion"));
            paquete.put("Referencia", resultset.getString("referencia_destino"));
            paquete.put("Estado", resultset.getString("estado"));
            paquete.put("Fecha Entrada", resultset.getString("fecha_entrada"));
            paquete.put("Fecha Entrega", resultset.getString("fecha_entrega"));

            listPackage.put(paquete);
        }

        return listPackage;

    }

}
