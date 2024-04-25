package com.paqueExpres.DAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;

import org.json.*;

import com.paqueExpres.util.ConexionDb;

public class RegistroPuntoDAO {

    private Connection jdbConnection;
    private JSONArray listaClientes;
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

    public JSONArray getAllCliente() throws SQLException {
        String sqlScript = "SELECT * FROM " + nameTable;
        listaClientes = new JSONArray();

        ///
        try (PreparedStatement statement = jdbConnection.prepareStatement(sqlScript);
                ResultSet resultSet = statement.executeQuery()) {

            /// obtener la lista de clientes y retornarlo
            listaClientes = listarClientes(resultSet);

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

        return listaClientes;
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
                + "(id_paquete, id_punto_control, id_ruta, horas_acumuladas, costo_generado, fecha_entrada. fecha_salida, id_usuario ) VALUES (?,?,?,?,?,?,?,?)";

        try (PreparedStatement statement = jdbConnection.prepareStatement(sql)) {

            statement.setString(1, jsonDatos.getString("id_paquete"));
            statement.setString(2, jsonDatos.getString("id_punto_control"));
            statement.setString(3, jsonDatos.getString("id_rua"));
            statement.setString(4, jsonDatos.getString("horas_acumulada"));
            statement.setString(5, jsonDatos.getString("costo_generado"));
            statement.setString(6, jsonDatos.getString("fecha_entrada"));
            statement.setString(7, jsonDatos.getString("fecha_salida"));
            statement.setString(8, jsonDatos.getString("id_usuario"));

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

    private JSONArray listarClientes(ResultSet resultSet) throws SQLException, JSONException {
        // Crear un JSONArray para almacenar los usuarios
        JSONArray listaClientes = new JSONArray();

        while (resultSet.next()) {
            // Crear un JSONObject para cada usuario y añadirlo al JSONArray
            JSONObject rgPunto = new JSONObject();

            rgPunto.put("ID", resultSet.getString("id_cliente"));
            rgPunto.put("ID paquete", resultSet.getString("nombre"));
            rgPunto.put("ID punto Control", resultSet.getString("apellido"));
            rgPunto.put("ID ruta", resultSet.getString("direccion"));
            rgPunto.put("Horas acumuladas", resultSet.getString("genero"));
            rgPunto.put("Costo generado", resultSet.getString("telefono"));
            rgPunto.put("Fecha entrada", resultSet.getString("telefono"));
            rgPunto.put("Fecha salida", resultSet.getString("telefono"));
            rgPunto.put("ID usuario", resultSet.getString("id_usuario"));

            listaClientes.put(rgPunto);
        }

        return listaClientes;
    }

}
