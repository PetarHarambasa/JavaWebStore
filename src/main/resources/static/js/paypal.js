function getCookieValue(cookieName) {
    const name = cookieName + "=";
    const decodedCookie = decodeURIComponent(document.cookie);
    const cookieArray = decodedCookie.split(';');

    for(let i = 0; i < cookieArray.length; i++) {
        let cookie = cookieArray[i];
        while (cookie.charAt(0) == ' ') {
            cookie = cookie.substring(1);
        }
        if (cookie.indexOf(name) == 0) {
            return cookie.substring(name.length, cookie.length);
        }
    }
    return "";
}

paypal.Buttons({
    style: {
        layout: 'vertical',
        shape: 'pill',
    },
    createOrder: function (data, actions) {
        // Set up the transaction
        return actions.order.create({
            purchase_units: [{
                amount: {
                    value: parseFloat(getCookieValue('totalPriceCookie'))
                }
            }]
        });
    },
    onApprove: function (data, actions) {
        // Capture the funds from the transaction
        return actions.order.capture().then(function (details) {
            // Show a success message to your buyer
            alert('Transaction completed by ' + details.payer.name.given_name + '!');

            setTimeout(function () {

                const xhr = new XMLHttpRequest();
                const url = "http://localhost:8080/userHistory";

                xhr.open("POST", url, true);
                xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

                const paramTypeOfPurchase = "PAYPAL";
                const paramTotalPrice = parseFloat(getCookieValue('totalPriceCookie'))

                xhr.onreadystatechange = function() {
                    if (xhr.readyState === XMLHttpRequest.DONE) {
                        if (xhr.status === 200) {
                            // Request was successful
                            window.location.href = url;
                        } else {
                            // Request failed
                            console.error("Error: " + xhr.status);
                        }
                    }
                };

                const data = `typeOfPurchase=${paramTypeOfPurchase}&totalPrice=${paramTotalPrice}`;

                xhr.send(data);

                // Code to be executed after 2 seconds
            }, 1000);
        });
    }
}).render('#paypal-button-container');