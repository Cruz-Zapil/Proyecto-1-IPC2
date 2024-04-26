package com.paqueExpres.DAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.paqueExpres.util.ConexionDb;

public class RutaDAO {

    private Connection jdbConexion;
    private JSONArray listaRutas;
    private ConexionDb conexion;
    private final String nameTable = "ruta";

    public void conectar() throws SQLException {

        /// Se obiene una instacia de la conesion a la base de datos
        conexion = ConexionDb.obtenerInstancia("root", "VictorQuiej135-");

        /// Obtener conexion
        jdbConexion = conexion.obtenerConexion();

    }

    public JSONArray getAllRutas() throws SQLException {

        String sql = "SELECT * FROM " + nameTable;
        listaRutas = new JSONArray();

        try (PreparedStatement statement = jdbConexion.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            // obtener la lista de usuario
            listaRutas = listarRutas(resultSet);

            // cerrabdi recursos

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

        return listaRutas;

    }



    public JSONArray getPackage(String columna, String condicion) throws SQLException {

        String sqlScrip = "SELECT * FROM " + nameTable + "WHERE ? = ?";

        try (PreparedStatement statement = jdbConexion.prepareStatement(sqlScrip)) {

            statement.setString(1, columna);
            statement.setString(2, condicion);

            ResultSet resultSet = statement.executeQuery();

            /// obtener la lista de clientes
            listaRutas = listarRutas(resultSet);

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

        return listaRutas;
    }



    public boolean newRuta(BufferedReader datosEnviados) throws JSONException, SQLException, IOException {

        StringBuilder sb = new StringBuilder();
        String line;
        int filasAfectadas = 0;

        while ((line = datosEnviados.readLine()) != null) {

            sb.append(line);
        }
        JSONObject jsonDatos = new JSONObject(sb.toString());

        String sqlScript = "INSERT INTO " + nameTable
                + "(carretera, estado, id_destino, limite_carga) VALUES (?,?,?,?)";

        try (PreparedStatement statement = jdbConexion.prepareStatement(sqlScript)) {
            statement.setString(1, jsonDatos.getString("carretera"));
            statement.setString(2, jsonDatos.getString("estado"));
            statement.setString(3, jsonDatos.getString("id_destino"));
            statement.setString(4, jsonDatos.getString("limite_carga"));

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

    private JSONArray listarRutas(ResultSet resultSet) throws JSONException, SQLException {
        /// un objeto en json para ordernar las rutas
        JSONArray listaRutas = new JSONArray();

        while (resultSet.next()) {
            /// un objeto json para almacenar cada atributo de ruta
            JSONObject ruta = new JSONObject();

            ruta.put("id_ruta", resultSet.getString("id_ruta"));
            ruta.put("carretera", resultSet.getString("carretera"));
            // Obtener el estado del usuario
            int estadoInt = resultSet.getInt("estado");
            String estado = (estadoInt == 1) ? "activo" : "inactivo";
            ruta.put("estado", estado);

            ruta.put("destino", resultSet.getString("id_destino"));
            ruta.put("limite", resultSet.getString("limite_carga"));

        }
        return listaRutas;

    }

}
