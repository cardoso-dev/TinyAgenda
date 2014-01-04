/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tinyagenda.controller;

import com.tinyagenda.model.Event;
import com.tinyagenda.model.Person;
import com.tinyagenda.persist.GeneralDAO;
import java.io.IOException;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Pedro
 */
public class SaveEvent extends HttpServlet {

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
        int eday=Integer.parseInt(request.getParameter("loadday"));
        int emnt=Integer.parseInt(request.getParameter("loadmonth"));
        int eyar=Integer.parseInt(request.getParameter("loadyear"));
        String hours=request.getParameter("hh");
        String mins=request.getParameter("mm");
        String evName=request.getParameter("name");
        String evDescrip=request.getParameter("about");
        String redirect;
        Calendar cal=Calendar.getInstance();
        Person usr=(Person)request.getSession().getAttribute("user");
        Date theDay;
        Time theTime;
        RequestDispatcher reqDisp;
        cal.set(Calendar.YEAR, eyar);
        cal.set(Calendar.MONTH, emnt);
        cal.set(Calendar.DATE, eday);
        theDay=cal.getTime();
        theTime=Time.valueOf(hours+":"+mins+":00");
        Event event=new Event(theDay,theTime,evName,evDescrip,usr);
        if(genDao.saveEntity(event)){
            request.setAttribute("loadmonth",""+emnt);
            request.setAttribute("loadyear",""+eyar);
            request.setAttribute("loadContent", "month.jsp");
            request.setAttribute("trysvevent",true);
            redirect="/month";
        }else{
            request.setAttribute("trysvevent",false);
            request.setAttribute("loadday",""+eday);
            request.setAttribute("loadmonth",""+emnt);
            request.setAttribute("loadyear",""+eyar);
            request.setAttribute("loadContent", "eventForm.jsp");
            request.setAttribute("errorsaving",genDao.getError());
            redirect="/index.jsp";
        }
        reqDisp=request.getRequestDispatcher(redirect);
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
