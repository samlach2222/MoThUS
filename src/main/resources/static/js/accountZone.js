/**
 * Function called when the user clicks on the "Account" button in the vertical menu.
 */
window.onload = function () {
    loadContent('/accountContent');
    window.addEventListener('resize', updateMargin);
}

/**
 * Function called when the user clicks on a button in the vertical menu to display the content.
 * @param url the url of the content to display
 */
function loadContent(url) {
    fetch(url)
        .then(response => response.text())
        .then(data => {
            document.getElementById('content').innerHTML = data;
            const script = document.createElement('script');
            switch (url) {
                case '/accountContent':
                    script.onload = function () {
                        initAccountContent();
                    };
                    script.src = "/js/accountContent.js";
                    document.head.appendChild(script);
                    break;
                case '/elementSkinsContent':
                    script.onload = function () {
                        initElementSkinsContent();
                    };
                    script.src = "/js/elementSkinsContent.js";
                    document.head.appendChild(script);
                    break;
                case '/pageSkinsContent':
                    script.onload = function () {
                        initPageSkinsContent();
                    };
                    script.src = "/js/pageSkinsContent.js";
                    document.head.appendChild(script);
                    break;
                case '/termsOfUseContent':
                    break;
            }
            updateMargin();
        })
        .catch(error => console.error('Error:', error));
}

/**
 * Function to update the margin of the content when the window is resized.
 */
function updateMargin() {
    const verticalMenuWidth = document.getElementById('verticalMenu').offsetWidth;
    document.getElementById('content').style.marginLeft = verticalMenuWidth + 'px';
}

/**
 * Function to display a skin
 * @param elt the default element
 * @param type the type of the skin (page or element)
 * @returns {HTMLDivElement} the skin div
 */
function displaySkinsContent(elt, type) {
    // create <div> in elementSkinList (Element skin)
    let elementSkin = document.createElement("div");
    elementSkin.classList.add("elementSkin");
    elementSkin.id = elt.id;
    elementSkin.style.backgroundImage = "url(" + elt.image + ")";

    if(type === "element") {
        // create <p> in elementSkin (Element letter)
        let elementSkinRarity = document.createElement("p");
        elementSkinRarity.classList.add("elementSkinLetter");
        elementSkinRarity.innerHTML = "X";
        elementSkin.appendChild(elementSkinRarity);

        // create <p> in elementSkin (Element number)
        let elementSkinNumber = document.createElement("p");
        elementSkinNumber.classList.add("elementSkinNumber");
        elementSkinNumber.innerHTML = "0";
        elementSkin.appendChild(elementSkinNumber);
    }
    // create <div> in elementSkin (Element rarity)
    let elementSkinRarityDiv = document.createElement("div");
    elementSkinRarityDiv.classList.add("elementSkinRarity");

    switch (elt.rarity) {
        case "Common":
            elementSkinRarityDiv.style.background = "#009f63";
            elementSkinRarityDiv.innerHTML = document.getElementById("commonLabel").innerHTML;
            break;
        case "Uncommon":
            elementSkinRarityDiv.style.background = "#1075d6";
            elementSkinRarityDiv.innerHTML = document.getElementById("uncommonLabel").innerHTML;
            break;
        case "Rare":
            elementSkinRarityDiv.style.background = "linear-gradient(to bottom right, #c3ab1c, #ffdb00)";
            elementSkinRarityDiv.innerHTML = document.getElementById("rareLabel").innerHTML;
            break;
        case "Mythic":
            elementSkinRarityDiv.innerHTML = document.getElementById("mythicLabel").innerHTML;
            elementSkinRarityDiv.style.background = "linear-gradient(to bottom right, #fa6101, #cd0c07)";
            break;
        default:
            elementSkinRarityDiv.innerHTML = document.getElementById("commonLabel").innerHTML;
            elementSkinRarityDiv.style.background = "#009f63";
            break;
    }

    // create <div> in elementSkin (Element equipped)
    if(elt.equipped === "true") {
        let elementSkinEquipped = document.createElement("div");
        elementSkinEquipped.classList.add("elementSkinEquipped");
        elementSkinEquipped.innerHTML = document.getElementById("equippedLabel").innerHTML;
        elementSkin.appendChild(elementSkinEquipped);
    }

    elementSkin.appendChild(elementSkinRarityDiv);
    return elementSkin;
}

/**
 * Function to display the all the skins of the user
 * @param data the data to display
 * @param type the type of the skins (page or element), depends on the page
 */
function displaySkins(data, type) {
    let elts = [];
    Object.values(data).forEach(elementSkin => {
        elementSkin.forEach(elementSkin => {
            let elt = {
                rarity: elementSkin.rarity,
                image: elementSkin.image,
                id: elementSkin.id,
                equipped: elementSkin.equipped
            };
            elts.push(elt);
        });
    });
    let skinList = document.getElementById("skinList");
    for(let i = 0; i < elts.length; i++) {
        let elt = elts[i];
        let skin = displaySkinsContent(elt, type);
        skinList.appendChild(skin);
    }
}

