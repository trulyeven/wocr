ocrInterval = true; // OCR 반복을 저장할 변수
var previousResult = ''; // result값 저장할 변수
responsearray = [];

function startOCR() {
	$.ajax({
		url: '/wocr/start-OCR',
		type: 'GET',
		success: function(response) {
		// 서버에서의 처리가 성공한 경우에 실행할 동작을 작성합니다.
			// console.log('OCR 실행 결과:', response);
      		if (response !== responsearray[0]) { // 현재 결과가 이전 결과와 다른 경우에만 처리
      			responsearray.unshift(response);
	        	$('#result').prepend(responsearray); // 결과를 출력할 위치에 값을 누적하여 추가
      		}
    	},
		error: function(xhr, status, error) {
	  	// 서버에서의 처리가 실패한 경우에 실행할 동작을 작성합니다.
		console.log('요청을 보낼 수 없습니다. 오류: ' + error);
	    }
	});
	if (ocrInterval == true) {
		setTimeout(function() {
			startOCR();
	  	}, 4000);
	} else {
		ocrInterval = true;  
	}
}


function stopOCR() {
	ocrInterval = false;
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
    height: '320',
    width: '640',
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

