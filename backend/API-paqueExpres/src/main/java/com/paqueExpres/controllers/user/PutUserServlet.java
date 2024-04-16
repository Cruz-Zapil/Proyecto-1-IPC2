package com.paqueExpres.controllers.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.paqueExpres.DAO.UsuarioDAO;

@WebServlet("/update-user")
public class PutUserServlet extends HttpServlet {

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // obtener datos enviados
        BufferedReader reader = request.getReader();

        // instanciar conexion
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        // responder con boolean
        boolean estado;

        // Respuesta JSON
        JSONObject jsonResponse = new JSONObject();

        try {

            // establecer conecion
            usuarioDAO.conectar();

            estado = usuarioDAO.updateUser(reader);

            // Verificar si se actualizó el cliente
            if (estado) {
                // El cliente se actualizó correctamente
                jsonResponse.put("success", true);
                jsonResponse.put("message", "Los datos del Usuario han sido actualizados exitosamente!");
            } else {
                // No se encontró el cliente para actualizar
                jsonResponse.put("success", false);
                jsonResponse.put("message", "El Usuario no existe.");
            }

        } catch (SQLException e) {

            e.printStackTrace();

            // manejamos erroes en la conexion de base de datos:
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error al conectar a la DB: " + e.getMessage());

        }

        // Configurar la respuesta HTTP como JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8"); // Configurar la codificación de caracteres

    }
}
