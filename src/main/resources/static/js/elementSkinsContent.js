/**
 * Function to get the element skins from the server and display them
 */
function initElementSkinsContent() {

    let jsonData = "";

    fetch('/getElementSkinsData')
        .then(response => response.text())
        .then(jd => {
            jsonData = jd;
            displaySkins(JSON.parse(jsonData), "element");
        })
        .catch(error => {
            console.error('Error fetching or parsing JSON data:', error);
            throw error; // Rethrow the error if necessary
        });


}