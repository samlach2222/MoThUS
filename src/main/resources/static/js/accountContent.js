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
        notifyError("Invalid email") // TODO : translate
        return;
    }
    // TODO : Send email change request to server and change in the input
    document.getElementById("emailChange").style.display = "none";
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
        notifyError("Invalid username") // TODO : translate
        return;
    }
    // TODO : Send username change request to server and change in the input
    document.getElementById("usernameChange").style.display = "none";
}

/**
 * Function called when the user click on the disconnect button to disconnect the user
 */
function disconnect() {
    location.href='/logout'
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
    // TODO : Send password change request to server and change in the input
    cancelChangeDisplayPassword();
}