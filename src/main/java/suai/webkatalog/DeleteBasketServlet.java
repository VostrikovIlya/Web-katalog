package suai.webkatalog;

import suai.webkatalog.model.Basket;
import suai.webkatalog.model.Catalog;
import suai.webkatalog.model.Product;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "DeleteBasketServlet", value = "/DeleteBasketServlet")
public class DeleteBasketServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession ses = request.getSession();
        String idProduct = request.getParameter("id");
        Basket basket = (Basket) ses.getAttribute("Basket");
        Catalog catalog = Catalog.getInstance();

        if (ses.getAttribute("name") != null && idProduct != null && basket != null) {
            Product productFromBasket = basket.removeProduct(idProduct)
                    .orElseThrow(RuntimeException::new);
            ses.setAttribute("Basket", basket);
            int number = productFromBasket.getNumber();

            catalog.getProduct(idProduct).ifPresentOrElse(product -> product.setNumber(product.getNumber() + number), () -> catalog.addProduct(productFromBasket));
        }
        response.sendRedirect(request.getHeader("referer"));
    }
}
