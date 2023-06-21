/**
 * 
*/

// input 유튜브 주소 유효성 검사
document.addEventListener('DOMContentLoaded', function() {
    const input = document.getElementById('url');
    const submitButton = document.getElementById('submit');
    const inputalert = document.getElementById('inputalert');

    input.addEventListener('input', function() {
      const inputValue = input.value;
      const pattern = /(?:watch\?v=|\/videos\/|embed\/|youtu.be\/|\/v\/|\/e\/|watch\?v%3D|watch\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)([^#\&\?\n]*)/;
      const matchResult = inputValue.match(pattern);
      const videoCode = matchResult ? matchResult[1] : '';
  
      if (videoCode === null || videoCode === '') {
        submitButton.disabled = true;
        inputalert.hidden = false;
    } else {
        submitButton.disabled = false;
        inputalert.hidden = true;
      }
    });
  });
  