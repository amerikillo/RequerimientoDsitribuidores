/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pedidos;

import Correos.CorreoValidaDist;
import conn.ConectionDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Americo
 */
public class Capturar extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession sesion = request.getSession(true);
        ConectionDB con = new ConectionDB();
        try {
            if (request.getParameter("accion").equals("ValidaDist")) {
                CorreoValidaDist correo = new CorreoValidaDist();
                con.conectar();
                con.ejecutar("update tb_pedidos set F_StsPed=3 where  F_IdPed = '" + request.getParameter("F_IdPed") + "' ");
                correo.enviaCorreo(request.getParameter("F_IdPed"));
                con.cierraConexion();
                response.sendRedirect("verRequerimientos.jsp");
            }
            if (request.getParameter("accion").equals("EliminarPedido")) {
                con.conectar();
                con.ejecutar("update tb_pedidos set F_StsPed=500 where  F_IdPed = '" + request.getParameter("F_IdPed") + "' ");
                con.ejecutar("update tb_detpedido set F_StsPed=500 where  F_IdPed = '" + request.getParameter("F_IdPed") + "' ");
                con.cierraConexion();
                response.sendRedirect("verRequerimientos.jsp");
            }
            if (request.getParameter("accion").equals("Capturar")) {
                byte[] a = request.getParameter("F_Observaciones").getBytes("ISO-8859-1");
                String Observaciones = (new String(a, "UTF-8")).toUpperCase();
                con.conectar();
                con.ejecutar("insert into tb_detpedido values ('0','" + sesion.getAttribute("F_IdPed") + "','" + request.getParameter("F_ClaPro") + "','" + request.getParameter("CantSol") + "','" + Observaciones + "',CURDATE(),'0')");
                con.cierraConexion();
                response.sendRedirect("main_menu.jsp");
            }
            if (request.getParameter("accion").equals("EliminarInsumoEdi")) {
                con.conectar();
                con.ejecutar("delete from tb_detpedido where F_Id = '" + request.getParameter("F_Id") + "' ");
                con.cierraConexion();
                request.setAttribute("F_IdPed", request.getParameter("F_IdPed"));
                request.getRequestDispatcher("verRequerimentoEsp.jsp").forward(request, response);
            }
            if (request.getParameter("accion").equals("EliminarInsumo")) {
                con.conectar();
                con.ejecutar("delete from tb_detpedido where F_Id = '" + request.getParameter("F_Id") + "' ");
                con.cierraConexion();
                response.sendRedirect("main_menu.jsp");
            }
            if (request.getParameter("accion").equals("CancelarRequerimento")) {
                try {
                    con.conectar();
                    con.ejecutar("update tb_pedidos set F_StsPed='1' where F_IdPed='" + request.getParameter("F_IdPed") + "'");
                    con.cierraConexion();
                    sesion.setAttribute("F_IdPed", null);
                    response.sendRedirect("main_menu.jsp");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            if (request.getParameter("accion").equals("FinalizarRequerimento")) {
                try {
                    con.conectar();
                    con.ejecutar("update tb_pedidos set F_StsPed='2' where F_IdPed='" + request.getParameter("F_IdPed") + "'");
                    con.cierraConexion();
                    sesion.setAttribute("F_IdPed", null);
                    response.sendRedirect("main_menu.jsp");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            if (request.getParameter("accion").equals("NuevoRequerimento")) {
                try {
                    con.conectar();
                    con.ejecutar("insert into tb_pedidos values('" + sesion.getAttribute("F_ClaCli") + "','0','0',CURDATE(),NOW())");
                    ResultSet rset = con.consulta("select F_IdPed from tb_pedidos where F_ClaCli = '" + sesion.getAttribute("F_ClaCli") + "' and F_FecCap = NOW() and F_StsPed= '0' ");
                    String F_IdPed = "";
                    while (rset.next()) {
                        F_IdPed = rset.getString("F_IdPed");
                    }
                    con.cierraConexion();
                    sesion.setAttribute("F_IdPed", F_IdPed);
                    response.sendRedirect("main_menu.jsp");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
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
