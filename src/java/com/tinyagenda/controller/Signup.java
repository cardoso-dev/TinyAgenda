/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tinyagenda.controller;

import com.tinyagenda.model.Person;
import com.tinyagenda.persist.GeneralDAO;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Pedro
 */
public class Signup extends HttpServlet {

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
        String usr=request.getParameter("usr");
        String pwd=request.getParameter("pwd");
        String pwd2=request.getParameter("pwd2");
        GeneralDAO genDao=new GeneralDAO();
        RequestDispatcher reqDisp;
        Person user;
        if(usr.trim().equals("") || pwd.trim().equals("") || !pwd.equals(pwd2)){
            request.setAttribute("trysignup",false);
            request.setAttribute("errorsignup","You must fill each and every field and the passwords must match");
        }else{
            user=(Person)genDao.getEntity("select count(*) from Person where name='"+usr+"'");
            if(user!=null){
                request.setAttribute("trysignup",false);
                request.setAttribute("errorsignup","User name already in use");
            }else{
                user=new Person(usr,pwd);
                if(genDao.saveEntity(user)){
                    request.setAttribute("trysignup",true);
                }else{
                    request.setAttribute("trysignup",false);
                    request.setAttribute("errorsignup",genDao.getError());
                }
            }
        }
        reqDisp=request.getRequestDispatcher("/index.jsp");
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
