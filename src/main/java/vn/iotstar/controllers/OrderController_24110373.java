package vn.iotstar.controllers;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.models.Order_24110373;
import vn.iotstar.models.User_24110373;
import vn.iotstar.service.OrderService_24110373;
import vn.iotstar.service.impl.OrderServiceImpl_24110373;

@WebServlet(urlPatterns = { "/orders", "/order/detail" })
public class OrderController_24110373 extends HttpServlet {

    private final OrderService_24110373 orderService = new OrderServiceImpl_24110373();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        User_24110373 user = session == null ? null : (User_24110373) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        if (user.isAdmin()) {
            session.setAttribute("flashError", "Tài khoản quản trị không có đơn hàng.");
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        if ("/order/detail".equals(req.getServletPath())) {
            int orderId;
            try {
                orderId = Integer.parseInt(req.getParameter("id"));
            } catch (Exception e) {
                resp.sendRedirect(req.getContextPath() + "/orders");
                return;
            }
            Order_24110373 order = orderService.getOrder(orderId, user.getId());
            if (order == null) {
                resp.sendRedirect(req.getContextPath() + "/orders");
                return;
            }
            req.setAttribute("order", order);
            req.getRequestDispatcher("/views/order-detail.jsp").forward(req, resp);
        } else {
            List<Order_24110373> orders = orderService.getOrders(user.getId());
            req.setAttribute("orders", orders);
            req.getRequestDispatcher("/views/orders.jsp").forward(req, resp);
        }
    }
}
