package com.paqueExpres.DAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;

import org.json.*;

import com.paqueExpres.util.ConexionDb;

public class RegistroPuntoDAO {

    private Connection jdbConnection;
    private JSONArray listaRegistro;
    private ConexionDb conexion;
    private final String nameTable = "registro_punto_control";

    public void conexionExterno(Connection jdbConnection) {
        this.jdbConnection = jdbConnection;
    }

    public void conectar() throws SQLException {
        /// obtener una instancia de la conexion a la base de datos
        conexion = ConexionDb.obtenerInstancia("root", "VictorQuiej135-");
        // obtener la conexion
        jdbConnection = conexion.obtenerConexion();
    }

    public JSONArray getAllRegistro() throws SQLException {
        String sqlScript = "SELECT * FROM " + nameTable;
        listaRegistro = new JSONArray();

        ///
        try (PreparedStatement statement = jdbConnection.prepareStatement(sqlScript);
                ResultSet resultSet = statement.executeQuery()) {

            /// obtener la lista de clientes y retornarlo
            listaRegistro = listarRegistro(resultSet);

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

        return listaRegistro;
    }

    public JSONArray getRegistroPunto(String comlumna, String condicion) throws SQLException, IOException {

        String sqlScrip = "SELECT * FROM " + nameTable + " WHERE ? = ? ";

        try (PreparedStatement statement = jdbConnection.prepareStatement(sqlScrip)) {

            statement.setString(1, comlumna);
            statement.setString(2, condicion);

            ResultSet resultSet = statement.executeQuery();
            listaRegistro = listarRegistro(resultSet);

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

        return listaRegistro;
    }

    public JSONArray getPaqueteRegistro(String condicion) throws SQLException, IOException {

        String sqlScrip = "SELECT * FROM " + nameTable + " WHERE id_paquete = ? ";

        try (PreparedStatement statement = jdbConnection.prepareStatement(sqlScrip)) {

            statement.setString(1, condicion);

            ResultSet resultSet = statement.executeQuery();
            listaRegistro = listarRegistro(resultSet);

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

        return listaRegistro;
    }

    public boolean newRegistroPunto(BufferedReader datosEnviados) throws IOException, JSONException, SQLException {

        StringBuilder sb = new StringBuilder();
        String line;
        int filasAfectadas = 0;

        while ((line = datosEnviados.readLine()) != null) {
            sb.append(line);
        }

        JSONObject jsonDatos = new JSONObject(sb.toString());

        String sql = "INSERT INTO " + nameTable
                + "(id_paquete, id_punto_control,fecha_entrada ) VALUES (?,?,?)";

        try (PreparedStatement statement = jdbConnection.prepareStatement(sql)) {

            statement.setString(1, jsonDatos.getString("id_paquete"));
            statement.setString(2, jsonDatos.getString("id_punto_control"));
            statement.setString(3, jsonDatos.getString("fecha_entrada"));

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

    public boolean updateRegistroPunto(BufferedReader datosEviados) throws IOException, SQLException {

        StringBuilder sb = new StringBuilder();
        String line;
        int filasAfectadas = 0;

        while ((line = datosEviados.readLine()) != null) {
            sb.append(line);
        }

        JSONObject jsonData = new JSONObject(sb.toString());

        String sql = "UPDATE " + nameTable
                + " SET id_paquete= ?, id_punto_control = ?, horas_acumuladas=?, costo_generado=?, fecha_entrada=?, fecha_salida=? WHERE id_registro_punto =? ";

        try (PreparedStatement statement = jdbConnection.prepareStatement(sql)) {

            statement.setString(1, jsonData.getString("id_paquete"));
            statement.setString(2, jsonData.getString("id_punto_control"));
            statement.setInt(3, jsonData.getInt("horas_acumuladas"));
            statement.setInt(4, jsonData.getInt("costo_generado"));
            statement.setString(5, jsonData.getString("fecha_entrada"));
            statement.setString(6, jsonData.getString("fecha_salida"));
            statement.setString(7, jsonData.getString("id_punto_registro"));

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

    private JSONArray listarRegistro(ResultSet resultSet) throws SQLException, JSONException {
        // Crear un JSONArray para almacenar los usuarios
        JSONArray listaClientes = new JSONArray();

        while (resultSet.next()) {
            // Crear un JSONObject para cada usuario y añadirlo al JSONArray
            JSONObject rgPunto = new JSONObject();

            rgPunto.put("id_registro", resultSet.getString("id_registro_punto"));
            rgPunto.put("id_paquete", resultSet.getString("id_paquete"));
            rgPunto.put("id_punto_control", resultSet.getString("id_punto_control"));
            rgPunto.put("horas_acumuladas", resultSet.getInt("horas_acumuladas"));
            rgPunto.put("costo_generado", resultSet.getInt("costo_generado"));
            rgPunto.put("fecha_entrada", resultSet.getString("fecha_entrada"));
            rgPunto.put("fecha_salida", resultSet.getString("fecha_salida"));

            listaClientes.put(rgPunto);
        }

        return listaClientes;
    }

}
