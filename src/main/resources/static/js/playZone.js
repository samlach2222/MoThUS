window.onload = function () {
    currentLine = 0;
    firstLetter = "";
    receiveDataFromSpring();
    receiveWordFromSpring();
}

let currentLine;
let firstLetter;

//////////////////////////
//    GAME FUNCTIONS    //
//////////////////////////

/**
 * Display an element in the periodic table
 * @param element to display
 * @returns {HTMLDivElement} elementDiv
 */
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

/**
 * Display the periodic table
 * @param data from Spring
 */
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
                        // get first td of the current line
                        const gameTable = document.getElementById("mothusHtmlTable");
                        const gameTableBody = gameTable.getElementsByTagName("tbody")[0];
                        const row = gameTableBody.rows[currentLine];
                        const cells = row.cells;
                        for (let i = 0; i < cells.length; i++) {
                            const cell = cells[i];
                            const elementDiv = cell.querySelector('.elementDiv');
                            const elementLettersDiv = elementDiv.querySelector('.elementLettersDiv');
                            if (elementLettersDiv.innerHTML === ".") {
                                elementLettersDiv.innerHTML = elt.symbol;
                                break;
                            }
                        }
                    }
                    // allow drag and drop
                    cell.draggable = true;
                    cell.ondragstart = function (event) {
                        event.dataTransfer.setData("text", elt.symbol);
                    }
                    cell.appendChild(displayElement(elt));
                }
            })
            row.appendChild(cell);
        }
        periodicTableBody.appendChild(row);
    }

    // return button
    const row0 = periodicTableBody.rows[0];
    const row1 = periodicTableBody.rows[1];
    const row2 = periodicTableBody.rows[2];
    const cell1 = row0.cells[3];
    cell1.colSpan = 3;
    cell1.rowSpan = 3;
    // add button
    const returnButton = document.createElement("button");

    // backspace symbol innerHTML
    returnButton.innerHTML = "&#x232B;";
    returnButton.style.width = "100%";
    returnButton.style.height = "100%";
    returnButton.style.backgroundColor = "#b90022";
    returnButton.style.color = "white";
    returnButton.style.fontSize = "1.5em";
    returnButton.style.borderRadius = "8px";
    returnButton.style.border = "none";
    returnButton.style.cursor = "pointer";
    returnButton.style.display = "flex";
    returnButton.style.alignItems = "center";
    returnButton.style.justifyContent = "center";
    returnButton.onclick = function () {

    }
    cell1.appendChild(returnButton);

    // enter button
    const cell2 = row0.cells[6];
    cell2.colSpan = 3;
    cell2.rowSpan = 3;
    // add button
    const validateButton = document.createElement("button");

    // enter symbol innerHTML
    validateButton.innerHTML = "&#x23CE;";
    validateButton.style.width = "100%";
    validateButton.style.height = "100%";
    validateButton.style.backgroundColor = "#005f9f";
    validateButton.style.color = "white";
    validateButton.style.fontSize = "1.5em";
    validateButton.style.borderRadius = "8px";
    validateButton.style.border = "none";
    validateButton.style.cursor = "pointer";
    validateButton.style.display = "flex";
    validateButton.style.alignItems = "center";
    validateButton.style.justifyContent = "center";
    validateButton.onclick = function () {
        // call event listener on enter key
        const event = new KeyboardEvent('keydown', {
            key: 'Enter',
            code: 'Enter',
            which: 13,
            keyCode: 13,
            charCode: 13,
            view: window,
            bubbles: true
        });
        document.dispatchEvent(event);
    }
    cell2.appendChild(validateButton);

    // delete cells
    row0.deleteCell(7);
    row0.deleteCell(8);
    row0.deleteCell(9);
    row0.deleteCell(10);

    row1.deleteCell(7);
    row1.deleteCell(8);
    row1.deleteCell(9);
    row1.deleteCell(10);
    row1.deleteCell(11);
    row1.deleteCell(12);

    row2.deleteCell(7);
    row2.deleteCell(8);
    row2.deleteCell(9);
    row2.deleteCell(10);
    row2.deleteCell(11);
    row2.deleteCell(12);

    periodicTable.appendChild(periodicTableBody);
    activatePlayLine(currentLine);
}

/**
 * Display the game table when the page is loaded
 * @param length of the word
 */
function displayGameTable(length) {
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
            row.appendChild(cell);
        }
        gameTableBody.appendChild(row);
    }
    gameTable.appendChild(gameTableBody);
}

/**
 * Allow user to drag and drop or to click on the elements in the periodic table in a specific line of the game table
 */
function activatePlayLine(rowNumber) {
    const gameTable = document.getElementById("mothusHtmlTable");
    const gameTableBody = gameTable.getElementsByTagName("tbody")[0];

    for (let i = 0; i < 8; i++) {
        const row = gameTableBody.rows[i];
        const cells = row.cells;

        // for each cell in the row
        for (let j = 0; j < cells.length; j++) {
            const cell = cells[j];

            // allow drop
            if(i === rowNumber && j > 0) {
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
            }
            // disallow drop
            else {
                cell.ondragover = function (event) {
                    event.preventDefault();
                }

                cell.ondrop = function (event) {
                    event.preventDefault();
                }
            }
        }
    }
}

