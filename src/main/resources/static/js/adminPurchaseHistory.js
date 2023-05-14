getUsersForSelect()

function getUsersForSelect() {
    const xhr = new XMLHttpRequest();
    const url = "http://localhost:8080/getUsersForSelect";

    xhr.open("GET", url, true);
    xhr.responseType = 'json';

    xhr.onload = function() {
        if (xhr.status === 200) {
            // Request was successful
            let responseData = xhr.response;
            // Process the response data
            fillInSelect(responseData)
        } else {
            // Request failed
            console.log('Request failed with status ' + xhr.status);
        }
    };

// Send the request
    xhr.send();
}

function fillInSelect(responseData) {
    const selectElement = document.getElementById('selectShopUsers');
    let data = responseData
    populateSelect()

// Function to populate the select element with options
    function populateSelect() {
        // Loop through the data array and create an option element for each item
        for (let i = 0; i < data.length; i++) {
            const option = document.createElement('option');
            option.value = data[i].username;
            option.textContent = data[i].username;
            selectElement.appendChild(option);
        }
    }
}

