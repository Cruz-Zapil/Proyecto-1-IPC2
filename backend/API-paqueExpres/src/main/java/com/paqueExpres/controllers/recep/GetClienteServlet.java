package com.paqueExpres.controllers.recep;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.paqueExpres.DAO.ClienteDAO;

@WebServlet("/consulta-cliente")
public class GetClienteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /// obtener los parametros de la solicitud
        String id_cliente = request.getParameter("id_cliente");

        // instaciar cliente
        ClienteDAO clienteDAO = new ClienteDAO();

        // respuesta en JSON
        JSONObject jsonResponse = new JSONObject();

        /// estado
        boolean estado = false;

        try {

            // esablecer conexion
            clienteDAO.conectar();

            estado = clienteDAO.checkClient(id_cliente);

            /// verficar si existe el cliente:
            if (estado) {
                jsonResponse.put("success", true);
                jsonResponse.put("message", "El cliente existe");
            } else {

                jsonResponse.put("success", false);
                jsonResponse.put("message", "El usuario no existe");
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
