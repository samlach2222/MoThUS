/**
 * This function is called when the page is loaded to init click events on email and username inputs
 */
function initAccountContent() {
    let email = document.getElementById("email");
    let username = document.getElementById("username");
    email.addEventListener("click", displayEmailValidateButton);
    username.addEventListener("click", displayUsernameValidateButton);
}

/**
 * Function called when the email input is clicked to display the validate button
 */
function displayEmailValidateButton() {
    document.getElementById("emailChange").style.display = "unset";
}

/**
 * Function called when the username input is clicked to display the validate button
 */
function displayUsernameValidateButton() {
    document.getElementById("usernameChange").style.display = "unset";
}

/**
 * Function called when the email validation button is clicked to change the email in the database
 */
function validateEmailChange() {
    // get the value
    let email = document.getElementById("email");
    let emailValue = email.value;

    // check if the email is valid
    let emailRegex = /\S+@\S+\.\S+/;
    if (!emailRegex.test(emailValue)) {
        let xhr = new XMLHttpRequest();
        xhr.open('POST', '/getMessageInvalidEmail', false);  // The third parameter 'false' makes the request synchronous
        xhr.setRequestHeader('Content-Type', 'application/json');
        const token = document.head.querySelector('meta[name="_csrf"]').content;
        const header = document.head.querySelector('meta[name="_csrf_header"]').content;
        xhr.setRequestHeader(header, token);
        try {
            xhr.send();
            if (xhr.status === 200) {
                notifyError(xhr.responseText);
            } else {
                throw new Error('Network response was not ok');
            }
        } catch (err) {
            console.error(err);
        }
        return;
    }

    let message = "";

    // Construct the request body
    let requestBody = JSON.stringify({
        newMail: emailValue,
    });

    const token = document.head.querySelector('meta[name="_csrf"]').content;
    const header = document.head.querySelector('meta[name="_csrf_header"]').content;
    fetch('/changeMail', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [header]: token
        },
        body: requestBody
    })
        .then(response => response.text())
        .then(m => {
            message = m;
            if (message === "SUCCESS") {
                let xhr = new XMLHttpRequest();
                xhr.open('POST', '/getMessageEmailChangeSuccess', false);  // The third parameter 'false' makes the request synchronous
                xhr.setRequestHeader('Content-Type', 'application/json');
                const token = document.head.querySelector('meta[name="_csrf"]').content;
                const header = document.head.querySelector('meta[name="_csrf_header"]').content;
                xhr.setRequestHeader(header, token);
                try {
                    xhr.send();
                    if (xhr.status === 200) {
                        notifySuccess(xhr.responseText);
                    } else {
                        throw new Error('Network response was not ok');
                    }
                } catch (err) {
                    console.error(err);
                }
            } else {
                notifyError(message);
            }
            document.getElementById("emailChange").style.display = "none";
        })
        .catch(error => {
            console.error('Error fetching or parsing JSON data:', error);
            throw error; // Rethrow the error if necessary
        });
}

/**
 * Function called when the username validation button is clicked to change the username in the database

 */
