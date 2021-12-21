package suai.webkatalog;

import suai.webkatalog.model.Catalog;
import suai.webkatalog.model.Categories;
import suai.webkatalog.model.Product;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AddProductServlet", value = "/AddProductServlet")
public class AddProductServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String number = request.getParameter("number");
        String price = request.getParameter("price");
        String categories = request.getParameter("categories");

        if (name != null && number != null && price != null && categories != null) {
            Catalog catalog = Catalog.getInstance();
            Product product = new Product(name, Integer.parseInt(number), Integer.parseInt(price));
            if (Categories.findByName(categories) != null) {
                product.setCategories(Categories.findByName(categories));
            }
            catalog.addProduct(product);
        }
        response.sendRedirect("index.jsp");
    }

    @Override
    public void destroy() {
        super.destroy();
        Catalog catalog = Catalog.getInstance();
        catalog.saveFile("C:\\Users\\Ilya\\Desktop\\YandexDisk\\Labs\\Java\\Web-katalog\\src\\main\\java\\suai\\webkatalog\\data\\catalog.txt");
    }
}
