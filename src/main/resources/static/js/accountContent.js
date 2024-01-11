function initAccountContent() {
    let email = document.getElementById("email");
    let username = document.getElementById("username");
    email.addEventListener("click", displayEmailValidateButton);
    username.addEventListener("click", displayUsernameValidateButton);
}

function displayEmailValidateButton() {
    document.getElementById("emailChange").style.display = "unset";
}

function displayUsernameValidateButton() {
    document.getElementById("usernameChange").style.display = "unset";
}

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

function disconnect() {
    location.href='/logout'
}

function changeDisplayPassword() {
    let passwordDiv = document.getElementById("passwordDiv");
    passwordDiv.style.display = "none";
    let changePasswordDiv = document.getElementById("changePasswordDiv");
    changePasswordDiv.style.display = "unset";
}

function cancelChangeDisplayPassword() {
    let passwordDiv = document.getElementById("passwordDiv");
    passwordDiv.style.display = "unset";
    let changePasswordDiv = document.getElementById("changePasswordDiv");
    changePasswordDiv.style.display = "none";
}

function validatePasswordChange() {
    // TODO : Send password change request to server and change in the input
    cancelChangeDisplayPassword();
}