function validateUsernameChange() {
    // get the value
    let username = document.getElementById("username");
    let usernameValue = username.value;

    // check if the username is valid
    let usernameRegex = /^[a-zA-Z0-9]+$/;
    if (!usernameRegex.test(usernameValue)) {
        let xhr = new XMLHttpRequest();
        xhr.open('POST', '/getMessageInvalidUsername', false);  // The third parameter 'false' makes the request synchronous
        xhr.setRequestHeader('Content-Type', 'application/json');
        const token = document.head.querySelector('meta[name="_csrf"]').content;
        const header = document.head.querySelector('meta[name="_csrf_header"]').content;
        xhr.setRequestHeader(header, token);
        try {
            xhr.send();
            if (xhr.status === 200) {
                notifyError(xhr.responseText);
            } else {
                throw new Error('Network response was not ok');
            }
        } catch (err) {
            console.error(err);
        }
        return;
    }
    let message = "";

    // Construct the request body
    let requestBody = JSON.stringify({
        newUsername: usernameValue,
    });

    const token = document.head.querySelector('meta[name="_csrf"]').content;
    const header = document.head.querySelector('meta[name="_csrf_header"]').content;
    fetch('/changeUsername', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [header]: token
        },
        body: requestBody
    })
        .then(response => response.text())
        .then(m => {
            message = m;
            if (message === "SUCCESS") {
                let xhr = new XMLHttpRequest();
                xhr.open('POST', '/getMessageUsernameChangeSuccess', false);  // The third parameter 'false' makes the request synchronous
                xhr.setRequestHeader('Content-Type', 'application/json');
                const token = document.head.querySelector('meta[name="_csrf"]').content;
                const header = document.head.querySelector('meta[name="_csrf_header"]').content;
                xhr.setRequestHeader(header, token);
                try {
                    xhr.send();
                    if (xhr.status === 200) {
                        notifySuccess(xhr.responseText);
                    } else {
                        throw new Error('Network response was not ok');
                    }
                } catch (err) {
                    console.error(err);
                }
                disconnect();
            } else {
                notifyError(message);
            }
            document.getElementById("usernameChange").style.display = "none";
        })
        .catch(error => {
            console.error('Error fetching or parsing JSON data:', error);
            throw error; // Rethrow the error if necessary
        });
}

/**
 * Function called when the user click on the disconnect button to disconnect the user
 */
function disconnect() {
    location.href = '/logout'
}

/**
 * Function called when the user click on the reset password button to display the change password div
 */
function changeDisplayPassword() {
    let passwordDiv = document.getElementById("passwordDiv");
    passwordDiv.style.display = "none";
    let changePasswordDiv = document.getElementById("changePasswordDiv");
    changePasswordDiv.style.display = "unset";
}

/**
 * Function called when the user click on the cancel button to cancel the password change
 */
function cancelChangeDisplayPassword() {
    let passwordDiv = document.getElementById("passwordDiv");
    passwordDiv.style.display = "unset";
    let changePasswordDiv = document.getElementById("changePasswordDiv");
    changePasswordDiv.style.display = "none";
}

/**
 * Function called when the user click on the validate button to validate the password change
 */
function validatePasswordChange() {
    let oldPassword = document.getElementById("oldPass").value;
    let newPassword = document.getElementById("newPass").value;
    let newPasswordConfirm = document.getElementById("confirmNewPass").value;

    let message = "";

    // Construct the request body
    let requestBody = JSON.stringify({
        oldPassword: oldPassword,
        newPassword: newPassword,
        newPasswordConfirm: newPasswordConfirm
    });

    const token = document.head.querySelector('meta[name="_csrf"]').content;
    const header = document.head.querySelector('meta[name="_csrf_header"]').content;
    fetch('/changePassword', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [header]: token
        },
        body: requestBody
    })
        .then(response => response.text())
        .then(m => {
            message = m;
            if (message === "SUCCESS") {
                let xhr = new XMLHttpRequest();
                xhr.open('POST', '/getMessagePasswordChangeSuccess', false);  // The third parameter 'false' makes the request synchronous
                xhr.setRequestHeader('Content-Type', 'application/json');
                const token = document.head.querySelector('meta[name="_csrf"]').content;
                const header = document.head.querySelector('meta[name="_csrf_header"]').content;
                xhr.setRequestHeader(header, token);
                try {
                    xhr.send();
                    if (xhr.status === 200) {
                        notifySuccess(xhr.responseText);
                    } else {
                        throw new Error('Network response was not ok');
                    }
                } catch (err) {
                    console.error(err);
                }
            } else {
                notifyError(message);
            }
            cancelChangeDisplayPassword();
        })
        .catch(error => {
            console.error('Error fetching or parsing JSON data:', error);
            throw error; // Rethrow the error if necessary
        });
}
