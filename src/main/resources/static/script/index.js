/**
 * 
 */

 // 유효한 YouTube 비디오 ID 패턴
var youtubeIdPattern = /^[A-Za-z0-9_-]{11}$/;

// 입력 필드 값 변경 시 유효성 검사
document.getElementById('videoId').addEventListener('input', function() {
    var videoId = this.value;
    var validVideoId = youtubeIdPattern.test(videoId);
    this.setAttribute('disabled', !validVideoId);
    document.querySelector('button[type="submit"]').disabled = !validVideoId;
});