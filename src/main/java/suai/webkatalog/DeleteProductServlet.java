package suai.webkatalog;

import suai.webkatalog.model.Basket;
import suai.webkatalog.model.Catalog;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "DeleteProductServlet", value = "/DeleteProductServlet")
public class DeleteProductServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession ses = request.getSession();
        Catalog catalog = Catalog.getInstance();
        String idProduct = request.getParameter("id");
        String admin = (String) ses.getAttribute("admin");
        if(admin != null && idProduct != null) {
            catalog.removeProduct(idProduct);
        }
        response.sendRedirect(request.getHeader("referer"));
    }
}
