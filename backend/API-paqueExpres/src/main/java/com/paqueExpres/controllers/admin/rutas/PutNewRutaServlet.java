package com.paqueExpres.controllers.admin.rutas;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/new-ruta")
public class PutNewRutaServlet extends HttpServlet{

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
        /// obtener datos enviados
        BufferedReader reader = request.getReader();

        // instanciar conexion --> falta 

        

    }
    
}
