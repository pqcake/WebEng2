<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="de">
<head>
    <meta charset="utf-8">
    <title>BIG Bid - Der Pate (Film)</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="../styles/style.css">
</head>
<body data-decimal-separator="," data-grouping-separator=".">

<%@ include file="header.jsp" %>

<div class="main-container">
    <%@include file="userinfo.jsp"%>
    <main aria-labelledby="productheadline" class="details-container">
        <div class="details-image-container">
            <img class="details-image" src="../images/${product.img}" alt="">
        </div>
        <div data-product-id="${product.id}" class="details-data">
            <h2 class="main-headline" id="productheadline">${product.name}</h2>

            <div class="auction-expired-text" <c:if test="${!product.isExpired()}">style="display:none"</c:if>>
                <p>
                    Diese Auktion ist bereits abgelaufen.
                    <c:if test="${product.highest_bidder!=null}">
                    Das Produkt wurde um
                    <span class="highest-bid">${product.getFormattedCurrentBid()}</span> an
                    <span class="highest-bidder">${product.highest_bidder.username}</span> verkauft.
                    </c:if>
                </p>
            </div>
            <c:if test="${!product.isExpired()}">
                <p class="detail-time">Restzeit: <span data-end-time="${product.getFormattedEndtime()}"
                                                       class="detail-rest-time js-time-left"></span>
                </p>
                <form class="bid-form" method="post" action="">
                    <label class="bid-form-field" id="highest-price">
                        <span class="highest-bid">${product.getFormattedCurrentBid()}</span>
                        <span class="highest-bidder">${product.highest_bidder.username}</span>
                    </label>
                    <label class="accessibility" for="new-price"></label>
                    <input type="number" step="0.01" min="0" id="new-price" class="bid-form-field form-input"
                           name="new-price" required>
                    <p class="bid-error">Es gibt bereits ein höheres Gebot oder der Kontostand ist zu niedrig.</p>
                    <input type="submit" id="submit-price" class="bid-form-field button" name="submit-price" value="Bieten">
                </form>
            </c:if>
        </div>
    </main>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>