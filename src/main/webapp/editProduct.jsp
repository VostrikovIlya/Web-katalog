<%@ page import="suai.webkatalog.model.Categories" %>
<%@ page import="suai.webkatalog.model.Catalog" %>
<%@ page import="suai.webkatalog.model.Product" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
            crossorigin="anonymous"></script>

</head>
<body>
<nav class="navbar navbar-expand-sm navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="index.jsp">Home</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup"
                aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
    </div>
</nav>

<div class="container-sm">
    <div class="row m-4">
        <div class="col-md-4 products">
            <% Catalog catalog = Catalog.getInstance();
             String id = request.getParameter("id");
             if(id == null) {
                 response.sendRedirect(request.getHeader("referer"));
             }
                Product product = catalog.getProduct(id).orElseThrow(RuntimeException::new);
            %>
            <form action="EditProductServlet" method="post">
                <div class="mb-3">
                    <label for="Name" class="form-label">Title</label>
                    <input type="text" class="form-control" id="Name" name="name" required value="<%=product.getName()%>"
                           onBlur="if(this.value=='')this.value='<%=product.getName()%>'" onFocus="if(this.value=='<%=product.getName()%>') this.value=''">
                </div>
                <div class=" mb-3">
                    <label for="Number" class="form-label">Number</label>
                    <input type="number" min="1" class="form-control" id="Number" name="number" required value="<%=product.getNumber()%>"
                           onBlur="if(this.value=='')this.value='<%=product.getNumber()%>'" onFocus="if(this.value=='<%=product.getNumber()%>') this.value=''">
                </div>
                <div class="mb-3">
                    <label for="Price" class="form-label">Price</label>
                    <input type="number" min="1" class="form-control" id="Price" name="price" required value="<%=product.getPrice()%>"
                           onBlur="if(this.value=='')this.value='<%=product.getPrice()%>'" onFocus="if(this.value=='<%=product.getPrice()%>') this.value=''">
                </div>
                <div class="mb-3">
                    <select class="form-select" aria-label="Default select" name="categories">
                        <option selected>Categories</option>
                        <%
                            for (Categories categories : Categories.values()) {
                        %>
                        <option value="<%=categories.getName()%>"><%=categories%>
                        </option>
                        <% }%>
                    </select>
                </div>
                <%if (request.getAttribute("Error") != null) {%>
                <h3><%=(String) request.getAttribute("Error")%>
                </h3>
                <%}%>
                <div class="mb-3">
                    <input type="hidden" name="id" value="<%=product.getId()%>">
                    <input type="submit" class="btn btn-primary" value="Edit">
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>