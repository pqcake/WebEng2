<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" class="at.ac.tuwien.big.we16.ue2.model.User" scope="session"/>
<%@page contentType=" text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="de">
<head>
    <meta charset="utf-8"/>
    <title>BIG Bid - Produkte</title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="stylesheet" href="../styles/style.css"/>
</head>
<body data-decimal-separator="," data-grouping-separator=".">

<a href="#productsheadline" class="accessibility">Zum Inhalt springen</a>

<header aria-labelledby="bannerheadline">
    <img class="title-image" src="../images/big-logo-small.png" alt="BIG Bid logo"/>

    <h1 class="header-title" id="bannerheadline">
        BIG Bid
    </h1>
    <nav aria-labelledby="navigationheadline">
        <h2 class="accessibility" id="navigationheadline">Navigation</h2>
        <ul class="navigation-list">
            <li>
                <a href="" class="button" accesskey="l">Abmelden</a>
            </li>
        </ul>
    </nav>
</header>
<div class="main-container">
    <aside class="sidebar" aria-labelledby="userinfoheadline">
        <div class="user-info-container">
            <h2 class="accessibility" id="userinfoheadline">Benutzerdaten</h2>
            <dl class="user-data properties">
                <dt class="accessibility">Name:</dt>
                <dd class="user-name"><%=user.getUsername()%></dd>
                <dt>Kontostand:</dt>
                <dd>
                    <span class="balance"><%=user.getFormattedBalance()%> €</span>
                </dd>
                <dt>Laufend:</dt>
                <dd>
                    <span class="running-auctions-count">0</span>
                    <span class="auction-label" data-plural="Auktionen" data-singular="Auktion">Auktionen</span>
                </dd>
                <dt>Gewonnen:</dt>
                <dd>
                    <span class="won-auctions-count">0</span>
                    <span class="auction-label" data-plural="Auktionen" data-singular="Auktion">Auktionen</span>
                </dd>
                <dt>Verloren:</dt>
                <dd>
                    <span class="lost-auctions-count">0</span>
                    <span class="auction-label" data-plural="Auktionen" data-singular="Auktion">Auktionen</span>
                </dd>
            </dl>
        </div>
        <div class="recently-viewed-container">
            <h3 class="recently-viewed-headline">Zuletzt angesehen</h3>
            <ul class="recently-viewed-list"></ul>
        </div>
    </aside>
    <main aria-labelledby="productsheadline">
        <h2 class="main-headline" id="productsheadline">Produkte</h2>
        <div class="products">
            <c:forEach var="item" items="${products}">
                <div class="product-outer" data-product-id="${item.id}">
                    <a href="" class="product expired "
                       title="Mehr Informationen zu ${item.name}">
                        <img class="product-image" src="../images/${item.img}" alt=""/>
                        <dl class="product-properties properties">
                            <dt>Bezeichnung</dt>
                            <dd class="product-name">${item.name}</dd>
                            <dt>Preis</dt>
                            <dd class="product-price">
                                ${item.getFormattedCurrentBid()} €
                            </dd>
                            <dt>Verbleibende Zeit</dt>
                            <dd data-end-time="2016,03,14,14,30,23,288" data-end-text="abgelaufen"
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
<footer>
    © 2016 BIG Bid
</footer>
<script src="/scripts/jquery.js"></script>
<script src="/scripts/framework.js"></script>
</body>
</html>