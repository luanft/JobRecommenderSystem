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

<!-- custom css -->
<link rel="stylesheet" href="resources/css/main.css">

<!-- jquery -->
<script type="text/javascript" src="resources/js/home.js"></script>

</head>
<body>
	<!-- include header file -->
	<jsp:include page="header.jsp"></jsp:include>

	<div class="container">
		<div class="row" style="margin: 0 auto;">
			<div class="panel-group">
				<div class="panel panel-primary">
					<div class="panel-heading" data-toggle="collapse"
						data-target="#panel-content">
						<label class="text-uppercase">Trạng thái tasks</label>
					</div>
					<div id="panel-content" class="panel-collapse collapse in">
						<div class="panel-body">
							<table class="table table-hover">
								<thead>
									<tr>
										<th>STT</th>
										<th>Tên task</th>
										<th>Ngày tạo</th>
										<th>Thuật toán</th>
										<th>Dataset</th>
										<th>Trạng thái</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>1</td>
										<td>Task 1</td>
										<td>20-6-2016</td>
										<td>Collaborative Filtering</td>
										<td>scoring.txt</td>
										<td>Đã xong</td>
									</tr>
									<tr>
										<td>2</td>
										<td>Task 2</td>
										<td>20-6-2016</td>
										<td>Content Base</td>
										<td>scoring.txt, job.txt, cv.txt</td>
										<td>Lỗi</td>
									</tr>
									<tr>
										<td>3</td>
										<td>Task 3</td>
										<td>20-6-2016</td>
										<td>Hybrid</td>
										<td>scoring.txt, job.txt, cv.txt</td>
										<td>Đang chạy</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>


				<div class="panel panel-primary">
					<div class="panel-heading" data-toggle="collapse"
						data-target="#panel-content2">
						<label class="text-uppercase">Tạo task mới</label>
					</div>
					<div id="panel-content2" class="panel-collapse collapse in">
						<div class="panel-body">
							<div class="row">
								<div class="col-md-6">
									<form class="form">
										<div class="form-group">
											<label for="task">Tên task</label><input type="text" required="required"
												class="form-control" id="task" id="task">
										</div>
										<div class="form-group">
											<label for="algorithm">Chọn thuật toán</label><select
												class="form-control" id="algorithm" name="algorithm">
												<option value="cf">Collaborative Filtering</option>
												<option value="cb">Content Base</option>
												<option value="hb">Hybrid</option>
											</select>
										</div>
										<div class="form-group">
											<label for="dataset">Chọn dataset</label>
											<table class="table table-hover">
												<thead>
													<tr>
														<th width="20%">Chọn</th>
														<th width="40%">Tên dataset</th>
														<th width="40%">Ngày tạo</th>
													</tr>
												</thead>
												<tbody>
													<tr>
														<td><input class="form-control" type="checkbox"></td>
														<td>job.txt</td>
														<td>20-6-2016</td>
													</tr>
													<tr>
														<td><input class="form-control" type="checkbox"></td>
														<td>cv.txt</td>
														<td>20-6-2016</td>
													</tr>
													<tr>
														<td><input class="form-control" type="checkbox"></td>
														<td>score.txt</td>
														<td>20-6-2016</td>
													</tr>
												</tbody>
											</table>
										</div>
										<button type="submit" class="btn btn-primary">Xử lý</button>
									</form>
								</div>
								<div class="col-md-6">
									<div class="panel panel-primary">
										<div class="panel-heading" data-toggle="collapse"
											data-target="#note">
											<label>LƯU Ý QUAN TRỌNG</label>
										</div>
										<div class="panel-collapse collapse in" id="note">
											<div class="panel-body">
												<ul>
													<li><p class="text-danger">Để chạy thuật toán Collaborative Filtering
															vui lòng chọn loại file dataset là score.txt</p></li>
													<li><p class="text-danger">Để chạy thuật toán Content Base hoặc Hybrid
															vui lòng chọn 3 loại file dataset: score.txt, job.txt và
															cv.txt</p></li>
												</ul>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>