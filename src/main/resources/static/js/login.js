/**
 * Function called when the user visit the login page.
 * It redirects to loginContent or registerContent based and display a notification if needed.
 */
window.onload = function () {
    if (document.getElementsByClassName('alert alert-registration-success').length > 0) {
        let text = document.getElementsByClassName('alert alert-registration-success')[0].innerText;
        notifySuccess(text)
        loadContent('/loginContent');
    } else if (document.getElementsByClassName('alert alert-registration-error').length > 0){
        let text = document.getElementsByClassName('alert alert-registration-error')[0].innerText;
        notifyError(text)
        loadContent('/registerContent');
    } else if (document.getElementsByClassName('alert alert-registration-password-error').length > 0) {
        notifyError('Registration failed. Password and confirmed password do not match.')
        loadContent('/registerContent');
    } else if (document.getElementsByClassName('alert alert-login-error').length > 0){
        let text = document.getElementsByClassName('alert alert-login-error')[0].innerText;
        notifyError(text)
        loadContent('/loginContent');
    } else if (document.getElementsByClassName('alert alert-registration-pending').length > 0){
        openPopup();
    } else if (document.getElementsByClassName('alert alert-registration-wrong-code').length > 0){
        let text = document.getElementsByClassName('alert alert-registration-wrong-code')[0].innerText;
        notifyError(text);
        openPopup();
    }
}

/**
 * Function used to load the content of an url.
 * @param url the url of the content to load.
 */
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

/**
 * Function to open a popup.
 */
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

/**
 * Function to load the content of the popup.
 */
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

/**
 * Function to start the timer of the register popup.
 */
function startTimer() {
    const token = document.head.querySelector('meta[name="_csrf"]').content;
    const header = document.head.querySelector('meta[name="_csrf_header"]').content;
    fetch('/timeBeforeValidationCode', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [header]: token,
        }
    })
        .then(response => response.text())
        .then(data => {
            let initialTime = parseInt(data);
            console.log(data);
            let timeRemaining = initialTime;
            document.getElementById('timerValue').innerText = timeRemaining.toString();

            const timerInterval = setInterval(function () {
                timeRemaining--;
                document.getElementById('timerValue').innerText = timeRemaining.toString() + 's';

                const progressPercentage = (timeRemaining / initialTime) * 100;
                document.getElementById('progressBar').style.width = progressPercentage + '%';

                if (timeRemaining <= 0) {
                    clearInterval(timerInterval);
                    document.getElementById('PopupLabel').innerText = 'Time expired, please use resend code button.';
                    document.getElementById('confirmEmailForm').style.display = 'none';
                    document.getElementById('resendCodeForm').style.display = 'flex';
                    document.getElementById('timerContainer').style.display = 'none';
                }
            }, 1000);
        })
        .catch(error => {
            console.error('Error fetching or parsing data:', error);
            throw error; // Rethrow the error if necessary
        });
}

/**
 * Function to resend the validation email.
 */
function resendEmail() {
    let xhr = new XMLHttpRequest();
    xhr.open('POST', '/createNewValidationCode', false);  // The third parameter 'false' makes the request synchronous
    xhr.setRequestHeader('Content-Type', 'application/json');
    const token = document.head.querySelector('meta[name="_csrf"]').content;
    const header = document.head.querySelector('meta[name="_csrf_header"]').content;
    xhr.setRequestHeader(header, token);

    try {
        xhr.send();
    }
    catch (err) {
        console.error(err);
    }
}