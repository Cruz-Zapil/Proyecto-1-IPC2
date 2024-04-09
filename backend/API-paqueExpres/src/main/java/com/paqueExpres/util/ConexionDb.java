package com.paqueExpres.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDb {
    
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/paqueExpres";
    private static ConexionDb instancia;
    private Connection jdbcConnection;

    private ConexionDb(String jdbcUserName, String jdbcPassword) throws SQLException {
        try {

            // Cargar el controlador de la base de datos
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establecer la conexión con la base de datos
            this.jdbcConnection = DriverManager.getConnection(JDBC_URL, jdbcUserName, jdbcPassword);

        } catch (ClassNotFoundException e) {

            throw new SQLException("Error al cargar el controlador de la base de datos", e);
        }
    }

    public static synchronized ConexionDb obtenerInstancia(String jdbcUserName, String jdbcPassword) throws SQLException {
        // Verificar si ya existe una instancia o si la conexión está cerrada

        if (instancia == null || instancia.jdbcConnection.isClosed()) {

            // Si no existe una instancia o está cerrada, crear una nueva instancia
            
            instancia = new ConexionDb(jdbcUserName, jdbcPassword);
        }
        // Retornar la instancia existente o recién creada
        return instancia;
    }

    public Connection obtenerConexion() {
        return jdbcConnection;
    }

    public void cerrarConexion() throws SQLException {
        // Cerrar la conexión si está abierta
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }
}
