/*
    Helper functions for the first exercise of the Web Engineering course
*/
/*$(document).ready(function () {
    $(".product-price").each(function () {
        console.log($(this).text());
        $(this).html(formatCurrency(parseFloat($(this).text())));
        //format bigdecimal output, now replaced with getFormattedCurrentBid()
    })
})*/


/* 
    checks if native form validation is available.
    Source/Further informations: http://diveintohtml5.info/everything.html
*/
function hasFormValidation() {
    return 'noValidate' in document.createElement('form');
}

/* 
    checks if native date input is available.
    Source/Further informations: http://diveintohtml5.info/everything.html
*/
function hasNativeDateInput() {
    var i = document.createElement('input');
    i.setAttribute('type', 'date');
    return i.type !== 'text';
}

var DATE_DELIMITERS = ['/','\\','-'];

/*
    returns the string representation of a date input field in the format dd.mm.yyyy.
    If the value of the input field can't be interpreted as a date, the original value is returned.
*/
function getNormalizedDateString(selector) {
    value = $(selector).val();
    
    // normalize delimiter to .
    for(var i = 0; i < DATE_DELIMITERS.length; i++) 
        value = value.split(DATE_DELIMITERS[i]).join(".");
    
    // check if date might be reverse, i.e., yyyy.mm.dd
    rehtml5 = /^(\d{4})\.(\d{1,2})\.(\d{1,2})$/;
    if(regs = value.match(rehtml5))
        value = regs[3] + "." + regs[2] + "." + regs[1];

    // check if valid date string dd.mm.yyyy
    date = /^(\d{1,2})\.(\d{1,2})\.(\d{4})$/;
    if(value.match(date))
      return value;
    return $(selector).val();
}

/*
    returns the string representation of the given value (seconds) in the format mm:ss.
*/
function secToMMSS(value){
    var minutes = Math.floor(value / 60);
    var seconds = (value % 60);
    
    if(seconds < 10) {
        seconds = "0" + seconds;
    }
    if(minutes < 10) {
        minutes = "0" + minutes;
    }
    return minutes + ":" + seconds;
}

/* 
  checks if native form validation is available.
  Source/Further informations: http://diveintohtml5.info/storage.html
*/
function supportsLocalStorage() {
    try {
        return 'localStorage' in window && window['localStorage'] !== null;
    } catch(e) {
        return false;
    }
}

function writeNewText(el, secs) {
    if (secs > 0) {
        secs--;
        el.html(secToMMSS(secs));
    }
    else {
        el.html(el.data("end-text"));
        el.parents(".product").addClass("expired");
    }
}
$(".js-time-left").each(function() {
    var endTime = $(this).data("end-time").split(",");
    endTime = new Date(endTime[0],endTime[1]-1,endTime[2],endTime[3],endTime[4],endTime[5],endTime[6]);
    var today = new Date();
    var diffS = Math.round((endTime - today) / 1000);
    var that = $(this);
    writeNewText(that, diffS);
    setInterval(function () {
        if (diffS > 0) {
            diffS--;
        }
        writeNewText(that, diffS);
    }, 1000);
});

function formatCurrency(x) {
    // regex from http://stackoverflow.com/a/2901298
    return x.toFixed(2).replace(".", $("body").data('decimal-separator')).replace(/\B(?=(\d{3})+(?!\d))/g, $("body").data('grouping-separator')) + "&nbsp;€";
}

// Depending on the setup of your server, servlet, and socket, you may have to
// change the URL.
var serverLocation = "ws://localhost:8080/socket";

