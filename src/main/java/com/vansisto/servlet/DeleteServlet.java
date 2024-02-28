package com.vansisto.servlet;

import com.vansisto.service.ProductService;
import com.vansisto.service.impl.ProductServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {

    private static final String PRODUCT_ID = "id";
    private final ProductService productService = new ProductServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productId = req.getParameter(PRODUCT_ID);
        int status = productService.deleteById(Long.parseLong(productId));
        resp.setStatus(status);
    }
}
