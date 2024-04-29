package com.paqueExpres.controllers.operador;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.paqueExpres.DAO.RegistroPuntoDAO;

@WebServlet("/get-registro")
public class GetRegistroPunto extends HttpServlet{
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // Obtener los parámetros de consulta de la URL
        String columna  = request.getParameter("columuna");
        String condicion = request.getParameter("condicion");

        /// instaciar conexion
        RegistroPuntoDAO registroPuntoDAO = new RegistroPuntoDAO();

        /// respuesta en JSON
        JSONObject jsonResponse = new JSONObject();
        JSONArray listaDeDatos = new JSONArray();

        try {

            /// establecer conexion
            registroPuntoDAO.conectar();
            listaDeDatos = registroPuntoDAO.getRegistroPunto(columna, condicion);

            /// verficar si existe el cliente:
            if (listaDeDatos.length() != 0) {
                jsonResponse.put("success", true);
                jsonResponse.put("message", "El usuario existe");
                jsonResponse.put("datos", listaDeDatos);

            } else {

                jsonResponse.put("success", false);
                jsonResponse.put("message", "No existe el registro");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // mesaje de error
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Errro al conectar a la DB: " + e.getMessage());

        }

        // Configurar la respuesta HTTP como JSON

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8"); // Configurar la codificación de caracteres

        // Escribir el JSON de respuesta en el PrintWriter de HttpServletResponse
        response.getWriter().write(jsonResponse.toString());


    }
    
}
