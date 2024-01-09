function initElementSkinsContent() {
    let elementSkins = { // TODO : Get element skins from server
        "common": [
            {
                "id": "1",
                "rarity": "Common",
                "image": "/images/elementSkins/1.png",
                "equipped": "true"
            },
            {
                "id": "2",
                "rarity": "Uncommon",
                "image": "/images/elementSkins/2.png",
                "equipped": "false"
            },
            {
                "id": "3",
                "rarity": "Rare",
                "image": "/images/elementSkins/3.png",
                "equipped": "false"
            },
            {
                "id": "4",
                "rarity": "Mythic",
                "image": "/images/elementSkins/4.png",
                "equipped": "false"
            }
        ]
    }
    displaySkins(elementSkins, "element");
}