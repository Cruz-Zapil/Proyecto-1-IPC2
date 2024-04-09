/*
package com.paqueExpres.controllers;

import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.paqueExpres.util.ConexionDb;

@WebServlet("/consulta-clientes")

public class GetClienteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /// peticion
        String tabla = request.getParameter("tabla");
        String columna = request.getParameter("columna");
        String condicion = request.getParameter("condicion");
        
        int tipoPeticion;
        try{
             tipoPeticion = Integer.parseInt(request.getParameter("tipoPeticion"));

        }catch (NumberFormatException e){

            tipoPeticion =1;
        }

        /// realizamos conexion
        //Conexion conexion = new Conexion("root", "VictorQuiej135-");
        ConexionDb conexion = new ConexionDb(  "root","VictorQuiej135-" ); 

        JSONObject jsonResponse = new JSONObject();

        try  {

            conexion.connection();
            Connection jdbcConexion = (Connection) conexion.getJdbcConnection();
            String sqlScrip = "";

           
            
            switch (tipoPeticion) {
                case 1:
                
                sqlScrip = "SELECT * FROM " + tabla;
                
                break;
                
                case 2:
                sqlScrip = "SELECT * FROM " + tabla + " WHERE " + columna + " = '"+ condicion+ "'" ;
                
                    break;
            }

            /// Ejecutar consulta sql

            if (!sqlScrip.equals("")) {

                PreparedStatement statement = jdbcConexion.prepareStatement(sqlScrip);

                ResultSet resultSet = statement.executeQuery();

                /// procesar Resultado:

                JSONArray clientesArray = new JSONArray();
          
                clientesArray = peticion1(resultSet);  
              

                // cerrando recursos
                resultSet.close();
                statement.close();

                // Enviar respuesta en formato JSON
                response.setContentType("application/json");

                try {

                    response.getWriter().print(clientesArray);
                    response.flushBuffer();

                } catch (IOException e) {

                    e.printStackTrace();
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.getWriter().write("Error al enviar la respuesta.");

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            jsonResponse.put("suss", e);

        } finally {
            /// cerrando conexion con la DB
            try {
                conexion.descconect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public JSONArray peticion1(ResultSet resultSet) throws JSONException, SQLException {

        JSONArray listaClientes = new JSONArray();

        while (resultSet.next()) {
            JSONObject cliente = new JSONObject();
            cliente.put("id", resultSet.getString("id_cliente"));
            cliente.put("nombre", resultSet.getString("nombre"));
            cliente.put("apellido", resultSet.getString("apellido"));
            listaClientes.put(cliente);

        }
        return listaClientes;

    }

}
*/