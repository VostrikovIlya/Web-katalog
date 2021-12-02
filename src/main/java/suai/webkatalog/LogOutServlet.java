package suai.webkatalog;

import suai.webkatalog.model.Basket;
import suai.webkatalog.model.Catalog;
import suai.webkatalog.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LogOutServlet", value = "/log-out-servlet")
public class LogOutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        HttpSession ses = request.getSession();
        Basket basket = (Basket) ses.getAttribute("Basket");
        Catalog catalog = Catalog.getInstance();
        if (ses.getAttribute("name") != null && basket != null) {
            for (Product product : basket.getProducts()) {
                int number = product.getNumber();
                catalog.getProduct(product.getId()).ifPresentOrElse(prod -> prod.setNumber(prod.getNumber() + number), () -> catalog.addProduct(product));
            }
        }
        if (session != null)
            session.invalidate();
        response.sendRedirect("index.jsp");
    }
}

