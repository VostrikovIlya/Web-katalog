<%@ page import="suai.webkatalog.model.Product" %>
<%@ page import="java.util.concurrent.ConcurrentSkipListSet" %>
<%@ page import="suai.webkatalog.model.Basket" %>
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
    %>
    <div class="container-fluid">
        <a class="navbar-brand" href="index.jsp">Home</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup"
                aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav">
                <%if (name != null) {%>
                <a class="nav-link active" aria-current="page" href="log-out-servlet"><%="LogOut"%>
                </a>
                <%}%>
            </div>
        </div>
    </div>
</nav>

<div class="container content">
    <div class="row m-4">
        <div class="col-md-8 products">
            <div class="row">
                <% Basket basket = (Basket) ses.getAttribute("Basket");
                    ConcurrentSkipListSet<Product> products = basket.getProducts();
                    int total = 0;
                    if (basket.size() == 0) {
                %>
                <h3><%="Basket empty!"%>
                </h3>
                <%
                    }
                    for (Product product : products) {
                        total += product.getPrice();
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
                            <%}%>
                            <form action="DeleteBasketServlet" method="post">
                                <input type="hidden" name="id" value="<%=product.getId()%>">
                                <input type="submit" class="btn btn-light" value="Delete">
                            </form>
                        </div>
                    </div>
                </div>
                <%} if (basket.size() != 0) {%>
                    <h2><%="Total: " + total + "$"%></h2>
                <%}%>
            </div>
        </div>
    </div>
</div>
</body>
</html>