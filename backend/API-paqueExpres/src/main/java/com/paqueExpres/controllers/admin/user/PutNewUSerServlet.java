package com.paqueExpres.controllers.admin.user;

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

@WebServlet("/new-user")
public class PutNewUSerServlet extends HttpServlet {

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /// obtener datos enviados
        BufferedReader reader = request.getReader();

        // instanciar conencio
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        // responder con boolean
        boolean estado = false;

        /// respuesa en JSON
        JSONObject jsonResponse = new JSONObject();

        try {

            /// establecer conexion
            usuarioDAO.conectar();

            estado = usuarioDAO.newUser(reader);

            /// verificar si se añadio el usuario
            if (estado) {
                /// el usuario se añadio

                jsonResponse.put("success", true);
                jsonResponse.put("message", "Un usuario ha sido agregado");

            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "El Usuario no se agrego.");

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
