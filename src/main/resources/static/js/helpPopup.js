/**
 * Function to display a word example with colors in the help popup
 */
function displayExemple() {
    let elts = [];
    let elt = {
        atomicNumber: 16,
        symbol: "S",
    };
    elts.push(elt);
    elt = {
        atomicNumber: 13,
        symbol: "Al",
    };
    elts.push(elt);
    elt = {
        atomicNumber: 92,
        symbol: "U",
    };
    elts.push(elt);
    elt = {
        atomicNumber: 73,
        symbol: "Ta",
    };
    elts.push(elt);
    elt = {
        atomicNumber: 22,
        symbol: "Ti",
    };
    elts.push(elt);
    elt = {
        atomicNumber: 8,
        symbol: "O",
    };
    elts.push(elt);
    elt = {
        atomicNumber: 7,
        symbol: "N",
    };
    elts.push(elt);
    elt = {
        atomicNumber: 16,
        symbol: "S",
    };
    elts.push(elt);

    const table = document.getElementById("mothusLettersExampleTable");
    const tableBody = document.createElement("tbody");

    const tableWidth = table.offsetWidth;
    const elmtWidthAndHeight = tableWidth / elts.length;
    table.style.height = elmtWidthAndHeight + "px";

    const row = document.createElement("tr");
    for (let i = 0; i < elts.length; i++) {
        // create all in row
        const cell = document.createElement("td");
        cell.appendChild(displayElement(elts[i]));
        row.appendChild(cell);
        const div = cell.getElementsByClassName("elementDiv")[0];
        div.style.height = "auto";
    }
    tableBody.appendChild(row);
    table.appendChild(tableBody);
    table.style.height = elmtWidthAndHeight + "px";

    for(let i = 0; i < elts.length; i++) {
        const cell = table.getElementsByTagName("td")[i];
        const div = cell.getElementsByClassName("elementDiv")[0];
        div.style.height = "auto";
        cell.style.border = "1px solid #494A4B";
        cell.style.color = "#FFFFFF";
        cell.style.borderRadius = "5px";
        cell.style.position = "relative";
        cell.style.width = elmtWidthAndHeight + "px";
        cell.style.height = elmtWidthAndHeight + "px";
        cell.style.maxHeight = elmtWidthAndHeight + "px";
        cell.style.minHeight = elmtWidthAndHeight + "px";

        switch (i) {
            case 0:
                cell.style.backgroundColor = "#b90022";
                break;
            case 1:
                cell.style.backgroundColor = "#b90022";
                break;
            case 2:
                cell.style.backgroundColor = "#005f9f";
                break;
            case 3:
                cell.style.backgroundColor = "#005f9f";
                break;
            case 4:
                cell.style.backgroundColor = "#b90022";
                break;
            case 5:
                cell.style.backgroundColor = "#005f9f";
                break;
            case 6:
                cell.style.backgroundColor = "#997100";
                break;
            case 7:
                cell.style.backgroundColor = "#7f00fd";
                break;
        }
    }
}