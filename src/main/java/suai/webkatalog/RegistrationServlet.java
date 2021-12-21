package suai.webkatalog;

import suai.webkatalog.model.Basket;
import suai.webkatalog.model.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "RegistrationServlet", value = "/registration-servlet")
public class RegistrationServlet extends HttpServlet {
    private final Users users = Users.getInstance();

    @Override
    public void init() throws ServletException {
        super.init();
        if(users.getSize() == 0) {
            users.loadFile("C:\\Users\\Ilya\\Desktop\\YandexDisk\\Labs\\Java\\Web-katalog\\src\\main\\java\\suai\\webkatalog\\data\\users.txt");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("registration.jsp").forward(req, resp);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        String name = request.getParameter("login");
        String password = request.getParameter("password");
        if (name != null && password != null && !name.isEmpty() && !password.isEmpty()) {
            if (users.containUser(name)) {
                response.sendRedirect("login.jsp");
            }
            else {
                users.addUser(name, password);
                HttpSession session = request.getSession();
                session.setAttribute("name", name);
                Basket basket = new Basket();
                session.setAttribute("Basket", basket);
                response.sendRedirect("index.jsp");
            }
        }
        else {
            request.setAttribute("Error", "No Such element");
            request.getRequestDispatcher("registration.jsp").forward(request,response);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        users.saveFile("C:\\Users\\Ilya\\Desktop\\YandexDisk\\Labs\\Java\\Web-katalog\\src\\main\\java\\suai\\webkatalog\\data\\users.txt");
    }
}
