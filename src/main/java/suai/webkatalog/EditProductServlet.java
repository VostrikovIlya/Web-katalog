package suai.webkatalog;

import suai.webkatalog.model.Catalog;
import suai.webkatalog.model.Categories;
import suai.webkatalog.model.Product;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "EditProductServlet", value = "/EditProductServlet")
public class EditProductServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String number = request.getParameter("number");
        String price = request.getParameter("price");
        String categories = request.getParameter("categories");
        String id = request.getParameter("id");
        if(name != null && number != null && price != null && categories != null && id != null) {
            Catalog catalog = Catalog.getInstance();
            Product product = catalog.getProduct(id).orElseThrow(RuntimeException::new);
            product.setName(name);
            product.setNumber(Integer.parseInt(number));
            product.setPrice(Integer.parseInt(price));
            if(Categories.findByName(categories) != null) {
                product.setCategories(Categories.findByName(categories));
            }
        }
        response.sendRedirect("index.jsp");
    }
}
