/**
 * Function called when the user clicks on the "Coin Shop" button in the vertical menu.
 */
function initCoinShopContent() {
}

/**
 * Function called when the user^open the pay invoice popup button in the vertical menu to set up creditCard inputs.
 */
function setupInputs() {
    let cleaveCNI = new Cleave('#cczCardNumberInput', {
        creditCard: true,
    });
    let cleaveEDI = new Cleave('#cczExpDateInput', {
        date: true,
        datePattern: ['m', 'y']
    });

    let validate = document.getElementById("cczPayButton");
    let cancel = document.getElementById("cczCancelButton");
    validate.addEventListener("click", validatePayment);
    cancel.addEventListener("click", cancelPayment);
}

/////////////////
// OPEN POPUPS //
/////////////////

/**
 * Function to open a popup
 * @param contentType the type of content to load in the popup
 */
function openPopup(contentType, lootboxType) {
    const popup = document.getElementById('popup');
    const body = document.body;

    // Add a class to the body to apply the overlay
    body.classList.add('overlay-active');

    // Load content based on contentType
    loadPopupContent(contentType, lootboxType);

    popup.style.display = 'block';
}

/**
 * Function to close a popup
 */
function closePopup() {
    const popup = document.getElementById('popup');
    const body = document.body;

    // Remove the overlay class from the body
    body.classList.remove('overlay-active');

    // Clear the window.onclick event to avoid interference with other click events
    window.onclick = null;

    popup.style.display = 'none';
}

/**
 * Function to load content in the popup
 * @param contentType the type of content to load in the popup
 */
function loadPopupContent(contentType, lootboxType) {
    const popupContent = document.querySelector('.popup-content');

    // Clear previous content
    popupContent.innerHTML = '';

    // Load content based on contentType
    switch (contentType) {
        case 'buyButton':
            fetch('/creditCardPopup?lootboxType=' + lootboxType)
                .then(response => response.text())
                .then(html => {
                    popupContent.innerHTML = html;
                    setupInputs();
                })
                .catch(error => {
                    console.error('Error loading content:', error);
                    throw error; // Rethrow the error if necessary
                });
            break;
    }
}

/**
 * Function called when the user click on the validate payment button in the popup.
 */
function validatePayment() {

    // Construct the request body
    let requestBody = JSON.stringify({
        amount: document.getElementById("coinNumber").innerText,
    });

    const token = document.head.querySelector('meta[name="_csrf"]').content;
    const header = document.head.querySelector('meta[name="_csrf_header"]').content;
    fetch('/payMollard', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [header]: token
        },
        body: requestBody
    })
        .then(response => response.text())
        .then(r => {
            if (r === "OK") { // TODO : NotifySuccess and NotifyError not found
                notifySuccess("Payment successful"); // TODO : translate
            } else {
                notifyError(r);
            }
        })
        .catch(error => {
            console.error('Error fetching or parsing JSON data:', error);
            throw error; // Rethrow the error if necessary
        });
    // TODO : Actualize mollards
    closePopup();
}

/**
 * Function called when the user click on the cancel payment button in the popup.
 */
function cancelPayment() {
    closePopup();
}