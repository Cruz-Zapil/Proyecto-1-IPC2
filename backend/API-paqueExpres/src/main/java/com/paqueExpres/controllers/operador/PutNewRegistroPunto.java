package com.paqueExpres.controllers.operador;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.paqueExpres.DAO.RegistroPuntoDAO;

@WebServlet("/new-registroPunto")
public class PutNewRegistroPunto extends HttpServlet {

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // obtener datos enviados
        BufferedReader reader = request.getReader();

        /// instaciar conexion
        RegistroPuntoDAO registroPuntoDAO = new RegistroPuntoDAO();

        // responder con boolean
        boolean estado = false;

        /// respuesta en JSON
        JSONObject jsonResponse = new JSONObject();

        try {

            /// establecer conexion
            registroPuntoDAO.conectar();
            estado = registroPuntoDAO.newRegistroPunto(reader);

            if (estado) {
                jsonResponse.put("success", true);
                jsonResponse.put("message", "Se a creado un nuevo registro:");

            } else {

                jsonResponse.put("success", false);
                jsonResponse.put("message", "Error registro no agregado, verifique sus datos.");

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
