<%@page import="uit.se.recsys.bean.UserBean"%>
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
										<td><a href="<%=request.getContextPath()%>/ket-qua">Task
												1</a></td>
										<td>20-6-2016</td>
										<td>Collaborative Filtering</td>
										<td>dataset 2</td>
										<td>Đã xong</td>
									</tr>
									<tr>
										<td>2</td>
										<td><a href="#">Task 2</a></td>
										<td>20-6-2016</td>
										<td>Content Base</td>
										<td>dataset 1</td>
										<td>Lỗi</td>
									</tr>
									<tr>
										<td>3</td>
										<td><a href="#">Task 3</a></td>
										<td>20-6-2016</td>
										<td>Hybrid</td>
										<td>dataset 3</td>
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
								<div class="col-md-3"></div>
								<div class="col-md-6">
									<form class="form">
										<div class="form-group">
											<label for="task">Tên task</label><input type="text"
												required="required" class="form-control" id="task" id="task">
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
											<label for="algorithm">Chọn hoặc <a href="<%= request.getContextPath()%>/quan-ly-dataset">nhập</a>
												dataset
											</label><select class="form-control" id="algorithm" name="algorithm">
												<option value="cf">dataset 1</option>
												<option value="cb">dataset 2</option>
												<option value="hb">dataset 3</option>
											</select>
										</div>
										<button type="submit" class="btn btn-primary">Xử lý</button>
									</form>
								</div>
								<div class="col-md-3"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>