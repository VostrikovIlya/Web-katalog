package suai.webkatalog;

import suai.webkatalog.model.Catalog;
import suai.webkatalog.model.Categories;
import suai.webkatalog.model.Product;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@WebServlet(name = "CategoriesServlet", value = "/CategoriesServlet")
public class CategoriesServlet extends HttpServlet {
    private final Catalog catalog = Catalog.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Categories categories = Categories.findByName(request.getParameter("Categories"));
        int numberPage = 1;
        if (categories != null) {
            Stream<Product> productStream = catalog.getProducts().stream().filter(e ->
            {
                if (e.getCategories() != null)
                    return e.getCategories().equals(categories);
                return false;
            });
            List<Product> list = productStream.collect(Collectors.toList());
            int size = list.size();
            int numberOfPage = (int) Math.ceil(size / 9.0);
            request.setAttribute("numberOfPage", numberOfPage);
            if (request.getParameter("numberPage") != null) {
                numberPage = Integer.parseInt(request.getParameter("numberPage"));
            }
            Stream<Product> products = list.stream().skip((numberPage - 1) * 9L).limit(9);
            request.setAttribute("products", products.collect(Collectors.toList()));
            request.getRequestDispatcher("categories.jsp").forward(request, response);
        }
    }
}
