package com.paqueExpres.controllers;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import com.paqueExpres.DAO.UsuarioDAO;

@WebServlet("/consulta-usuario")
public class GetUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener los parámetros de la solicitud HTTP
        String columna = request.getParameter("columna");
        String condicion = request.getParameter("condicion");

        JSONArray usuarioArray = new JSONArray();

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        try {
            // Establecer la conexión con la base de datos
            usuarioDAO.conectar();

            if ("ALL".equals(condicion)) {
                // Obtener todos los usuarios si la condición es "ALL"
                usuarioArray = usuarioDAO.obtenerTodosLosUsuarios();
            } else {
                // Obtener usuarios específicos según la columna y la condición
                usuarioArray = usuarioDAO.obtenerUsuarioEspecifico(columna, condicion);
            }
            // Configurar la respuesta HTTP como JSON
            response.setContentType("application/json");
            response.getWriter().print(usuarioArray);

        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar errores de conexión a la base de datos
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error al conectar a la DB: " + e.getMessage());
        } 


         // Configurar la respuesta HTTP como JSON
         response.setContentType("application/json");
         response.setCharacterEncoding("UTF-8"); // Configurar la codificación de caracteres
         
    }
}
