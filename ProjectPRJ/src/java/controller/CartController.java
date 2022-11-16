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
@WebServlet(name = "CartController", urlPatterns = {"/show"})
public class CartController extends HttpServlet {

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
        String rawProductId = request.getParameter("pid");
        try {
            int productId = Integer.parseInt(rawProductId);
        } catch (NumberFormatException ex) {

        }
        HttpSession session = request.getSession();
        ProductDAO productDao = new ProductDAO();
        Product product = productDao.getProductsByID(rawProductId);
        ArrayList<Product> products = (ArrayList<Product>) session.getAttribute("carts");
        if(products == null){
            products = new ArrayList<>();
            session.setAttribute("carts", products);
        }
        int position = getPositionDuplicatedProduct(product, products);
        if(position != -1){
            Product p = products.get(position);
            p.setQuantity(p.getQuantity() + 1);
        }else if(product != null){
            product.setQuantity(1);
            products.add(product);
        }
        double totalPrice = getTotalPrice(products);
        session.setAttribute("totalPrice", totalPrice);
        response.sendRedirect("cart.jsp");
    }
    
    int getPositionDuplicatedProduct(Product product , ArrayList<Product> products){
        if(product == null) return -1;
        for(int i = 0 ; i < products.size() ; i++){
            if(product.getId() == products.get(i).getId()) return i;
        }
        return -1;
    }
    
    double getTotalPrice(ArrayList<Product> products){
        double totalPrice = 0;
        for(int i = 0 ; i < products.size() ; i++){
            totalPrice += products.get(i).getPrice() * products.get(i).getQuantity();
        }
        return totalPrice;
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

    }

}
