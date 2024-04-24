package com.paqueExpres.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;

import com.paqueExpres.util.ConexionDb;

public class RolDAO {

    private Connection jdbcConexion;
    private JSONArray listaRol;
    private ConexionDb conexion;
    private final String nameTable = "rol";

    public void conexionExterno(Connection jdbcConexion) {
        this.jdbcConexion = jdbcConexion;
    }

    public void conectar() throws SQLException {

        // Obtener una instancia de la conexión a la base de datos
        conexion = ConexionDb.obtenerInstancia("root", "VictorQuiej135-");
        // Obtener la conexión
        jdbcConexion = conexion.obtenerConexion();
    }

   

    public String getCol(String nameCol, String condition) throws SQLException {
        String sqlScrip = "SELECT " + nameCol + " FROM " + nameTable + " WHERE id_rol = ?";
        String result = null;
        try (PreparedStatement statement = jdbcConexion.prepareStatement(sqlScrip)) {

            statement.setString(1, condition);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    result = resultSet.getString(nameCol);
                }
                statement.cancel();
            }
        }
        return result;
    }

}
