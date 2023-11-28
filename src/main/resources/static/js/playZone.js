window.onload = function () {
    receiveDataFromSpring();
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
    return fetch('/getYamlData')
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

