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

function updateMargin() {
    const verticalMenuWidth = document.getElementById('verticalMenu').offsetWidth;
    document.getElementById('content').style.marginLeft = verticalMenuWidth + 'px';
}