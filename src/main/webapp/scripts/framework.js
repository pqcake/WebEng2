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

$(document).ready(function () {

    //local storage
    if (supportsLocalStorage()) {
        // display
        var journey = JSON.parse(localStorage.getItem('journey')) || {};
        if (!jQuery.isEmptyObject(journey)) {
            $(".recently-viewed-headline,.recently-viewed-list").css("display", "block");
            jQuery.each(journey, function (link, name) {
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
            journey[link] = name;
            // journey.push(pair);
            localStorage.setItem('journey', JSON.stringify(journey));
        }
    }

// Depending on the setup of your server, servlet, and socket, you may have to
// change the URL.
    var socket = new WebSocket("ws://localhost:8080/socket");
    socket.onmessage = function (event) {
        //testing stuff
        console.log("event data: " + event.data);
        if (event.data) {
            var msg = JSON.parse(event.data);
            switch (msg.type) {
                case "AUCTION_EXPIRED":
                    console.log("product id: " + msg.productID);
                    console.log("current balance: " + msg.currentBalance);
                    console.log("runningBids: " + msg.runningBids);
                    console.log("wonAuctions: " + msg.wonAuctions);
                    console.log("lostAuctions: " + msg.lostAuctions);
                    //set attribute
                    $("[data-product-id=" + msg.productID + "] > a").toggleClass("expired");
                    $(".balance").text(msg.currentBalance);
                    $(".running-auctions-count").text(msg.runningBids);
                    $(".won-auctions-count").text(msg.wonAuctions);
                    $(".lost-auctions-count").text(msg.lostAuctions);
                    break;
                case "NEW_BID":

                    break;
                case "OUTBIDDEN":
                    break;

            }
        }
        else {

        }

    };
})