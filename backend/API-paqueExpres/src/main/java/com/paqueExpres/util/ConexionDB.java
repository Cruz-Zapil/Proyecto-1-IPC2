package com.paqueExpres.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    
    private String jdbcURL ="jdbc:mysql://localhost:3306/PaqueExpres";
    private String jdbcUserName;
    private String jdbcPassword;

    private Connection jdbcConnection;

    public ConexionDB(String jdbcUserName, String jdbcPassword) {
        this.jdbcUserName = jdbcUserName;
        this.jdbcPassword = jdbcPassword;
    }

    public void connection() throws SQLException {
        if(jdbcConnection == null || jdbcConnection.isClosed()){
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            this.jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUserName, jdbcPassword);
        }
    }

    public void descconect() throws SQLException{
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }

    public Connection getJdbcConnection(){
        return jdbcConnection;
    }
}
