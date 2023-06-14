/**
 * 
 */
function startOCR() {
  $.ajax({
    url: '/wocr/start-OCR',
    type: 'GET',
    success: function(response) {
      // 서버에서의 처리가 성공한 경우에 실행할 동작을 작성합니다.
      console.log('OCR 실행');
    },
    error: function(xhr, status, error) {
      // 서버에서의 처리가 실패한 경우에 실행할 동작을 작성합니다.
      console.log('요청을 보낼 수 없습니다. 오류: ' + error);
    }
  });
}

function stopOCR() {
  $.ajax({
    url: '/wocr/stop-OCR',
    type: 'GET',
    success: function(response) {
      // 서버에서의 처리가 성공한 경우에 실행할 동작을 작성합니다.
      console.log('OCR 종료');
    },
    error: function(xhr, status, error) {
      // 서버에서의 처리가 실패한 경우에 실행할 동작을 작성합니다.
      console.log('요청을 보낼 수 없습니다. 오류: ' + error);
    }
  });
}


/*
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
*/