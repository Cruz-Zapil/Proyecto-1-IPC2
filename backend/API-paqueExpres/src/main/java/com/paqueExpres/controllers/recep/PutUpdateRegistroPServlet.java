package com.paqueExpres.controllers.recep;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.paqueExpres.DAO.PackageDAO;

@WebServlet("/update-registro-punto")

public class PutUpdateRegistroPServlet extends HttpServlet {
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
          // obtener datos enviados
        BufferedReader reader = request.getReader();

        /// instaciar conexion
        PackageDAO packageDAO = new PackageDAO();

        // responder con boolean
        boolean estado = false;

        /// respuesta en JSON
        JSONObject jsonResponse = new JSONObject();
        
           try {

            /// establecer conexion
            packageDAO.conectar();
            estado = packageDAO.updatePackage(reader);

            if (estado) {
                jsonResponse.put("success", true);
                jsonResponse.put("message", "Se a agregado un nuevo Paquete:");

            } else {

                jsonResponse.put("success", false);
                jsonResponse.put("message", "Error: Paquete no agregado, verifique sus datos.");

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
