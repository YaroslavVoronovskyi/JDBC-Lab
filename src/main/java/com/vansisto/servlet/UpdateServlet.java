package com.vansisto.servlet;

import com.vansisto.model.Product;
import com.vansisto.service.ProductService;
import com.vansisto.service.impl.ProductServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("update")

public class UpdateServlet extends HttpServlet {

    private static final String PRODUCT_ID = "id";
    private static final String PRODUCT_NAME = "id";
    private static final String PRODUCT_PRICE = "id";
    private final ProductService productService = new ProductServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter(PRODUCT_ID);
        String name = req.getParameter(PRODUCT_NAME);
        String price = req.getParameter(PRODUCT_PRICE);

        Product product = productService.getById(Long.parseLong(id));
        product.setName(name);
        product.setPrice(BigDecimal.valueOf(Long.parseLong(price)));
        int status = productService.update(product);
        resp.setStatus(status);
    }
}
