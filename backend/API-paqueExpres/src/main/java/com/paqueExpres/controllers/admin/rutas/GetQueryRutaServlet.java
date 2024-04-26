package com.paqueExpres.controllers.admin.rutas;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.paqueExpres.DAO.RutaDAO;

@WebServlet ("/search-ruta")
public class GetQueryRutaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        


        String columna = request.getParameter("columan");
        String condicion = request.getParameter("condicion");
        
        // instanciar conexion
        RutaDAO rutaDAO = new RutaDAO();
    
        /// respuesa en JSON
        JSONObject jsonResponse = new JSONObject();
        JSONArray packageArray = new JSONArray();

        try {
            // conexion 
            rutaDAO.conectar();

            packageArray = rutaDAO.getPackage(columna,condicion);

            if (packageArray.length() != 0) {
                jsonResponse.put("succes",true);
                jsonResponse.put("message","Check ruta: ");
                jsonResponse.put("package", packageArray);           
            } else {
                jsonResponse.put("succes",false);
                jsonResponse.put("message","No se encontro ningun registro");

            }


        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar errores de conexión a la base de datos
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
