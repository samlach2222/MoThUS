/**
 * Function called when the user clicks on the "Account" button in the vertical menu.
 */
window.onload = function () {
    loadContent('/elementCaseContent');
}

/**
 * Loads the content of the given url into the content div
 * @param url the url to load the content from
 */
function loadContent(url) {
    fetch(url)
        .then(response => response.text())
        .then(data => {
            document.getElementById('content').innerHTML = data;
            const script = document.createElement('script');
            switch (url) {
                case '/elementCaseContent':
                    break;
                case '/coinShopContent':
                    script.onload = function () {
                        initCoinShopContent();
                    };
                    script.src = "/js/coinShopContent.js";
                    document.head.appendChild(script);
                    break;
            }
            updateMargin();
        })
        .catch(error => console.error('Error:', error));
}

/**
 * Updates the margin of the content to match the width of the vertical menu
 */
function updateMargin() {
    const verticalMenuWidth = document.getElementById('verticalMenu').offsetWidth;
    document.getElementById('content').style.marginLeft = verticalMenuWidth + 'px';
}

function getSkin(type){
    // Construct the request body
    let requestBody = JSON.stringify({
        type: type,
    });

    const token = document.head.querySelector('meta[name="_csrf"]').content;
    const header = document.head.querySelector('meta[name="_csrf_header"]').content;
    fetch('/getRandomSkin', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [header]: token
        },
        body: requestBody
    })
        .then(response => response.text())
        .then(jsonData => {
            let json = JSON.parse(jsonData);
            console.log(json);
        })
        .catch(error => {
            console.error('Error fetching or parsing JSON data:', error);
            throw error; // Rethrow the error if necessary
        });
}

function actualizeMollards() {

    const token = document.head.querySelector('meta[name="_csrf"]').content;
    const header = document.head.querySelector('meta[name="_csrf_header"]').content;
    fetch('/getMollards', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [header]: token
        },
    })
        .then(response => response.text())
        .then(m => {
            let mollards = document.getElementById("coinCounter");
            if(mollards !== null){
                mollards.innerHTML = m;
            }
        })
        .catch(error => {
            console.error('Error fetching or parsing JSON data:', error);
            throw error; // Rethrow the error if necessary
        });
}