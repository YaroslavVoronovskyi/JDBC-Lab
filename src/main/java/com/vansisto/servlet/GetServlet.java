package com.vansisto.servlet;

import com.vansisto.service.ProductService;
import com.vansisto.service.impl.ProductServiceImpl;
import com.vansisto.util.RestUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("get")
public class GetServlet extends HttpServlet {

    private static final String PRODUCT_ID = "id";
    private final ProductService productService = new ProductServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productId = req.getParameter(PRODUCT_ID);
        String respJson = RestUtil.toJson(productService.getById(Long.parseLong(productId)));
        resp.getWriter().write(respJson);
    }
}
