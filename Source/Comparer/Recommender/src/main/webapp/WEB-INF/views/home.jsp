<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Recommender</title>

<script type="text/javascript" src="resources/js/jquery-2.0.0.min.js"></script>
<link rel="stylesheet"
	href="resources/libs/bootstrap-3.3.6-dist/css/bootstrap.min.css">
<script type="text/javascript"
	src="resources/libs/bootstrap-3.3.6-dist/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="resources/js/bootstrap-filestyle.min.js"></script>
<link rel="stylesheet" href="resources/css/main.css">

</head>
<body>
	<!-- include header file -->
	<jsp:include page="header.jsp"></jsp:include>

	<div class="container">
		<div class="row" style="margin: 0 auto;">
			<pre class="text-info" style="font-size: 15px;">
<h3 class="text-primary">Để chạy được thuật toán cần cung cấp đầy đủ các file dataset có định dạng như sau:</h3>
- File upload có định dạng là file text (*.txt)
- Các cột cách nhau bằng ký tự tab (\t)
- File Job.txt gồm các cột sau: JobId, JobName, Location, Salary, Category, Requirement, Tag, Description.
- File CV.txt gồm các cột sau: UserId, CVId, UserName, CVName, UserAddress, ExpectedSalary, Category, Language, Skill, CareerObjective.
- File Score.txt gồm các cột sau: UserId, JobId, Score.</pre>
			<form class="form" role="form" action="excute" method="post"
				enctype="multipart/form-data">
				<label for="db">Chọn File Job.txt</label> <input type="file"
					class="filestyle" data-buttonName="btn-primary" name="file"
					data-buttonText="Chọn file" data-buttonBefore="true" id="score">
				<label for="db">Chọn File CV.txt</label> <input type="file"
					class="filestyle" data-buttonName="btn-primary" name="file"
					data-buttonText="Chọn file" data-buttonBefore="true" id="score">
				<label for="db">Chọn File Score.txt</label> <input type="file"
					class="filestyle" data-buttonName="btn-primary" name="file"
					data-buttonText="Chọn file" data-buttonBefore="true" id="score">
				<br> <label>Chọn thuật toán</label>
				<div class="radio">
					<label> <input type="radio" name="optradio">Collaborative
						Filtering
					</label>
				</div>
				<div class="radio">
					<label> <input type="radio" name="optradio">Content
						Base
					</label>
				</div>
				<div class="radio">
					<label> <input type="radio" name="optradio">Hybrid
					</label>
				</div>
				<br> <input class="btn btn-primary" type="submit" value="Xử lý">
			</form>
		</div>
	</div>
</body>
</html>