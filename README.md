## 프로젝트 목적 - WOCR

요즘 사람들은 영상에서 많은 정보를 얻는다. 하지만 영상의 특성상 보여지는 정보가 필요한 반면 단순 글자만 나열해서 읽어주는 효율이 나쁜 정보 전달이 있다. 영상을 글로 변환하면 정보의 습득 속도가 더 빠르기 때문에 영상의 글을 모아주는 사이트를 만들고자 하였다.

## 개발 기간 - WOCR
- 🕰️ 2023.05.28 ~ 2023.07.01

## 사용 기술 - WOCR

<img src="https://img.shields.io/badge/Java-blue?style=flat&logo=Java&logoColor=white"/> 17
<img src="https://img.shields.io/badge/Springboot-green?style=flat&logo=Springboot&logoColor=white"/> 3.11

<img src="https://img.shields.io/badge/HTML-red?style=flat&logo=html5&logoColor=white"/>
<img src="https://img.shields.io/badge/CSS-blue?style=flat&logo=css3&logoColor=white"/>
<img src="https://img.shields.io/badge/JavaScript-yellow?style=flat&logo=JavaScript&logoColor=white"/>
<img src="https://img.shields.io/badge/jQuery-blue?style=flat&logo=jquery&logoColor=black"/> 3.7.0.min

<img src="https://img.shields.io/badge/Git-orange?style=flat&logo=git&logoColor=white"/>
<img src="https://img.shields.io/badge/github-black?style=flat&logo=github&logoColor=white"/>

- API
    + selenium-java:4.9.1  
    + ashot:1.5.4  
    + tess4j:5.7.0  
    + google-cloud-translate:2.18.0  


## 프로젝트 소개 - WOCR

*처음 화면*

![chrome-capture-2023-6-11](https://github.com/trulyeven/trulyeven.github.io/assets/113951017/cb65eb4c-125e-48fa-b284-cd10e3834b2c)

유튜브 주소를 입력하면 OCR을 수행하는 창으로 이동한다
유효하지 않은 주소를 입력할 경우 경고메세지가 출력된다


*OCR 실행 화면*

![chrome-capture-2023-6-13](https://github.com/trulyeven/trulyeven.github.io/assets/113951017/0721962b-f14c-4189-bb20-3ecdeb45781e)

- 가운데 유튜브 창이 나오고 startOCR 버튼을 누르게 되면 영상의 이미지를 인식해서 OCR이 실행된다
- 실제로는 사용한 OCR이 이미지만 가능해서 selenium을 활용하여 screen샷을 찍고 그 결과를 OCR을 반복하여 출력하게 된다
- stopOCR 버튼을 누르면 OCR 기능이 중지된다
