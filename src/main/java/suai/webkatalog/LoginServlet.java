package suai.webkatalog;

import suai.webkatalog.model.Admin;
import suai.webkatalog.model.Basket;
import suai.webkatalog.model.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/login-servlet")
public class LoginServlet extends HttpServlet {
    private final Users users = Users.getInstance();
    private final Admin admins = Admin.getInstance();

    @Override
    public void init() throws ServletException {
        super.init();
        if(users.getSize() == 0) {
            users.loadFile("C:\\Users\\Ilya\\Desktop\\YandexDisk\\Labs\\Java\\Web-katalog\\src\\main\\java\\suai\\webkatalog\\data\\users.txt");
        }
        admins.loadFile("C:\\Users\\Ilya\\Desktop\\YandexDisk\\Labs\\Java\\Web-katalog\\src\\main\\java\\suai\\webkatalog\\data\\admins.txt");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        String name = request.getParameter("login");
        String password = request.getParameter("password");
        if (name != null && password != null) {
            if (users.containUser(name, password)) {
                HttpSession session = request.getSession();
                session.setAttribute("name", name);
                Basket basket = new Basket();
                session.setAttribute("Basket", basket);
            }
            if (admins.containUser(name, password)) {
                HttpSession session = request.getSession();
                session.setAttribute("admin", name);
            }
            response.sendRedirect("index.jsp");
        } else {
            request.setAttribute("Error", "Error login and password");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
