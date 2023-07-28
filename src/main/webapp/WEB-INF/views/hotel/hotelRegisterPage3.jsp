<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
window.onload = function(){
    document.getElementById("hostAddress").addEventListener("click", function(){ //주소입력칸을 클릭하면
        //카카오 지도 발생
        new daum.Postcode({
            oncomplete: function(data) { //선택시 입력값 세팅
                document.getElementById("hostAddress").value = data.address; // 주소 넣기
                document.querySelector("input[name=addressDetail]").focus(); //상세입력 포커싱
            }
        }).open();
    });
}
</script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=818488f03bbc3c53eaa216d3aaf39e13&libraries=services"></script>
<script type="text/javascript">

function getMap() {
	const addr1 = document.getElementById("hostAddress").value;
	const addr2 = document.getElementById("addressDetail").value;
	console.log(addr1);
	
	openMap(addr1 + " " + addr2, "내 숙소");
}


function openMap(compAddress,compName) {
	var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
	mapOption = {
	    center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
	    level: 3 // 지도의 확대 레벨
	};  
	
	//지도를 생성합니다    
	var map = new kakao.maps.Map(mapContainer, mapOption); 
	
	//주소-좌표 변환 객체를 생성합니다
	var geocoder = new kakao.maps.services.Geocoder();
	
	//주소로 좌표를 검색합니다
	geocoder.addressSearch(compAddress, function(result, status) {
	
	// 정상적으로 검색이 완료됐으면 
	 if (status === kakao.maps.services.Status.OK) {
	
	    var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
	
	    // 결과값으로 받은 위치를 마커로 표시합니다
	    var marker = new kakao.maps.Marker({
	        map: map,
	        position: coords
	    });
	
	    // 인포윈도우로 장소에 대한 설명을 표시합니다
	    var infowindow = new kakao.maps.InfoWindow({
	    		
	        content: '<div style="width:150px;text-align:center;padding:6px 0;">'+compName+'</div>'
	    });
	    infowindow.open(map, marker);
	
	    // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
	    map.setCenter(coords);
	} 
	
	}); 
	
}


/* document.addEventListener("DOMContentLoaded", function(event) {
	
	/* 주소 유효성 검사입니다. */
	function addressVcForm() {
		
		const addressVcDiv = document.getElementById("addressVc");
		
		const addressInput = document.querySelector("input[type='text'][name='hotel_address']");
		
		let isChecked = false;
		
		if (addressInput.value == "") {
			
			addressVcDiv.innerText = "*필수 입력사항 입니다. 주소를 입력해주세요.";
			addressVcDiv.style.fontSize = "0.9em";
			addressVcDiv.style.color = "red";
			
			addressInput.focus();
			
			return false;
		}
		
		return true;
		
	}
		
		document.getElementById("nextButton").addEventListener("click", function(event){
			
			if (!addressVcForm()) {
				
				event.preventDefault();
			}
			
		});
		
});
 */

