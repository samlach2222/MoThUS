function initCoinShopContent() {

}

function setupInputs(){
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

function openPopup(contentType) {
    const popup = document.getElementById('popup');
    const body = document.body;

    // Add a class to the body to apply the overlay
    body.classList.add('overlay-active');

    // Load content based on contentType
    loadPopupContent(contentType);

    popup.style.display = 'block';

    // Close the popup and remove the overlay when clicking outside (grayed-out zone)
    window.onclick = function(event) {
        if (event.target === popup) {
            closePopup();
        }
    };
}

function closePopup() {
    const popup = document.getElementById('popup');
    const body = document.body;

    // Remove the overlay class from the body
    body.classList.remove('overlay-active');

    // Clear the window.onclick event to avoid interference with other click events
    window.onclick = null;

    popup.style.display = 'none';
}

function loadPopupContent(contentType) {
    const popupContent = document.querySelector('.popup-content');

    // Clear previous content
    popupContent.innerHTML = '';

    // Load content based on contentType
    switch (contentType) {
        case 'buyButton':
            fetch('/creditCardPopup')
                .then(response => response.text())
                .then(html => {
                    popupContent.innerHTML = html;
                    setupInputs();
                })
                .catch(error => {
                    console.error('Error loading content:', error);
                });
            break;
    }
}

function validatePayment(){
    // TODO: Validate payment
    closePopup();
}

function cancelPayment(){
    closePopup();
}