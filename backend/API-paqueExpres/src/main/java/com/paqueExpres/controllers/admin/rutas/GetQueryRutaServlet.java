package com.paqueExpres.controllers.admin.rutas;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.paqueExpres.DAO.RutaDAO;

@WebServlet ("/search-ruta")
public class GetQueryRutaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        

           
        
        /// obtener datos enviados
        BufferedReader reader = request.getReader();

        // instanciar conexion
       RutaDAO rutaDAO = new RutaDAO();

        // responder con boolean
        boolean estado = false;

        /// respuesa en JSON
        JSONObject jsonResponse = new JSONObject();

        try {
            // conexion 
            rutaDAO.conectar();

        } catch (Exception e) {
            
        }


    }
}
