
var ocrInterval; // OCR 반복을 저장할 변수

function repeatOCR() {
  if (ocrInterval) {
      ocrInterval = setInterval(function() {
        startOCR();  // 2초마다 실행
      }, 2000);
  }
}

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
  clearInterval(ocrInterval); // OCR 실행 멈춤

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
  loadYouTubeAPI();
});

// 유튜브 API 스크립트 로드
function loadYouTubeAPI() {
  var tag = document.createElement('script');
  tag.src = "https://www.youtube.com/iframe_api";
  var firstScriptTag = document.getElementsByTagName('script')[0];
  firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
}

var player;

// YouTube IFrame API 로드 완료 시 호출되는 함수
function onYouTubeIframeAPIReady() {
  var videoID = document.getElementsByName('videoCode')[0].value;

  player = new YT.Player('player', {
    height: '640',
    width: '1280',
    videoId: videoID,
    events: {
      'onReady': onPlayerReady,
      'onStateChange': onPlayerStateChange
    }
  });
}

// 플레이어 준비 완료 시 호출되는 함수
function onPlayerReady(event) {
  console.log(player.getDuration());
  //event.target.playVideo();
}


// 플레이어 상태 변경 시 호출되는 함수
var playingTime = [];
var pausedTime = [];

function onPlayerStateChange(event) {
  if (event.data === YT.PlayerState.PLAYING) {
    // 재생 중일 때 현재 시간과 시스템 시간을 배열에 저장
    playingTime = [Math.floor(player.getCurrentTime()), new Date()];
    
    isplaying = true;
    sendIsPlaying(isplaying);
  }
   if (event.data === YT.PlayerState.PAUSED) { 
    // 멈추었을 때 현재 시간과 시스템 시간을 배열에 저장
    pausedTime = [Math.floor(player.getCurrentTime()), new Date()];
    
    isplaying = false;
    sendIsPlaying(isplaying);
	}
    if (pausedTime[0] !== playingTime[0]) {
      // 두 시간이 다르면 재생 시간과 시스템 시간을 전송
      var latestTime = pausedTime[1] > playingTime[1] ? pausedTime[0] : playingTime[0];
      sendCurrentTimeToServer(latestTime);
    }
    playingTime = pausedTime;
  
}
	

// AJAX를 사용하여 현재 재생 시간을 서버로 전송하는 함수
function sendCurrentTimeToServer(currentTime) {
  $.ajax({
    url: '/wocr/yotubeTime',
    type: 'POST',
    data: {
      currentTime: currentTime,
    },
    success: function(response) {
      console.log('Current time sent to server: ' + currentTime);
    },
    error: function(xhr, status, error) {
      console.log('Failed to send current time to server');
    }
  });
}

// 유투브 재생 유무 확인
function sendIsPlaying(isplaying) {
  $.ajax({
    url: '/wocr/yotubePlaying',
    type: 'POST',
    data: {
      isplaying: isplaying,
    },
    success: function(response) {
      console.log('isPlaying: ' + isplaying);
    },
    error: function(xhr, status, error) {
      console.log('Failed to send isPlaying status to server');
    }
  });
}

