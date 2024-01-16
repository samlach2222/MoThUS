/**
 * Function to get the page skins from the server and display them
 */
function initPageSkinsContent() {

    let jsonData = "";

    fetch('/getPageSkinsData')
        .then(response => response.text())
        .then(jd => {
            jsonData = jd;
        })
        .catch(error => {
            console.error('Error fetching or parsing JSON data:', error);
            throw error; // Rethrow the error if necessary
        });

    displaySkins(jsonData, "page");
}