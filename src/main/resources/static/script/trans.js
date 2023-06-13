/**
 * 
 */

function startOCR() {
	console.log("asdfasdf");
	fetch('/start-OCR')
	    .then(response => response.text())
	    .then(result => console.log(result))
	    .catch(error => console.error(error));
}

function stopOCR() {
    fetch('/stop-OCR')
        .then(response => response.text())
        .then(result => console.log(result))
        .catch(error => console.error(error));
}