/**
 * Color the current line of the game table
 * @param coloration a string with + if the letter is correct, - if the letter is incorrect, * if the letter is correct but not in the right place
 */
function colorCurrentLine(coloration) {
    const gameTable = document.getElementById("mothusHtmlTable");
    const gameTableBody = gameTable.getElementsByTagName("tbody")[0];
    const row = gameTableBody.rows[currentLine];
    const cells = row.cells;
    for (let i = 0; i < cells.length; i++) {
        const cell = cells[i];
        const symbol = coloration[i];
        if(symbol === "+") {
            cell.style.backgroundColor = "#b90022";
        }
        else if(symbol === "-") {
            cell.style.backgroundColor = "#005f9f";
        }
        else if(symbol === "*") {
            cell.style.backgroundColor = "#997100";
        }
    }
}

//////////////////////////
//    KEYBOARD EVENT    //
//////////////////////////

/**
 * Catch keyboard events to send the current word to Spring or to delete a letter
 */
document.addEventListener('keydown', function(event) {
    if (event.code === 'Enter') {
        // check if the line is complete
        const gameTable = document.getElementById("mothusHtmlTable");
        const gameTableBody = gameTable.getElementsByTagName("tbody")[0];
        const row = gameTableBody.rows[currentLine];
        const cells = row.cells;
        let isComplete = true;
        for (let i = 0; i < cells.length; i++) {
            const cell = cells[i];
            const elementDiv = cell.querySelector('.elementDiv');
            const elementLettersDiv = elementDiv.querySelector('.elementLettersDiv');
            if (elementLettersDiv.innerHTML === ".") {
                isComplete = false;
            }
        }
        if (isComplete) {

            sendCurrentWord(); // Send to spring and color the line

            currentLine++;
            activatePlayLine(currentLine);

            // first letter
            const newRow = gameTableBody.rows[currentLine];
            const newCells = newRow.cells;
            for(let i = 0; i < newCells.length; i++) {
                const cell = newCells[i];
                if (i === 0 ) {
                    cell.style.border = "1px solid #b90022";
                    cell.style.backgroundColor = "#b90022";
                    cell.children[0].remove();
                    const elt = {
                        atomicNumber: 1,
                        symbol: firstLetter,
                    };
                    cell.appendChild(displayElement(elt));
                }
                else if (i >= 1) {
                    cell.style.border = "1px solid #005f9f";
                    cell.style.backgroundColor = "#005f9f";
                    cell.children[0].remove();
                    const elt = {
                        atomicNumber: 1,
                        symbol: ".",
                    };
                    cell.appendChild(displayElement(elt));
                }
                else {
                    cell.style.border = "1px solid #005f9f";
                    cell.style.backgroundColor = "#005f9f";
                    cell.children[0].remove();
                    const elt = {
                        atomicNumber: 1,
                        symbol: "&nbsp;",
                    };
                    cell.appendChild(displayElement(elt));
                }
            }
        }
        else {
            alert("La ligne n'est pas complète");
        }
    }
});

//////////////////////////
// SPRING COMMUNICATION //
//////////////////////////

/**
 * Fetch the first letter and the length of the word from Spring
 */
function receiveWordFromSpring() {
    fetch('/getTodayWordData')
        .then(response => response.text())
        .then(word => {
            let splitWord = word.split(" ");
            let length = splitWord[0];
            firstLetter = splitWord[1];

            displayGameTable(length);
        })
        .catch(error => {
            console.error('Error fetching word data:', error);
            throw error; // Rethrow the error if necessary
        });
}

/**
 * Fetch the elements from Spring
 */
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

/**
 * Send the current word to Spring
 * This is synchronous because we need the response before continuing
 */
function sendCurrentWord(){
    let word = "";
    const gameTable = document.getElementById("mothusHtmlTable");
    const gameTableBody = gameTable.getElementsByTagName("tbody")[0];
    const row = gameTableBody.rows[currentLine];
    const cells = row.cells;
    for (let i = 0; i < cells.length; i++) {
        const cell = cells[i];
        const elementDiv = cell.querySelector('.elementDiv');
        const elementLettersDiv = elementDiv.querySelector('.elementLettersDiv');
        word += elementLettersDiv.innerHTML;
    }

    // send the word to Spring
    let xhr = new XMLHttpRequest();
    xhr.open('POST', '/sendWord', false);  // The third parameter 'false' makes the request synchronous
    xhr.setRequestHeader('Content-Type', 'application/json');

    try {
        xhr.send(JSON.stringify(word));

        if (xhr.status === 200) {
            console.log('Response:', xhr.responseText);
            colorCurrentLine(xhr.responseText);
        } else {
            throw new Error('Network response was not ok');
        }
    } catch (error) {
        console.error('Error:', error);
    }

}