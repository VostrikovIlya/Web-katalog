package suai.webkatalog;

import suai.webkatalog.model.Basket;
import suai.webkatalog.model.Catalog;
import suai.webkatalog.model.Categories;
import suai.webkatalog.model.Product;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@WebServlet(name = "AddBasketServlet", value = "/AddBasketServlet")
public class AddBasketServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession ses = request.getSession();
        Basket basket = (Basket) ses.getAttribute("Basket");
        Catalog catalog = Catalog.getInstance();
        String idProduct = request.getParameter("id");
        if (idProduct != null && basket != null) {
            Product prodFromCatalog = catalog.getProduct(idProduct)
                    .orElseThrow(RuntimeException::new);

            if (basket.containsProduct(idProduct)) {
                basket.getProduct(idProduct).ifPresent(product -> product.setNumber(product.getNumber() + 1));
            } else {
                basket.addProduct(new Product(prodFromCatalog.getName(), 1, prodFromCatalog.getPrice(), prodFromCatalog.getId()));
            }

            prodFromCatalog.setNumber(prodFromCatalog.getNumber() - 1);
            if (prodFromCatalog.getNumber() < 1) {
                catalog.removeProduct(prodFromCatalog);
            }
        }
        response.sendRedirect(request.getHeader("referer"));
    }
}
