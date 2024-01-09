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
    fetch('/timeBeforeValidationCode', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username: "username" }), // TODO : Get username from session
    })
        .then(response => response.text())
        .then(data => {
            let initialTime = parseInt(data);
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

function resendEmail() {
    let xhr = new XMLHttpRequest();
    xhr.open('POST', '/createNewValidationCode', false);  // The third parameter 'false' makes the request synchronous
    xhr.setRequestHeader('Content-Type', 'application/json');

    try {
        xhr.send(JSON.stringify("username")); // TODO : Get username from session
    }
    catch (err) {
        console.error(err);
    }
}