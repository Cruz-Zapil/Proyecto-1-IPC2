package com.paqueExpres.controllers.recep;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.paqueExpres.DAO.DestinoDAO;

@WebServlet("/consulta-destino")
public class GetQueryDestinoServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // instaciar destino
        DestinoDAO destinoDAO = new DestinoDAO();
          
        // respuesta en JSON
        JSONObject jsonResponse = new JSONObject();
        JSONArray destinoArray = new JSONArray();


        try {

            // esablecer conexion
            destinoDAO.conectar();
            
            destinoArray = destinoDAO.getAllDestino();

            /// verficar si existe el cliente:
            if (destinoArray.length() !=0) {
                jsonResponse.put("success", true);
                jsonResponse.put("message", "El destino existe");
                jsonResponse.put("destinos",destinoArray);
           
            } else {

                jsonResponse.put("success", false);
                jsonResponse.put("message", "No hay registro no existe");
            }

        } catch (SQLException e) {
            e.printStackTrace();

            /// mensaje de error;
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error al conectar a la DB: " + e.getMessage());

        }
        // Configurar la respuesta HTTP como JSON

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8"); // Configurar la codificación de caracteres

        // Escribir el JSON de respuesta en el PrintWriter de HttpServletResponse
        response.getWriter().write(jsonResponse.toString());


    }
}
