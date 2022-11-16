/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import model.Product;

/**
 *
 * @author Asus
 */
@WebServlet(name="EditCartController", urlPatterns={"/edit-cart"})
public class EditCartController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String rawDirection  = request.getParameter("direction");
        String rawPid = request.getParameter("pid");
        int pid = 0;
        try{
            pid = Integer.parseInt(rawPid);
        }catch(NumberFormatException exception){
            
        }
        
        boolean isUp = rawDirection.toLowerCase().equals("up");
        HttpSession session = request.getSession();
        ArrayList<Product> products = (ArrayList<Product>) session.getAttribute("carts");
        if(isUp){
            updateQuantityProducts(1, products, pid);
        }else{
            updateQuantityProducts(-1, products, pid);
        }
        double totalPrice = getTotalPrice(products);
        session.setAttribute("totalPrice", totalPrice);
        response.sendRedirect("cart.jsp");
    }
    
    void updateQuantityProducts(int direction , ArrayList<Product> products , int pid){
        for(int i = 0 ; i < products.size() ; i++){
            if(pid == products.get(i).getId()){
                products.get(i).setQuantity(products.get(i).getQuantity() + direction);
                return;
            }
        }
    }
    
    double getTotalPrice(ArrayList<Product> products){
        double totalPrice = 0;
        for(int i = 0 ; i < products.size() ; i++){
            totalPrice += products.get(i).getPrice() * products.get(i).getQuantity();
        }
        return totalPrice;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
