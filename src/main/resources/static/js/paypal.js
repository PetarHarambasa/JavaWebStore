import {totalPrice} from './merchCart.js';

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
                    value: totalPrice + 1
                }
            }]
        });
    },
    onApprove: function (data, actions) {
        // Capture the funds from the transaction
        return actions.order.capture().then(function (details) {
            // Show a success message to your buyer
            alert('Transaction completed by ' + details.payer.name.given_name + '!');
        });
    }
}).render('#paypal-button-container');