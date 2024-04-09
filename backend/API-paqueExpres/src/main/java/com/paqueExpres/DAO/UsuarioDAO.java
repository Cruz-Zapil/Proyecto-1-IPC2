package com.paqueExpres.DAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONObject;
import com.paqueExpres.util.ConexionDb;

public class UsuarioDAO {

    private Connection jdbcConexion;
    private JSONArray listaClientes;
    private ConexionDb conexion;
    private final String nameTable = "usuario";

    public void conectar() throws SQLException {

        // Obtener una instancia de la conexión a la base de datos

        conexion = (ConexionDb) ConexionDb.obtenerInstancia("root", "VictorQuiej135-");
        // Obtener la conexión
        jdbcConexion = conexion.obtenerConexion();
    }

    public JSONArray obtenerTodosLosUsuarios() throws SQLException {
        String sqlScript = "SELECT * FROM " + nameTable;
        listaClientes = new JSONArray();
        try (PreparedStatement statement = jdbcConexion.prepareStatement(sqlScript);
                ResultSet resultSet = statement.executeQuery()) {

            // Obtener la lista de usuarios y retornarla
            listaClientes = listarUsuarios(resultSet);

            // cerrando recursos
            statement.close();
            resultSet.close();
        }
        return listaClientes;
    }

    public JSONArray obtenerUsuarioEspecifico(String columna, String condicion) throws SQLException {
        String sql = "SELECT * FROM " + nameTable + " WHERE ? = ?";
        listaClientes = new JSONArray();
        try (PreparedStatement statement = jdbcConexion.prepareStatement(sql)) {
            statement.setString(1, columna);
            statement.setString(2, condicion);

            try (ResultSet resultSet = statement.executeQuery()) {

                // Obtener la lista de usuarios y retornarla
                listaClientes = listarUsuarios(resultSet);

                // cerrando recursos
                statement.close();
                resultSet.close();
            }

        } finally {
            try {
                // Cerrar la conexión con la base de datos
                conexion.cerrarConexion();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listaClientes;
    }

    public JSONObject loggin(String username, String password) throws SQLException {

        String sql = "SELECT * FROM " + nameTable + " WHERE username = ? AND password_has = ? ";
        JSONObject user = new JSONObject();

        try (PreparedStatement statement = jdbcConexion.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {

                /// acceder a los resultados
                if (resultSet.next()) {

                    user.put("ID", resultSet.getString("id_usuario"));
                    user.put("Nombre", resultSet.getString("nombre"));
                    user.put("Apellido", resultSet.getString("apellido"));
                    user.put("Estado", resultSet.getString("estado"));
                    user.put("ID rol", resultSet.getString("id_rol"));
                    user.put("Teléfono", resultSet.getString("telefono"));
                    user.put("Edad", resultSet.getString("edad"));

                } else {

                    // Si no se encontraron resultados
                    user.put("verifique sus datos", "Usuario no encontrado");
                }

                // cerrando recursos
                statement.close();
                resultSet.close();

            }

        } finally {
            try {

                conexion.cerrarConexion();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return user;

    }

    /// update usuario

    public boolean updateUser(BufferedReader datosEviados) throws IOException, SQLException {

        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = datosEviados.readLine()) != null) {
            sb.append(line);
        }

        JSONObject jsonData = new JSONObject(sb.toString());

        String sql = "UPDATE " + nameTable
                + " SET nombre = ?, apellido = ?, estado=?, id_rol=?, genero=?, telefono=?, edad=? WHERE id_usuario =? ";

        try (PreparedStatement statement = jdbcConexion.prepareStatement(sql)) {

            statement.setString(1, jsonData.getString("nombre"));
            statement.setString(2, jsonData.getString("apellido"));
            statement.setString(3, jsonData.getString("estado"));
            statement.setString(4, jsonData.getString("id_rol"));
            statement.setString(5, jsonData.getString("genero"));
            statement.setString(6, jsonData.getString("telefono"));
            statement.setString(7, jsonData.getString("edad"));
            statement.setString(8, jsonData.getString("id_usuario"));

            int filasAfectads = statement.executeUpdate();

            // cerrando recursos
            statement.close();

            if (filasAfectads > 0) {
                return true;
            }

        } finally {
            try {

                conexion.cerrarConexion();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    private JSONArray listarUsuarios(ResultSet resultSet) throws SQLException {
        // Crear un JSONArray para almacenar los usuarios
        JSONArray listaClientes = new JSONArray();

        while (resultSet.next()) {
            // Crear un JSONObject para cada usuario y añadirlo al JSONArray
            JSONObject usuario = new JSONObject();

            usuario.put("ID", resultSet.getString("id_usuario"));
            usuario.put("Nombre", resultSet.getString("nombre"));
            usuario.put("Apellido", resultSet.getString("apellido"));
            usuario.put("Username", resultSet.getString("username"));
            usuario.put("Estado", resultSet.getString("estado"));
            usuario.put("ID_rol", resultSet.getString("id_rol"));
            usuario.put("Género", resultSet.getString("genero"));
            usuario.put("Teléfono", resultSet.getString("telefono"));
            usuario.put("Edad", resultSet.getString("edad"));

            listaClientes.put(usuario);
        }
        return listaClientes;
    }

}
