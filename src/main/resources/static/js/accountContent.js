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
    // TODO : Send email change request to server and change in the input
    document.getElementById("emailChange").style.display = "none";
}

function validateUsernameChange() {
    // TODO : Send username change request to server and change in the input
    document.getElementById("usernameChange").style.display = "none";
}

function disconnect() {
    // TODO : Disconnect the account
    location.href='/login'
}