<%@page import="java.util.List"%>
<%@page import="org.apache.mahout.cf.taste.recommender.RecommendedItem"%>
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

<!-- jquery -->
<script type="text/javascript" src="resources/js/jquery-2.0.0.min.js"></script>

<!-- bootstrap -->
<link rel="stylesheet"
	href="resources/libs/bootstrap-3.3.6-dist/css/bootstrap.min.css">
<script type="text/javascript"
	src="resources/libs/bootstrap-3.3.6-dist/js/bootstrap.min.js"></script>

<!-- bootstrap upload file style -->
<script type="text/javascript"
	src="resources/js/bootstrap-filestyle.min.js"></script>

<!-- datatable -->
<link
	href="resources/libs/DataTables-1.10.9/css/jquery.dataTables.min.css"
	rel="stylesheet">
<script type="text/javascript"
	src="resources/libs/DataTables-1.10.9/js/jquery.dataTables.min.js"></script>
<script type="text/javascript"
	src="resources/libs/DataTables-1.10.9/js/jquery.dataTables.js"></script>
<!-- custom css -->
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
- Các cột cách nhau bằng ký tự tab (\t). Các file có thứ tự các cột đúng theo thứ tự bên dưới.
- File Job.txt gồm: JobId, JobName, Location, Salary, Category, Requirement, Tag, Description.
- File CV.txt gồm: UserId, CVId, UserName, CVName, UserAddress, ExpectedSalary, Category, Education, Language, Skill, CareerObjective.
- File Score.txt gồm: UserId, JobId, Score.</pre>
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
					<label> <input checked="checked" type="radio"
						name="algorithm" value="cf">Collaborative Filtering
					</label>
				</div>
				<div class="radio">
					<label> <input type="radio" name="algorithm" value="cb">Content
						Base
					</label>
				</div>
				<div class="radio">
					<label> <input type="radio" name="algorithm" value="hb">Hybrid
					</label>
				</div>
				<br> <input class="btn btn-primary" type="submit" value="Xử lý">
			</form>
			<br>
			<div class="result">
				<label>Kết quả thực nghiệm</label>
				<table id="result"
				class="table table-striped table-bordered table-hover " width="100%">
				<thead>
					<tr>
						<th>UserId</th>
						<th>JobId</th>
						<td>Score</td>						
					</tr>
				</thead>
				<tbody>
					<%
						if(request.getAttribute("recommendedItems") != null)							
						for(RecommendedItem ri : (List<RecommendedItem>)request.getAttribute("recommendedItems")){
							out.write("<tr>");
							out.write("<td>" + 1 + "</td>");
							out.write("<td>" + String.valueOf(ri.getItemID()) + "</td>");
							out.write("<td>" + String.valueOf(ri.getValue()) + "</td>");
							out.write("</tr>");
						}
					%>
				</tbody>
			</table>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="resources/js/datatable.js"></script>
</body>
</html>