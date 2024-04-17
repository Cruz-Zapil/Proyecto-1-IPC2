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

@WebServlet("/inicio-sesion")
public class PostUserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener datos del cliente del cuerpo de la solicitud en JSON
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;

        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        JSONObject jsonData = new JSONObject(sb.toString());

        // separar los datos:

        String username = jsonData.getString("username");
        String password = jsonData.getString("password");

        /// instaciar conexion:
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        // responder con:
        JSONObject datosUsuario = new JSONObject();
        
        try {
            // establecer conexion
            usuarioDAO.conectar();
            datosUsuario = usuarioDAO.loggin(username, password);

            // configuramso respuesta http

            response.setContentType("applicaton/json");
            response.getWriter().print(datosUsuario);

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
