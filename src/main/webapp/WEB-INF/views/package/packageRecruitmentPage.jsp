<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<title>패키지 파티 모집 페이지</title>
</head>
<body>
	<div class="container">
		<jsp:include page="../common/mainTopNavi.jsp"></jsp:include>
		
		<div class="row">
			<div class="col">
				<form action="./packageRecruitmentProcess?plan_id=${plan_id}" method="post">
					<div class="row">
						<div class="col">
							<div class="row mt-1">
								<div class="col">
									<input class="form-control" type="text" placeholder="제목" name="guide_planning_title">
								</div>
							</div>
							<div class="row mt-1">
								<div class="col">
									<input class="form-control" type="text" placeholder="내용" name="guide_planning_content">
								</div>
							</div>
							<div class="row mt-1">
								<div class="col">
								
									 <label for="guide_planning_start_date" class="small-text">*여행 출발 날짜</label>
									<input class="form-control" type="date" id="planning_start_date" name="guide_planning_start_date" >
									
								</div>
							</div>
							<div class="row mt-1">
								<div class="col">
									 <label for="guide_planning_end_date" class="small-text">*모집 종료 날짜</label>
									<input class="form-control" type="date" id="planning_end_date" name="guide_planning_end_date" >
								</div>
							</div>
							<div class="row mt-1">
								<div class="col">
									<input class="form-control" type="text" placeholder="모집 인원" name="guide_planning_member" >
								</div>
							</div>
							<div class="row mt-1">
								<div class="col">
									<input class="form-control" type="text" placeholder="모집 위치" name="guide_planning_start_point">
								</div>
							</div>
							<div class="row mt-1">
								<div class="col">
									<input class="form-control" type="text" placeholder="1인당 비용" name="guide_planning_price">
								</div>
							</div>
							<div class="row mt-1">
								<div class="col">
									<input class="form-control" type="hidden" name="guide_id" value="${guide_id}">
								</div>
								
							</div>
						</div>
						
					</div>
					<div class="row">
						<div class="col">
							<hr>
						</div>
					</div>
					<div class="row">
						<div class="col-8"></div>
						<div class="col d-grid">
							<button class="btn btn-outline-primary" name="action" value="temp_save">임시저장</button>
						</div>
						<div class="col d-grid">
							<button class="btn btn-outline-primary" name="action" value="recruit">모집하기</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
</body>
</html>