function start(websocketServerLocation){
    ws = new WebSocket(websocketServerLocation);
    ws.onmessage = function (event) {
        //testing stuff
        console.log("event data: " + event.data);
        if (event.data) {
            var msg = JSON.parse(event.data);
            switch(msg.type)
            {
                case "AUCTION_EXPIRED":
                    console.log("product id: "+ msg.productID);
                    console.log("current balance: " + msg.currentBalance);
                    console.log("runningBids: " + msg.runningBids);
                    console.log("wonAuctions: " + msg.wonAuctions);
                    console.log("lostAuctions: " + msg.lostAuctions);
                    //set attribute
                    $("[data-product-id="+ msg.productID + "] > a").toggleClass("expired");
                    $(".balance").text(msg.currentBalance + " €");
                    $(".running-auctions-count").text(msg.runningBids);
                    $(".won-auctions-count").text(msg.wonAuctions);
                    $(".lost-auctions-count").text(msg.lostAuctions);
                    break;
                case "NEW_BID":

                    $("[data-product-id="+ msg.productID + "] >  a > .product-properties.properties > .product-highest").text(msg.highestBidName);
                    $("[data-product-id="+ msg.productID + "] >  a > .product-properties.properties > .product-price").text(msg.bid + " €");
                    //also set highest bidder and bid in details (form) page
                    $("MAIN>DIV>FORM>LABEL>SPAN.highest-bidder").text(msg.highestBidName);
                    $("MAIN>DIV>FORM>LABEL>SPAN.highest-bid").text(msg.bid + " €");


                    break;
                case "OUTBIDDEN":
                    $("ASIDE>DIV>DL>DD>SPAN.balance").text(msg.newBalance + " €");
                    $("[data-product-id="+ msg.productID + "] > a").toggleClass("highlight", false);
                    break;

            }
        }
        else
        {

        }

    };
    ws.onclose = function(){
        //try to reconnect in 5 seconds
        setTimeout(function(){start(serverLocation)}, 5000);
    };
}

$(document).ready(function () {

    //local storage
    if (supportsLocalStorage()) {
        var links = JSON.parse(localStorage.getItem('links')) || [];
        // display
        var map = JSON.parse(localStorage.getItem('map')) || {};
        console.log(map);
        console.log(links);
        if (!jQuery.isEmptyObject(map)) {
            $(".recently-viewed-headline,.recently-viewed-list").css("display", "block");
            jQuery.each(map,function(link,name){
                    console.log("link="+link+" ,name="+name);
                    var linkStr = '<li class="recently-viewed-link"><a href="' + link + '">' + name + '</a></li>';
                    $(".recently-viewed-list").append(linkStr);
            });
        }
        // save
        if ($("#productheadline").length) {
            var headline = $("#productheadline");
            var name = headline.text();
            var link = location.pathname + location.search;
            var pair = {};
            if(-1==$.inArray(link,links)){
                links.push(link);
                map[link]=name;
            }
            if (links.length > 8) {
                var oldLink=links.shift();
                delete map[oldLink];
            }
            localStorage.setItem('links', JSON.stringify(links));
            localStorage.setItem('map', JSON.stringify(map));
        }
    }
    //start websocket
    start(serverLocation);
});

$(document).on("submit", ".bid-form", function(event) {

    var $form = $(this);
    //save bid for later
    var bid = $('.bid-form').find('input[name="new-price"]').val();

   $.post($form.attr("action"), $form.serialize(), function (data) {
       console.log("answer: " + data);
       var msg = JSON.parse(data);
       console.log("msg: " + msg);
       console.log("msg running auctions: " +msg.runningAuctions)
       console.log("msg new balance: " +msg.balance )
       switch (msg.status)
       {
           case "ok":
               $("MAIN>DIV>FORM>.bid-error").css("display","none");
               $("ASIDE>DIV>DL>DD>SPAN.running-auctions-count").text(msg.runningAuctions);
               $("ASIDE>DIV>DL>DD>SPAN.balance").text(msg.balance + " €");
               //also set highest bidder and new bid in details page
               var username = $(".main-container > .sidebar > " +
                   " .user-info-container > .user-data.properties > .user-name").text();
               $("MAIN>DIV>FORM>LABEL>SPAN.highest-bidder").text(username);
               $("MAIN>DIV>FORM>LABEL>SPAN.highest-bid").text(bid + " €");
               //set highlighting
               $("[data-product-id="+ msg.productID + "] > a").toggleClass("highlight");
               break;
           case "error":
               //alert(msg.text);
               $("MAIN>DIV>FORM>.bid-error").css("display","inline");
               break;
       }
   });


    event.preventDefault(); // Important! Prevents submitting the form.
});

