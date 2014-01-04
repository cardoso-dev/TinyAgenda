/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tinyagenda.controller;

import com.tinyagenda.model.Person;
import com.tinyagenda.persist.GeneralDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Pedro
 */
public class SeeDayEvents extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        GeneralDAO genDao=new GeneralDAO();
        RequestDispatcher reqDisp;
        List<Object> events=null;
        int month=0;
        int year=0;
        int day=0;
        Person usr=(Person)request.getSession().getAttribute("user");
        if( request.getParameter("loadmonth")!=null && request.getParameter("loadyear")!=null
                && request.getParameter("loadday")!=null){
            month=Integer.parseInt(request.getParameter("loadmonth").toString());
            year=Integer.parseInt(request.getParameter("loadyear").toString());
            day=Integer.parseInt(request.getParameter("loadday").toString());
            events=genDao.getListEvents(year, month, day, usr);
            if(events==null){
                request.setAttribute("loadingerror",genDao.getError());
            }else if(events.isEmpty()){
                request.setAttribute("loadingempty",true);
            }
        }
        request.setAttribute("myevents",events);
        request.setAttribute("stringdate",year+"-"+(month+1)+"-"+day);
        request.setAttribute("loadmonth",month);
        request.setAttribute("loadyear",year);
        request.setAttribute("loadContent", "events.jsp");
        reqDisp=request.getRequestDispatcher("index.jsp");
        reqDisp.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
