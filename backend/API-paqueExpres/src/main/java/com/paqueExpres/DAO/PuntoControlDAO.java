package com.paqueExpres.DAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;

import org.json.*;

import com.paqueExpres.util.ConexionDb;

public class PuntoControlDAO {

    private Connection jdbConnection;
    private JSONArray listaPuntoControl;
    private ConexionDb conexion;
    private final String nameTable = "punto_control";

    public void conexionExterno(Connection jdbConnection) {
        this.jdbConnection = jdbConnection;
    }

    public void conectar() throws SQLException {
        /// obtener una instancia de la conexion a la base de datos
        conexion = ConexionDb.obtenerInstancia("root", "VictorQuiej135-");
        // obtener la conexion
        jdbConnection = conexion.obtenerConexion();
    }

    public JSONArray getAllPunto() throws SQLException {
        String sqlScript = "SELECT * FROM " + nameTable;
        listaPuntoControl = new JSONArray();

        ///
        try (PreparedStatement statement = jdbConnection.prepareStatement(sqlScript);
                ResultSet resultSet = statement.executeQuery()) {

            /// obtener la lista de clientes y retornarlo
            listaPuntoControl = listarPuntoControl(resultSet);

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

        return listaPuntoControl;
    }

    public JSONArray getPuntoControl(BufferedReader datosEnviados) throws SQLException, IOException {

        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = datosEnviados.readLine()) != null) {
            sb.append(line);
        }

        JSONObject jsonDatos = new JSONObject(sb.toString());

        String columna1 = jsonDatos.getString("columna1");
        String condicion1 = jsonDatos.getString("condicion1");
        String columna2 = jsonDatos.getString("columna2");
        String condicion2 = jsonDatos.getString("condicion2");

        String sqlScrip = "SELECT * FROM " + nameTable + "WHERE ? = ? AND ? = ? ";

        try (PreparedStatement statement = jdbConnection.prepareStatement(sqlScrip)) {

            statement.setString(1, columna1);
            statement.setString(2, condicion1);
            statement.setString(3, columna2);
            statement.setString(4, condicion2);

            ResultSet resultSet = statement.executeQuery();
            listaPuntoControl = listarPuntoControl(resultSet);


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

        return listaPuntoControl;
    }

    public boolean newPuntoControl(BufferedReader datosEnviados) throws IOException, JSONException, SQLException {

        StringBuilder sb = new StringBuilder();
        String line;
        int filasAfectadas = 0;

        while ((line = datosEnviados.readLine()) != null) {
            sb.append(line);
        }

        JSONObject jsonDatos = new JSONObject(sb.toString());

        String sql = "INSERT INTO " + nameTable
                + "(id_ruta,limite,tarifa_local,id_tarifa_global, id_usuario) VALUES (?,?,?,?,?)";

        try (PreparedStatement statement = jdbConnection.prepareStatement(sql)) {

            statement.setString(1, jsonDatos.getString("id_ruta"));
            statement.setString(2, jsonDatos.getString("limite_paquete"));
            statement.setString(3, jsonDatos.getString("tarifa_local"));
            statement.setString(4, jsonDatos.getString("id_tarifa_global"));
            statement.setString(5, jsonDatos.getString("id_usuario"));

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

    private JSONArray listarPuntoControl(ResultSet resultSet) throws SQLException, JSONException {
        // Crear un JSONArray para almacenar los usuarios
        JSONArray listaPunto = new JSONArray();

        while (resultSet.next()) {
            // Crear un JSONObject para cada usuario y añadirlo al JSONArray
            JSONObject puntoControl = new JSONObject();

            puntoControl.put("id_punto", resultSet.getString("id_punto_control"));
            puntoControl.put("id_ruta", resultSet.getString("id_ruta"));
            puntoControl.put("limite_paquete", resultSet.getString("limite_paquete"));
            puntoControl.put("tarifa_local", resultSet.getString("moneda"));
            puntoControl.put("id_tarifa_global", resultSet.getString("tarifa_local"));
            puntoControl.put("id_usuario", resultSet.getString("id_tarifa_global"));

            listaPunto.put(puntoControl);
        }

        return listaPunto;
    }

}
