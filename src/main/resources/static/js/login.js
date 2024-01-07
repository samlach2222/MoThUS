window.onload = function () {
    if (document.getElementsByClassName('alert alert-registration-success').length > 0) {
        notifySuccess('Registration successful. You can now log in.')
        loadContent('/loginContent');
    } else if (document.getElementsByClassName('alert alert-registration-error').length > 0){
        notifyError('Registration failed. Username or email already exists.')
        loadContent('/registerContent');
    } else if (document.getElementsByClassName('alert alert-login-error').length > 0){
        notifyError('Login failed. Username or password is incorrect.')
        loadContent('/loginContent');
    } else if (document.getElementsByClassName('alert alert-registration-pending').length > 0){
        openPopup();
    } else if (document.getElementsByClassName('alert alert-registration-wrong-code').length > 0){
        notifyError('Invalid code. Please try again.');
        openPopup();
    }
}

function loadContent(url) {
    fetch(url)
        .then(response => response.text())
        .then(data => {
            document.getElementById('loginPanel').innerHTML = data;
        })
        .catch(error => console.error('Error:', error));
}

/////////////////
// OPEN POPUPS //
/////////////////

function openPopup() {
    const popup = document.getElementById('popup');
    const body = document.body;

    // Add a class to the body to apply the overlay
    body.classList.add('overlay-active');

    // Load content based on contentType
    loadPopupContent();
    popup.style.display = 'block';
    startTimer();
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

function loadPopupContent() {
    const popupContent = document.querySelector('.popup-content');
    fetch('/confirmEmailPopup')
        .then(response => response.text())
        .then(html => {
            popupContent.innerHTML = html;
        })
        .catch(error => {
            console.error('Error loading content:', error);
        });
}

////////////////////
// REGISTER TIMER //
////////////////////

// Update the timer and progress bar every second
function startTimer() {
    let timeRemaining = 60; // TODO: get time left from server

    const timerInterval = setInterval(function () {
        timeRemaining--;
        document.getElementById('timerValue').innerText = timeRemaining.toString();

        const progressPercentage = (timeRemaining / 60) * 100;
        document.getElementById('progressBar').style.width = progressPercentage + '%';

        if (timeRemaining <= 0) {
            clearInterval(timerInterval);
            // You can add code to handle what happens when the timer reaches 0
        }
    }, 1000);

    // TODO: implement resend email button + backend when 0 seconds
    // TODO: protect against spamming trying codes (long wait between tries, max tries, ...)
}