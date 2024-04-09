package com.paqueExpres.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.paqueExpres.util.ConexionDB;

public class UsuarioDAO {

    String columna;
    String condicion;
    Connection jdbcConexion;
    ConexionDB conexion;
    JSONArray listaClientes;

    public UsuarioDAO(String columna, String condicion) {
        this.columna = columna;
        this.condicion = condicion;

    }

    public void conexion() {
        // realizar conexion:
        conexion = new ConexionDB("root", "VictorQuiej135-");

        try {

            // conectamos
            conexion.connection();

            /// obntenemos conexion
            jdbcConexion = conexion.getJdbcConnection();

        } catch (SQLException e) {
            e.printStackTrace();

        }

    }

    public JSONArray getAllUser() throws SQLException {

        /// formulamos consulta sql
        String sqlScript = "SELECT * FROM = usuarios";

        /// lista de personas
        listaClientes = new JSONArray();

        /// ingresamos script
        try {

            PreparedStatement statement = jdbcConexion.prepareStatement(sqlScript);

            ResultSet resultSet = statement.executeQuery();

            listaClientes = listUser(resultSet);

            // cerrando recursos
            resultSet.close();
            statement.close();

        } finally {
            /// cerrando conexion con la DB
            try {
                conexion.descconect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return listaClientes;

    }

    public JSONArray getSepcificUser(String condition) throws SQLException {

        // formulamos consula sql
        String sql = "SELECT * FROM usuarios WHERE ? = ?";

        /// lista de personas
        listaClientes = new JSONArray();

        /// ingresamos script
        try {

            PreparedStatement statement = jdbcConexion.prepareStatement(sql);
            statement.setString(1, columna);
            statement.setString(2, condition);

            ResultSet resultSet = statement.executeQuery();

            listaClientes = listUser(resultSet);

            // cerrando recursos
            resultSet.close();
            statement.close();

        } finally {
            /// cerrando conexion con la DB
            try {
                conexion.descconect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return listaClientes;

    }

    public JSONArray listUser(ResultSet resultSet) throws JSONException, SQLException {

        JSONArray listaClientes = new JSONArray();

        while (resultSet.next()) {
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
