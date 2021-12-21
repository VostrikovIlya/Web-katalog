package suai.webkatalog;

import suai.webkatalog.model.Catalog;
import suai.webkatalog.model.Product;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@WebServlet(name = "IndexServlet", value = "/IndexServlet")
public class IndexServlet extends HttpServlet {
    private Catalog catalog;
    @Override
    public void init() throws ServletException {
        catalog = Catalog.getInstance();
        try {
            catalog.loadFile("C:\\Users\\Ilya\\Desktop\\YandexDisk\\Labs\\Java\\Web-katalog\\src\\main\\java\\suai\\webkatalog\\data\\catalog.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int size = catalog.getSize();
        int numberPage = 1;
        int numberOfPage = (int) Math.ceil(size / 9.0);
        request.setAttribute("numberOfPage", String.valueOf(numberOfPage));
        Stream<Product> products = catalog.getProducts().stream().limit(9);
        request.setAttribute("products", products.collect(Collectors.toList()));
        request.getRequestDispatcher("index.jsp").forward(request,response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int size = catalog.getSize();
        int numberPage = 1;
        int numberOfPage = (int) Math.ceil(size / 9.0);
        request.setAttribute("numberOfPage", String.valueOf(numberOfPage));
        if (request.getParameter("numberPage") != null) {
            numberPage = Integer.parseInt(request.getParameter("numberPage"));
        }
        Stream<Product> products = catalog.getProducts().stream().skip((numberPage - 1) * 9L).limit(9);
        request.setAttribute("products", products.collect(Collectors.toList()));
        request.getRequestDispatcher("index.jsp").forward(request,response);
    }
}
