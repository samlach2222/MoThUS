/**
 * Function to get the page skins from the server and display them
 */
function initPageSkinsContent() {
    let elementSkins = { // TODO : Get page skins from server
        "common": [
            {
                "id": "1",
                "rarity": "Common",
                "image": "/images/pageSkins/1.png",
                "equipped": "true"
            },
            {
                "id": "2",
                "rarity": "Uncommon",
                "image": "/images/pageSkins/2.png",
                "equipped": "false"
            },
            {
                "id": "3",
                "rarity": "Rare",
                "image": "/images/pageSkins/3.png",
                "equipped": "false"
            },
            {
                "id": "4",
                "rarity": "Mythic",
                "image": "/images/pageSkins/4.png",
                "equipped": "false"
            }
        ]
    }
    displaySkins(elementSkins, "page");
}