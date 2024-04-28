package com.paqueExpres.DAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;
import org.json.*;
import com.paqueExpres.util.ConexionDb;

public class UsuarioDAO {

    private Connection jdbcConexion;
    private JSONArray listaUsuario;
    private ConexionDb conexion;
    private final String nameTable = "usuario";

    public void conexionExterno(Connection jdbcConexion) {
        this.jdbcConexion = jdbcConexion;
    }

    public void conectar() throws SQLException {

        // Obtener una instancia de la conexión a la base de datos
        conexion = ConexionDb.obtenerInstancia("root", "VictorQuiej135-");
        // Obtener la conexión
        jdbcConexion = conexion.obtenerConexion();
    }

    public JSONArray getAllUser() throws SQLException {
        String sqlScript = "SELECT * FROM " + nameTable;
        listaUsuario = new JSONArray();
        try (PreparedStatement statement = jdbcConexion.prepareStatement(sqlScript);
                ResultSet resultSet = statement.executeQuery()) {

            // Obtener la lista de usuarios y retornarla
            listaUsuario = listarUsuarios(resultSet);

            // cerrando recursos
            statement.close();
            resultSet.close();
        } finally {
            try {
                // Cerrar la conexión con la base de datos
                conexion.cerrarConexion();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listaUsuario;
    }

    public JSONArray obtenerUsuarioEspecifico(String columna, String condicion) throws SQLException {
        String sql = "SELECT * FROM " + nameTable + " WHERE ? = ?";
        listaUsuario = new JSONArray();
        try (PreparedStatement statement = jdbcConexion.prepareStatement(sql)) {
            statement.setString(1, columna);
            statement.setString(2, condicion);

            try (ResultSet resultSet = statement.executeQuery()) {

                // Obtener la lista de usuarios y retornarla
                listaUsuario = listarUsuarios(resultSet);

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
        return listaUsuario;
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

                    user.put("success", true);
                    user.put("messege", "Bienvenido");
                    user.put("id", resultSet.getString("id_usuario"));
                    user.put("nombre", resultSet.getString("nombre"));
                    user.put("apellido", resultSet.getString("apellido"));
                    user.put("id_rol", resultSet.getString("id_rol"));
                    user.put("teléfono", resultSet.getString("telefono"));
                    user.put("edad", resultSet.getString("edad")); 

                    // Obtener el estado del usuario
                    int estadoInt = resultSet.getInt("estado");
                    boolean estado = (estadoInt == 1) ? true : false;
                    user.put("estado", estado);

                } else {

                    // Si no se encontraron resultados
                    user.put("success", false);
                    user.put("messege", "Usuario no encontrado");
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
        int filasAfectads = 0;

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

    public boolean newUser(BufferedReader datosEnviados) throws IOException, JSONException, SQLException {

        StringBuilder sb = new StringBuilder();
        String line;
        int filasAfectadas = 0;

        while ((line = datosEnviados.readLine()) != null) {
            sb.append(line);
        }

        JSONObject jsonDatos = new JSONObject(sb.toString());

        String sql = "INSERT INTO " + nameTable
                + "(nombre, apellido, username, password_has, estado, id_rol, genero, telefono, edad) VALUES (?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement statement = jdbcConexion.prepareStatement(sql)) {

            statement.setString(1, jsonDatos.getString("nombre"));
            statement.setString(2, jsonDatos.getString("apellido"));
            statement.setString(3, jsonDatos.getString("username"));
            statement.setString(4, jsonDatos.getString("password_has"));
            statement.setString(5, jsonDatos.getString("estado"));
            statement.setString(6, jsonDatos.getString("id_rol"));
            statement.setString(7, jsonDatos.getString("genero"));
            statement.setString(8, jsonDatos.getString("telefono"));
            statement.setString(9, jsonDatos.getString("edad"));

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

    private JSONArray listarUsuarios(ResultSet resultSet) throws SQLException {
        // Crear un JSONArray para almacenar los usuarios
        JSONArray listaClientes = new JSONArray();
        RolDAO tmp = new RolDAO();
        tmp.conexionExterno(jdbcConexion);

        while (resultSet.next()) {
            // Crear un JSONObject para cada usuario y añadirlo al JSONArray
            JSONObject usuario = new JSONObject();

            usuario.put("id", resultSet.getString("id_usuario"));
            usuario.put("nombre", resultSet.getString("nombre"));
            usuario.put("apellido", resultSet.getString("apellido"));
            usuario.put("username", resultSet.getString("username"));

            // Obtener el estado del usuario
            int estadoInt = resultSet.getInt("estado");
            String estado = (estadoInt == 1) ? "activo" : "inactivo";
            usuario.put("estado", estado);

            String tmpCol = tmp.getCol("nombre", resultSet.getString("id_rol"));
            usuario.put("rol", tmpCol);

            usuario.put("genero", resultSet.getString("genero"));
            usuario.put("telefono", resultSet.getString("telefono"));
            usuario.put("edad", resultSet.getString("edad"));

            listaClientes.put(usuario);
        }

        return listaClientes;
    }

}
