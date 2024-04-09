package com.paqueExpres.DAO;

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

    public void conectar() throws SQLException {

        // Obtener una instancia de la conexión a la base de datos

        conexion = (ConexionDb) ConexionDb.obtenerInstancia("root", "VictorQuiej135-");
        // Obtener la conexión
        jdbcConexion = conexion.obtenerConexion();
    }

    public JSONArray obtenerTodosLosUsuarios() throws SQLException {
        String sqlScript = "SELECT * FROM usuario";
        listaClientes = new JSONArray();
        try (PreparedStatement statement = jdbcConexion.prepareStatement(sqlScript);
                ResultSet resultSet = statement.executeQuery()) {
            // Obtener la lista de usuarios y retornarla
            listaClientes = listarUsuarios(resultSet);
        }
        return listaClientes;
    }

    public JSONArray obtenerUsuarioEspecifico(String columna, String condicion) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE ? = ?";
        listaClientes = new JSONArray();
        try (PreparedStatement statement = jdbcConexion.prepareStatement(sql)) {
            statement.setString(1, columna);
            statement.setString(2, condicion);
            try (ResultSet resultSet = statement.executeQuery()) {

                // Obtener la lista de usuarios y retornarla
                listaClientes = listarUsuarios(resultSet);
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

    private JSONArray listarUsuarios(ResultSet resultSet) throws SQLException {
        // Crear un JSONArray para almacenar los usuarios
        JSONArray listaClientes = new JSONArray();

        while (resultSet.next()) {
            // Crear un JSONObject para cada usuario y añadirlo al JSONArray
            JSONObject usuario = new JSONObject();

            usuario.put("id", resultSet.getString("id_usuario"));
            usuario.put("nombre", resultSet.getString("nombre"));
            usuario.put("apellido", resultSet.getString("apellido"));
            usuario.put("username", resultSet.getString("username"));
            usuario.put("estado", resultSet.getString("estado"));
            usuario.put("id_rol", resultSet.getString("id_rol"));
            usuario.put("género", resultSet.getString("genero"));
            usuario.put("teléfono", resultSet.getString("telefono"));
            usuario.put("edad", resultSet.getString("edad"));

            listaClientes.put(usuario);
        }
        return listaClientes;
    }


}
