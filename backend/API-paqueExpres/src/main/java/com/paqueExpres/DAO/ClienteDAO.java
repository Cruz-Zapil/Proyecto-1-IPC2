package com.paqueExpres.DAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;

import org.json.*;

import com.paqueExpres.util.ConexionDb;

public class ClienteDAO {

    private Connection jdbConnection;
    private JSONArray listaClientes;
    private ConexionDb conexion;
    private final String nameTable = "cliente";

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

    public boolean newCliente(BufferedReader datosEnviados) throws IOException, JSONException, SQLException {

        StringBuilder sb = new StringBuilder();
        String line;
        int filasAfectadas = 0;

        while ((line = datosEnviados.readLine()) != null) {
            sb.append(line);
        }

        JSONObject jsonDatos = new JSONObject(sb.toString());

        String sql = "INSERT INTO " + nameTable
                + "(id_cliente,nombre, apellido, direccion, genero,telefono) VALUES (?,?,?,?,?,?)";

        try (PreparedStatement statement = jdbConnection.prepareStatement(sql)) {

            statement.setString(1, jsonDatos.getString("id_cliente"));
            statement.setString(2, jsonDatos.getString("nombre"));
            statement.setString(3, jsonDatos.getString("apellido"));
            statement.setString(4, jsonDatos.getString("direccion"));
            statement.setString(5, jsonDatos.getString("genero"));
            statement.setString(6, jsonDatos.getString("telefono"));

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

    public boolean checkClient(String idCliente) throws SQLException {

        boolean tmpEstado=false;

        String sql = "SELECT id_cliente FROM " + nameTable + " WHERE id_cliene =?";
        try (PreparedStatement statement = jdbConnection.prepareStatement(sql)) {
            statement.setString(1, idCliente);

            try (ResultSet resultSet = statement.executeQuery()) {

                // Si la consulta devuelve algún resultado, significa que el cliente existe
                // Por lo tanto, retornamos true
                if (resultSet.next()) {
                    tmpEstado = true;
                } else {
                    // Si la consulta no devuelve ningún resultado, significa que el cliente no
                    // existe
                    // Por lo tanto, retornamos false
                    tmpEstado= false;
                }
                resultSet.close();      
                statement.close();
            }   

        } finally  {
            
            try {
                conexion.cerrarConexion();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return tmpEstado;

    }

    private JSONArray listarClientes(ResultSet resultSet) throws SQLException, JSONException {
        // Crear un JSONArray para almacenar los usuarios
        JSONArray listaClientes = new JSONArray();

        while (resultSet.next()) {
            // Crear un JSONObject para cada usuario y añadirlo al JSONArray
            JSONObject cliente = new JSONObject();

            cliente.put("ID", resultSet.getString("id_cliente"));
            cliente.put("Nombre", resultSet.getString("nombre"));
            cliente.put("Apellido", resultSet.getString("apellido"));
            cliente.put("Dirección", resultSet.getString("direccion"));
            cliente.put("Género", resultSet.getString("genero"));
            cliente.put("Teléfono", resultSet.getString("telefono"));

            listaClientes.put(cliente);
        }

        return listaClientes;
    }

}
