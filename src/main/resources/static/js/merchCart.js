let totalPrice = 0;

$(function () {
    const selectElement = document.querySelector('.table');
    const allPerAmountPrice = selectElement.querySelectorAll('.perAmount');
    const allAmountValues = selectElement.querySelectorAll('.amount');

    for (let i = 0; i < allPerAmountPrice.length; i++) {
        totalPrice += parseFloat(allPerAmountPrice[i].innerHTML.substring(0,
            allPerAmountPrice[i].innerHTML.length - 1)) * parseInt(allAmountValues[i].innerHTML);
    }
    document.cookie = "totalPriceCookie="+totalPrice;
    document.querySelector('.totalPrice').innerHTML = '$'+totalPrice;
});
