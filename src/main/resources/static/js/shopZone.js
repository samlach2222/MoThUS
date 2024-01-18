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

function getSkin(type) {
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

            if (jsonData === "null") {
                notifyError("You don't have enough Mollards to buy this item"); // TODO : Translate
            }
            let json = JSON.parse(jsonData);
            openPopup('loot', json);
        })
        .catch(error => {
            console.error('Error fetching or parsing JSON data:', error);
            throw error; // Rethrow the error if necessary
        });
}

/////////////////
// OPEN POPUPS //
/////////////////

/**
 * Function to open a popup
 * @param contentType the type of content to load in the popup
 */
function openPopup(contentType, json) {
    const popup = document.getElementById('popup');
    const body = document.body;

    // Add a class to the body to apply the overlay
    body.classList.add('overlay-active');

    // Load content based on contentType
    loadPopupContent(contentType, json);

    popup.style.display = 'block';

    // Close the popup and remove the overlay when clicking outside (grayed-out zone)
    window.onclick = function (event) {
        if (event.target === popup) {
            closePopup();
        }
    };
}

/**
 * Function to close a popup
 */
function closePopup() {
    const popup = document.getElementById('popup');
    const body = document.body;

    // Remove the overlay class from the body
    body.classList.remove('overlay-active');

    // Clear the window.onclick event to avoid interference with other click events
    window.onclick = null;

    popup.style.display = 'none';
}

/**
 * Function to load content in the popup
 * @param contentType the type of content to load in the popup
 */
function loadPopupContent(contentType, json) {
    const popupContent = document.querySelector('.popup-content');

    // Clear previous content
    popupContent.innerHTML = '';

    // Load content based on contentType
    switch (contentType) {
        case 'loot':

            const token = document.head.querySelector('meta[name="_csrf"]').content;
            const header = document.head.querySelector('meta[name="_csrf_header"]').content;
            fetch('/lootPopup', {
                headers: {
                    [header]: token
                }
            })
                .then(response => response.text())
                .then(html => {
                    popupContent.innerHTML = html;
                })
                .then(() => {
                    initLootPopup(json);
                })
                .catch(error => {
                    console.error('Error loading content:', error);
                    throw error; // Rethrow the error if necessary
                });
            break;
    }
}

function initLootPopup(json) {
    console.log(json);
    // return "{\"type\":\"" + s.getType() + "\",\"rarity\":\"" + s.getRarity() + "\",\"cssPath\":\"" + s.getCssFile() + "\",\"imagePath\":\"" + s.getPreviewImage() + "\"}";
    if (json.type.toLowerCase() === "elementskin") {
        let elementSkin = document.body.getElementsByClassName('elementSkin')[0];
        elementSkin.style.backgroundImage = "url('" + json.imagePath + "')";
        elementSkin.style.backgroundSize = "cover";
        elementSkin.style.backgroundPosition = "center";
        elementSkin.style.backgroundRepeat = "no-repeat";
        elementSkin.style.display = "flex";
        let elementSkinRarity = document.body.getElementsByClassName('elementSkinRarity')[0];
        elementSkinRarity.innerHTML = json.rarity;
    } else {
        let pageSkin = document.body.getElementsByClassName('pageSkin')[0];
        pageSkin.style.backgroundImage = "url('" + json.imagePath + "')";
        pageSkin.style.backgroundSize = "cover";
        pageSkin.style.backgroundPosition = "center";
        pageSkin.style.backgroundRepeat = "no-repeat";
        pageSkin.style.display = "flex";
        let pageSkinRarity = document.body.getElementsByClassName('pageSkinRarity')[0];
        pageSkinRarity.innerHTML = json.rarity;
    }
}