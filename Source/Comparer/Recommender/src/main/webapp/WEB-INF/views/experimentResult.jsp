<%@page import="java.util.List"%>

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

<!-- custom css -->
<link rel="stylesheet" href="resources/css/main.css">

<!-- custom query -->
<script type="text/javascript" src="resources/js/home.js"></script>

<!-- datatable -->
<link
	href="resources/libs/DataTables-1.10.9/css/jquery.dataTables.min.css"
	rel="stylesheet">
<script type="text/javascript"
	src="resources/libs/DataTables-1.10.9/js/jquery.dataTables.min.js"></script>
<script type="text/javascript"
	src="resources/libs/DataTables-1.10.9/js/jquery.dataTables.js"></script>

</head>
<body>
	<!-- include header file -->
	<jsp:include page="header.jsp"></jsp:include>

	<div class="container">
		<div class="row" style="margin: 0 auto;">
			<div class="panel-group">
				<div class="panel panel-primary">
					<div class="panel-heading" data-toggle="collapse"
						data-target="#panel-content2">
						<label class="text-uppercase">Kết quả thực nghiệm thuật
							toán</label>
					</div>
					<div id="panel-content2" class="panel-collapse collapse in">
						<div class="panel-body">
							<div class="row">
								<div class="col-md-2">
									<p style="font-weight: bold;">Tên task: </p>
									<p style="font-weight: bold;">Ngày tạo: </p>
									<p style="font-weight: bold;">Thuật toán: </p>
									<p style="font-weight: bold;">Dataset: </p>
									<p style="font-weight: bold;">Trạng thái: </p>
									<p style="font-weight: bold;">Kết quả: </p>
								</div>
								<div class="col-md-4">
									<p>Task 1</p>
									<p>20-7-2016</p>
									<p>Collaborative Filtering</p>
									<p>score.txt</p>									
									<p>Đã xong</p>
									<a href="#">result.txt</a>
								</div>
								<div class="col-md-6">
									<div class="result">
										<label>Bảng kết quả</label>
										<table id="result"
											class="table table-striped table-bordered table-hover ">
											<thead>
												<tr>
													<th>UserId</th>
													<th>JobId</th>
													<td>Score</td>
												</tr>
											</thead>
											<tbody>
												<tr>
													<td>1</td>
													<td>4</td>
													<td>5</td>
												</tr>
												<tr>
													<td>1</td>
													<td>3</td>
													<td>3</td>
												</tr>
												<tr>
													<td>1</td>
													<td>2</td>
													<td>4</td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="resources/js/datatable.js"></script>
</body>
</html>