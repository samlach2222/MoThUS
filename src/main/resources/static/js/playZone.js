window.onload = function () {
    receiveDataFromSpring();
    receiveWordFromSpring();
}


function displayElement(element) {
    // Create a div for the element
    const elementDiv = document.createElement("div");
    elementDiv.classList.add("elementDiv");

    // Create a div for the element atomic number
    const elementAtomicNumberDiv = document.createElement("div");
    elementAtomicNumberDiv.classList.add("elementAtomicNumberDiv");
    elementAtomicNumberDiv.innerHTML = element.atomicNumber;
    elementDiv.appendChild(elementAtomicNumberDiv);

    // Create a div for the element letters
    const elementLettersDiv = document.createElement("div");
    elementLettersDiv.classList.add("elementLettersDiv");
    elementLettersDiv.innerHTML = element.symbol;
    elementDiv.appendChild(elementLettersDiv);

    return elementDiv;
}

function displayElementTable(data){
    // Create a table of 10 rows and 18 columns with the periodic table
    const periodicTable = document.getElementById("periodicHtmlTable");
    const periodicTableBody = document.createElement("tbody");

    let elts = [];
    Object.values(data).forEach(elements => {
        elements.forEach(element => {
            let elt = {
                atomicNumber: element.atomicNumber,
                symbol: element.symbol,
                name: element.name,
                xPos: element.xPos,
                yPos: element.yPos,
            };
            elts.push(elt);
        });
    });

    for (let i = 0; i < 10; i++) {
        const row = document.createElement("tr");
        for (let j = 0; j < 18; j++) {
            const cell = document.createElement("td");
            elts.forEach(elt => {
                if (elt.xPos === j && elt.yPos === i) {
                    cell.style.border = "1px solid #494A4B";
                    cell.style.color = "#FFFFFF";
                    cell.style.borderRadius = "5px";
                    cell.style.cursor = "pointer";
                    // style td:hover
                    cell.style.backgroundColor = "#494A4B";
                    cell.onmouseover = function () {
                        cell.style.backgroundColor = "#707070";
                    }
                    cell.onmouseout = function () {
                        cell.style.backgroundColor = "#494A4B";
                    }
                    cell.onclick = function () {
                        // TODO : Gameplay
                    }
                    // allow drag and drop
                    cell.draggable = true;
                    cell.ondragstart = function (event) {
                        event.dataTransfer.setData("text", elt.symbol);
                        // TODO : Gameplay
                    }
                    cell.appendChild(displayElement(elt));
                }
            })
            row.appendChild(cell);
        }
        periodicTableBody.appendChild(row);
    }
    periodicTable.appendChild(periodicTableBody);
}

function receiveDataFromSpring() {
    fetch('/getYamlData')
        .then(response => response.text())
        .then(yamlData => {
            // string to Yaml
            yamlData = jsyaml.load(yamlData);
            displayElementTable(yamlData);
        })
        .catch(error => {
            console.error('Error fetching or parsing YAML data:', error);
            throw error; // Rethrow the error if necessary
        });
}

function displayGameTable(length, firstLetter) {
    // create a table of 8 rows and "length" columns
    const gameTable = document.getElementById("mothusHtmlTable");
    const gameTableBody = document.createElement("tbody");
    for (let i = 0; i < 8; i++) {
        const row = document.createElement("tr");
        for (let j = 0; j < length; j++) {
            const cell = document.createElement("td");
            cell.style.color = "#FFFFFF";
            cell.style.borderRadius = "5px";

            if (i === 0 && j === 0) {
                cell.style.border = "1px solid #b90022";
                cell.style.backgroundColor = "#b90022";
                const elt = {
                    atomicNumber: 1,
                    symbol: firstLetter,
                };
                cell.appendChild(displayElement(elt));
            }
            else if (i ===0 && j >= 1) {
                cell.style.border = "1px solid #005f9f";
                cell.style.backgroundColor = "#005f9f";
                const elt = {
                    atomicNumber: 1,
                    symbol: ".",
                };
                cell.appendChild(displayElement(elt));
            }
            else {
                cell.style.border = "1px solid #005f9f";
                cell.style.backgroundColor = "#005f9f";
                const elt = {
                    atomicNumber: 1,
                    symbol: "&nbsp;",
                };
                cell.appendChild(displayElement(elt));
            }
            // allow drop
            cell.ondragover = function (event) {
                event.preventDefault();
            }

            cell.ondrop = function (event) {
                event.preventDefault();
                // Get the dragged text
                const draggedText = event.dataTransfer.getData("text");
                // Find the corresponding elementDiv in the dropped cell
                const elementDiv = cell.querySelector('.elementDiv');
                // Find the elementLettersDiv inside the elementDiv
                const elementLettersDiv = elementDiv.querySelector('.elementLettersDiv');
                // Set the innerHTML of elementLettersDiv
                elementLettersDiv.innerHTML = draggedText;
            }
            row.appendChild(cell);
        }
        gameTableBody.appendChild(row);
    }
    gameTable.appendChild(gameTableBody);
}

function receiveWordFromSpring() {
    fetch('/getTodayWordData')
        .then(response => response.text())
        .then(word => {
            let splitWord = word.split(" ");
            let length = splitWord[0];
            let firstLetter = splitWord[1];

            displayGameTable(length, firstLetter);
        })
        .catch(error => {
            console.error('Error fetching word data:', error);
            throw error; // Rethrow the error if necessary
        });
}

