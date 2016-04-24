<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" class="at.ac.tuwien.big.we16.ue2.model.User" scope="session"/>
<!DOCTYPE html>
<html lang="de">
<head>
    <meta charset="utf-8"/>
    <title>BIG Bid - Produkte</title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="stylesheet" href="../styles/style.css"/>
</head>
<body data-decimal-separator="," data-grouping-separator=".">
    <%@ include file="header.jsp" %>
    <input type="hidden" id="refreshed" value="no"/>
    <script>
        onload=function(){
            var e=document.getElementById("refreshed");
            if(e.value=="no")e.value="yes";
            else{e.value="no";location.reload();}
        }
    </script>
    <div class="main-container">
    <%@include file="userinfo.jsp"%>
    <main aria-labelledby="productsheadline">
        <h2 class="main-headline" id="productsheadline">Produkte</h2>
        <div class="products">
            <c:forEach var="item" items="${products}">
                <c:choose>
                    <c:when test="${item.highest_bidder.username==user.username}">
                        <c:set var="productclass" value="product highlight"/>
                    </c:when>
                    <c:when test="${item.isExpired()}">
                        <c:set var="productclass" value="product expired"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="productclass" value="product"/>
                    </c:otherwise>
                </c:choose>
                <div class="product-outer" data-product-id="${item.id}">
                    <a href="
                        <c:url value="/DetailServlet">
                            <c:param name="id" value="${item.id}"/>
                        </c:url>"
                       class="${productclass}"
                       title="Mehr Informationen zu ${item.name}"
                    >
                        <img class="product-image" src="../images/${item.img}" alt="Product Image"/>
                        <dl class="product-properties properties">
                            <dt>Bezeichnung</dt>
                            <dd class="product-name">${item.name}</dd>
                            <dt>Preis</dt>
                            <c:choose>
                                <c:when test="${item.current_bid==null}">
                                    <dd class="product-price">
                                        Noch keine Gebote
                                    </dd>
                                </c:when>
                                <c:otherwise>
                                    <dd class="product-price">
                                        ${item.getFormattedCurrentBid()}
                                    </dd>
                                </c:otherwise>
                            </c:choose>
                            <dt>Verbleibende Zeit</dt>
                            <dd data-end-time="${item.getFormattedEndtime()}" data-end-text="abgelaufen"
                                class="product-time js-time-left"></dd>
                            <dt>Höchstbietende/r</dt>
                            <dd class="product-highest">${item.highest_bidder}</dd>
                        </dl>
                    </a>
                </div>
            </c:forEach>
        </div>
    </main>
</div>
    <%@ include file="footer.jsp" %>
</body>
</html>