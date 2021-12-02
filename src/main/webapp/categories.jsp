<%@ page import="suai.webkatalog.model.Categories" %>
<%@ page import="suai.webkatalog.model.Product" %>
<%@ page import="java.util.concurrent.ConcurrentSkipListSet" %>
<%@ page import="suai.webkatalog.model.Catalog" %>
<%@ page import="java.util.stream.Stream" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Catalog</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
            crossorigin="anonymous"></script>

</head>
<body>
<nav class="navbar navbar-expand-sm navbar-dark bg-dark">
    <% HttpSession ses = request.getSession();
        String name = (String) ses.getAttribute("name");
        String admin = (String) ses.getAttribute("admin");

    %>
    <div class="container-fluid">
        <a class="navbar-brand" href="index.jsp">Home</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup"
                aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav">
                <% if (name == null && admin == null) {%>
                <a class="nav-link active" aria-current="page" href="login.jsp"><%="Login"%>
                </a>
                <a class="nav-link active" aria-current="page" href="registration.jsp"><%="Registration"%>
                </a>
                <%
                    }
                    if (name != null) {
                %>
                <a class="nav-link active" aria-current="page" href="log-out-servlet"><%="LogOut"%>
                </a>
                <a class="nav-link active" aria-current="page" href="basket.jsp"><%="Basket"%>
                </a>
                <%
                    }
                    if (admin != null) {
                %>
                <a class="nav-link active" aria-current="page" href="log-out-servlet"><%="LogOut"%>
                </a>
                <a class="nav-link active" aria-current="page" href="basket.jsp"><%="Add Product"%>
                </a>
                <%}%>
            </div>
        </div>
    </div>
</nav>


<div class="container overflow-hidden">
    <div class="row m-4">
        <div class="col-sm-2">
            <div class="list-group">
                <%
                    Categories[] categories = Categories.values();
                    for (Categories categories1 : categories) {
                %>
                <a href="categories.jsp?Categories=<%=categories1%>" class="list-group-item"><%=categories1%>
                </a>
                <%}%>
            </div>
        </div>
        <div class="col-md-8 products">
            <div class="row">
                <%
                    Categories categories2 = Categories.findByName(request.getParameter("Categories"));
                    Catalog catalog = Catalog.getInstance();
                    if (categories2 != null) {
                        Stream<Product> productStream = catalog.getProducts().stream().filter(e ->
                        {
                            if (e.getCategories() != null)
                                return e.getCategories().equals(categories2);
                            return false;
                        });

                        for (Product product : productStream.collect(Collectors.toCollection(ArrayList::new))) {
                %>
                <div class="col-md-4">
                    <div class="card text-white bg-dark mb-3" style="max-width: 18rem;">
                        <div class="card-header"><%=product.getName()%>
                        </div>
                        <div class="card-body">
                            <h5 class="card-title"><%="Price: " + product.getPrice() + "$"%>
                            </h5>
                            <p class="card-text"><%=product.getNumber()%>
                            </p>
                            <% if (product.getCategories() != null) {%>
                            <p class="card-text"><%=product.getCategories()%>
                            </p>
                            <%
                                }
                                if (name != null) {
                            %>
                            <div class="mb-3">
                                <form action="AddBasketServlet" method="post">
                                    <input type="hidden" name="id" value="<%=product.getId()%>">
                                    <input type="submit" class="btn btn-light" value="Add">
                                </form>
                            </div>
                            <%
                                }
                                if (admin != null) {
                            %>
                            <div class="mb-3">
                                <form action="DeleteProductServlet" method="post">
                                    <input type="hidden" name="id" value="<%=product.getId()%>">
                                    <input type="submit" class="btn btn-light" value="Delete">
                                </form>
                            </div>
                            <div class="mb-3">
                                <form action="editProduct.jsp" method="post">
                                    <input type="hidden" name="id" value="<%=product.getId()%>">
                                    <input type="submit" class="btn btn-light" value="Edit">
                                </form>
                            </div>
                            <%}%>
                        </div>
                    </div>
                </div>
                <% }
                }%>
            </div>
        </div>
    </div>
</div>
</body>
</html>