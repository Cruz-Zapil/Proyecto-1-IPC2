package com.paqueExpres.controllers;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.paqueExpres.DAO.UsuarioDAO;

@WebServlet("/consulta-usuario")

public class GetUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // recibir parametros

        String columna = request.getParameter("columan");
        String condicion = request.getParameter("condicion");

        JSONObject jsonResponse = new JSONObject();

        JSONArray clientesArray = new JSONArray();

        UsuarioDAO obtenerDatos = new UsuarioDAO(columna, condicion);
        obtenerDatos.conexion();

        try {

            if (condicion.equals("ALL")) {
                clientesArray = obtenerDatos.getAllUser();
                
            }else {
                clientesArray = obtenerDatos.getSepcificUser(condicion);
            }


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

        } catch (SQLException e) {
         
            e.printStackTrace();
            jsonResponse.put("Error al conectar a la DB", e.getMessage());
        }

    }

   

}
