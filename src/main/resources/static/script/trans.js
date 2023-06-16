/**
 * 
 */
function startOCR() {
	$.ajax({
		url: '/wocr/start-OCR',
		type: 'GET',
		success: function(response) {
			// 서버에서의 처리가 성공한 경우에 실행할 동작을 작성합니다.
			console.log('OCR 실행 결과:', response);
			// response 변수에는 서버에서 반환한 result 값이 포함됩니다.
			$('#result').text(response); // 결과를 출력할 위치에 값 설정
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


// 페이지 로드 시 유튜브 API 초기화
$(document).ready(function() {
  onYouTubeIframeAPIReady();
});

var tag = document.createElement('script');

tag.src = "https://www.youtube.com/iframe_api";
var firstScriptTag = document.getElementsByTagName('script')[0];
firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

var player;
function onYouTubeIframeAPIReady() {
	var videoID = document.getElementsByName('videoCode')[0].value;
	
	player = new YT.Player('player', {
		height: '360',
		width: '640',
		videoId: videoID,
		events: {
		'onReady': onPlayerReady,
		'onStateChange': onPlayerStateChange
		}
	});
}
	
function onPlayerReady(event) {
	console.log(player.getDuration());
	//event.target.playVideo();
}

var done = false;
function onPlayerStateChange(event) {
	if (event.data == YT.PlayerState.PLAYING && !done) {
		setTimeout(stopVideo, 6000);
		done = true;
	}
}

function stopVideo() {
	player.stopVideo();
}




/* 웹페이지와 셀레니움 페이지 클릭 연동
function clickEventHandler(event) {
  var x = event.clientX;
  var y = event.clientY;

  console.log(x);
  // AJAX를 사용하여 좌표 값을 Spring 컨트롤러로 전송
  $.ajax({
    url: '/wocr/webClick',
    type: 'POST',
    data: {
      x: x,
      y: y
    },
    success: function(response) {
      console.log('Click event sent to Spring');
    },
    error: function(xhr, status, error) {
      console.log('Failed to send click event to Spring');
    }
  });
}

// 초기에 한 번 이벤트 리스너를 등록합니다
document.addEventListener('click', clickEventHandler);
*/

