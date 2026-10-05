package vn.iotstar.controllers;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.models.CartItem_24110373;
import vn.iotstar.models.User_24110373;
import vn.iotstar.service.CartService_24110373;
import vn.iotstar.service.OrderService_24110373;
import vn.iotstar.service.impl.CartServiceImpl_24110373;
import vn.iotstar.service.impl.OrderServiceImpl_24110373;
import vn.iotstar.utils.BusinessException_24110373;
import vn.iotstar.utils.RequestUtil_24110373;

@WebServlet(urlPatterns = { "/checkout" })
public class CheckoutController_24110373 extends HttpServlet {

    private final CartService_24110373 cartService = new CartServiceImpl_24110373();
    private final OrderService_24110373 orderService = new OrderServiceImpl_24110373();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User_24110373 user = currentUser(req);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login?redirect="
                    + URLEncoder.encode(req.getContextPath() + "/cart", StandardCharsets.UTF_8));
            return;
        }
        if (user.isAdmin()) {
            denyAdmin(req, resp);
            return;
        }

        List<Integer> ids = RequestUtil_24110373.parseIds(req.getParameterValues("ids"));
        List<CartItem_24110373> items = cartService.getSelected(user.getId(), ids);
        if (items.isEmpty()) {
            req.getSession().setAttribute("flashError", "Vui lòng chọn ít nhất một sản phẩm để thanh toán.");
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }
        if (cartService.hasStockProblem(items)) {
            req.getSession().setAttribute("flashError",
                    "Có sách đã chọn vượt quá tồn kho. Vui lòng điều chỉnh số lượng trước khi thanh toán.");
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        req.setAttribute("receiverName", user.getFullname());
        showForm(req, resp, items);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        User_24110373 user = currentUser(req);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        if (user.isAdmin()) {
            denyAdmin(req, resp);
            return;
        }

        List<Integer> ids = RequestUtil_24110373.parseIds(req.getParameterValues("ids"));
        String receiverName = req.getParameter("receiverName");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String note = req.getParameter("note");

        try {
            int orderId = orderService.checkoutCOD(user.getId(), ids, receiverName, phone, address, note);
            resp.sendRedirect(req.getContextPath() + "/order/detail?id=" + orderId + "&success=1");
        } catch (BusinessException_24110373 e) {
            List<CartItem_24110373> items = cartService.getSelected(user.getId(), ids);
            if (items.isEmpty()) {
                req.getSession().setAttribute("flashError", e.getMessage());
                resp.sendRedirect(req.getContextPath() + "/cart");
                return;
            }
            req.setAttribute("error", e.getMessage());
            req.setAttribute("receiverName", receiverName);
            req.setAttribute("phone", phone);
            req.setAttribute("address", address);
            req.setAttribute("note", note);
            showForm(req, resp, items);
        }
    }

    private void showForm(HttpServletRequest req, HttpServletResponse resp, List<CartItem_24110373> items)
            throws ServletException, IOException {
        req.setAttribute("cartItems", items);
        req.setAttribute("cartTotal", cartService.getTotal(items));
        req.setAttribute("hasStockProblem", cartService.hasStockProblem(items));
        req.getRequestDispatcher("/views/checkout.jsp").forward(req, resp);
    }

    private User_24110373 currentUser(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return session == null ? null : (User_24110373) session.getAttribute("user");
    }

    private void denyAdmin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().setAttribute("flashError", "Tài khoản quản trị không có giỏ hàng và đơn hàng.");
        resp.sendRedirect(req.getContextPath() + "/home");
    }
}