</script>

 <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
   
    .nextButton {
	    width: auto;
	    height: auto;
	    background-image: linear-gradient(98deg,#4f4f4f,#4f4f4f);
	    border-radius: 10px;
	    border: none;
	    color: rgb(255, 255, 255);
	    padding: 10px 30px;
	    font-weight: 600;
	    font-size: 14px;
	    text-align: center;
    }
    
    .nextButton:hover {
    	background-image: linear-gradient(98deg,#4f4f4f,#333333);	
    }

    .box {
        width: auto;
        height: auto;
        border: solid rgb(205, 205, 205) 1px;
        border-radius: 10px;
        padding: 17px 15px;

    }
    
    a {
    	text-decoration: none;
    	font-weight: 600;
    	color: #595959;
    }
    
    a:hover {
		text-decoration: none;
		color: black;
	}
    </style>
</head>
<body>

<div class="container-fluid">
        <div class="row align-items-center py-4" style="position: fixed; top: 0; width: 100%; z-index: 1;">
            <div class="col px-5">
                <span style="color: #e7104a; font-size: 25px; font-weight: 600;">LOGO</span>
            </div>
            <div class="col"></div>
            <div class="col-auto px-5">
                <div>
                    <a href="/travel/hotel/hotelPage">나가기</a>
                </div>
            </div>
        </div>
		
		<form action="./hotelRegisterPage3Process" method="post">
        <div class="row" style="padding-top: 100px; padding-bottom: 100px;">
            <div class="col"></div>
            <div class="col">
                <div class="row my-3">
                    <div class="col">
                        <div style="font-size: 28px; font-weight: 600;">숙소 기본 사항 작성하기</div>
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        <div class="row my-4">
                            <div class="col">
                                <div style="font-size: 18px; font-weight: 600;">숙박 가능한 인원은 몇 명인가요?</div>
                            </div>
                        </div>
                        <div class="row align-items-center">
                            <div class="col">
                                <div style="font-size: 16px; font-weight: 500;">게스트</div>
                            </div>
	                       	<div class="col-auto">
	                            <select name="hotel_limit_number" class="form-control">
	                             	<option value="1">1</option>
	                            	<option value="2">2</option>
	                             	<option value="3">3</option>
	                             	<option value="4">4</option>
	                             	<option value="5">5</option>
	                             	<option value="6">6</option>
	                             	<option value="7">7</option>
	                             	<option value="8">8</option>
	                             	<option value="9">9</option>
	                             	<option value="10">10</option>
	                            </select>
	                        </div>
                        </div>
                        <hr class="my-3">
                        <div class="row align-items-center">
                            <div class="col">
                                <div style="font-size: 16px; font-weight: 500;">침실</div>
                            </div>
                            <div class="col-auto">
                                <select name="hotel_bedRoom" class="form-control">
                                	<option value="1">1</option>
                                	<option value="2">2</option>
                                	<option value="3">3</option>
                                	<option value="4">4</option>
                                	<option value="5">5</option>
                                	<option value="6">6</option>
                                	<option value="7">7</option>
                                	<option value="8">8</option>
                                	<option value="9">9</option>
                                	<option value="10">10</option>
                                </select>
                            </div>
                        </div>
                        <hr class="my-3">
                        <div class="row align-items-center">
                            <div class="col">
                                <div style="font-size: 16px; font-weight: 500;">욕실</div>
                            </div>
                           	<div class="col-auto">
	                            <select name="hotel_bathRoom" class="form-control">
	                             	<option value="1">1</option>
	                            	<option value="2">2</option>
	                             	<option value="3">3</option>
	                             	<option value="4">4</option>
	                             	<option value="5">5</option>
	                             	<option value="6">6</option>
	                             	<option value="7">7</option>
	                             	<option value="8">8</option>
	                             	<option value="9">9</option>
	                             	<option value="10">10</option>
	                            </select>
	                        </div>
                        </div>
                        <hr class="my-3">
                        <div class="row align-items-center">
                            <div class="col">
                                <div style="font-size: 16px; font-weight: 500;">침대</div>
                            </div>
                            <div class="col-auto">
	                            <select name="hotel_bed" class="form-control">
	                             	<option value="1">1</option>
	                            	<option value="2">2</option>
	                             	<option value="3">3</option>
	                             	<option value="4">4</option>
	                             	<option value="5">5</option>
	                             	<option value="6">6</option>
	                             	<option value="7">7</option>
	                             	<option value="8">8</option>
	                             	<option value="9">9</option>
	                             	<option value="10">10</option>
	                            </select>
	                        </div>
                        </div>
                        <div class="row mt-5 mb-3">
                            <div class="col">
                                <div style="font-size: 18px; font-weight: 600;">숙소 체크인, 체크아웃 시간을 알려주세요</div>
                            </div>
                        </div>
                        <div class="row align-items-center">
                            <div class="col-auto">
                                <div style="font-size: 16px; font-weight: 500;">체크인</div>
                            </div>
                            <div class="col-auto">
                                <div style="font-size: 16px; font-weight: 500;">
                                    <select id= "checkInTime" class="form-control" name="hotel_check_in_time">
                                        <option value="00">00:00</option>
                                        <option value="01">01:00</option>
                                        <option value="02">02:00</option>
                                        <option value="03">03:00</option>
                                        <option value="04">04:00</option>
                                        <option value="05">05:00</option>
                                        <option value="06">06:00</option>
                                        <option value="07">07:00</option>
                                        <option value="08">08:00</option>
                                        <option value="09">09:00</option>
                                        <option value="10">10:00</option>
                                        <option value="11">11:00</option>
                                        <option value="12">12:00</option>
                                        <option value="13">13:00</option>
                                        <option value="14">14:00</option>
                                        <option value="15">15:00</option>
                                        <option value="16">16:00</option>
                                        <option value="17">17:00</option>
                                        <option value="18">18:00</option>
                                        <option value="19">19:00</option>
                                        <option value="20">20:00</option>
                                        <option value="21">21:00</option>
                                        <option value="22">22:00</option>
                                        <option value="23">23:00</option>
                                        <option value="24">24:00</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-auto">
                                <div style="font-size: 16px; font-weight: 500;">체크아웃</div>
                            </div>
                            <div class="col-auto">
                                <div style="font-size: 16px; font-weight: 500;">
                                    <select id= "checkOutTime" class="form-control" name="hotel_check_out_time">
                                        <option value="00">00:00</option>
                                        <option value="01">01:00</option>
                                        <option value="02">02:00</option>
                                        <option value="03">03:00</option>
                                        <option value="04">04:00</option>
                                        <option value="05">05:00</option>
                                        <option value="06">06:00</option>
                                        <option value="07">07:00</option>
                                        <option value="08">08:00</option>
                                        <option value="09">09:00</option>
                                        <option value="10">10:00</option>
                                        <option value="11">11:00</option>
                                        <option value="12">12:00</option>
                                        <option value="13">13:00</option>
                                        <option value="14">14:00</option>
                                        <option value="15">15:00</option>
                                        <option value="16">16:00</option>
                                        <option value="17">17:00</option>
                                        <option value="18">18:00</option>
                                        <option value="19">19:00</option>
                                        <option value="20">20:00</option>
                                        <option value="21">21:00</option>
                                        <option value="22">22:00</option>
                                        <option value="23">23:00</option>
                                        <option value="24">24:00</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row mt-5 mb-3">
                            <div class="col">
                                <div style="font-size: 18px; font-weight: 600;">숙소 위치를 알려주세요</div>
                            </div>
                        </div>
                        <div class="row">
                        	<div class="col">
                        		<input class="form-control" type="text" id="hostAddress" placeholder="주소를 입력해주세요" name="hotel_address">
                        	</div>
                        </div>
                        <div class="row">
                        	<div class="col">
                        		<div id="addressVc"></div>
                        	</div>
                        </div>
                    </div> 
                </div>
            </div>
            <div class="col"></div>
        </div>
                

        <div class="row align-items-center py-4" style="position: fixed; bottom: 0; width: 100%; z-index: 1;">
            <div class="col-auto px-5">
                <span style="font-size: 14px; font-weight: 600;">뒤로</span>
            </div>
            <div class="col"></div>
            <div class="col-auto px-5">
                <button id="nextButton" class="nextButton">
                    <span>다음</span>
                </button>
            </div>
        </div>
        </form>
    </div>

            
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
        crossorigin="anonymous"></script>

</body>
</html>