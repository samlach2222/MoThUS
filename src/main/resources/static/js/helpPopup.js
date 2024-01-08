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
    console.log(tableWidth);
    const elmtWidthAndHeight = tableWidth / elts.length;
    console.log(elmtWidthAndHeight);

    const row = document.createElement("tr");
    for (let i = 0; i < elts.length; i++) {
        // create all in row
        const cell = document.createElement("td");
        cell.style.border = "1px solid #494A4B";
        cell.style.color = "#FFFFFF";
        cell.style.borderRadius = "5px";
        cell.style.position = "relative";
        cell.style.width = elmtWidthAndHeight + "px";
        cell.style.height = elmtWidthAndHeight + "px";
        cell.style.maxHeight = elmtWidthAndHeight + "px";
        cell.style.minHeight = elmtWidthAndHeight + "px";
        cell.appendChild(displayElement(elts[i]));
        row.appendChild(cell);

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

        // TODO : compare CTRL F5 and F5 HTML

        const div = cell.getElementsByClassName("elementDiv")[0];
        div.style.height = "auto";
    }
    tableBody.appendChild(row);
    table.style.height = elmtWidthAndHeight + "px";
    table.appendChild(tableBody);
}