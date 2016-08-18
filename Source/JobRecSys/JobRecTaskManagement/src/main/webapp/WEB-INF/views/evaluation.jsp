<%@page import="uit.se.recsys.bean.MetricBean"%>
<%@page import="uit.se.recsys.bean.TaskBean"%>
<%@page import="uit.se.recsys.bean.UserBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Đánh giá thuật toán - Recommender</title>

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
	<%
		UserBean user = (UserBean) session.getAttribute("user");
		if (user == null || user.getUserName() == null) {
	%>
	<jsp:include page="header.jsp"></jsp:include>
	<%
		} else {
	%>
	<jsp:include page="loggedInHeader.jsp"></jsp:include>
	<%
		}
	%>

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
										<%
											List<MetricBean> listMetric = (List<MetricBean>) request
													.getAttribute("listMetric");
											for (MetricBean metric : listMetric) {
												out.write("<th>");
												out.write(metric.getName());
												out.write("</th>");
											}
										%>
									</tr>
								</thead>
								<tbody>
									<%
										List<TaskBean> listTask = (List<TaskBean>) request
												.getAttribute("listTask");
										int count = 1;
										for (TaskBean task : listTask) {
											out.write("<tr>");
											out.write("<td>" + count++ + "</td>");
											out.write("<td><a href='" + request.getContextPath()
													+ "/ket-qua?taskid= " + task.getTaskId() + "'>"
													+ task.getTaskName() + "</a></td>");
											out.write("<td>" + task.getTimeCreate() + "</td>");
											out.write("<td>" + task.getAlgorithm() + "</td>");
											out.write("<td>" + task.getDataset() + "</td>");
											out.write("<td>" + task.getStatus() + "</td>");
											for (MetricBean metric : task.getMetrics()) {
												out.write("<td>");
												out.write(String.valueOf(metric.getScore()));
												out.write("</td>");
											}
										}
									%>
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
									<form:form class="form" action="danh-gia-thuat-toan" modelAttribute="task" method="POST">
										<div class="form-group">
											<label for="task">Tên task</label>
											<form:input type="text" required="required"
												class="form-control" path="taskName" id="taskName" />
										</div>
										<div class="form-group">
											<label for="algorithm">Chọn thuật toán</label>
											<form:select class="form-control" id="algorithm"
												path="algorithm">
												<option value="cf">Collaborative Filtering</option>
												<option value="cb">Content Base</option>
												<option value="hb">Hybrid</option>
											</form:select>
										</div>
										<div class="form-group">
											<label for="algorithm">Chọn hoặc <a
												href="<%=request.getContextPath()%>/quan-ly-dataset">nhập</a>
												dataset
											</label>
											<form:select class="form-control" id="dataset" path="dataset">
												<%
													String[] datasets = (String[]) request
																	.getAttribute("datasets");
															if (datasets != null) {
																for (int i = 0; i < datasets.length; i++) {
																	out.write("<option value='" + datasets[i] + "'>"
																			+ datasets[i] + "</option>");
																}
															}
												%>
											</form:select>
										</div>
										<div class="form-group">
											<label for="task">Tỉ lệ % tập test</label>
											<form:input type="number" required="required"
												class="form-control" path="testSize" id="testSize" />
										</div>
										<button type="submit" class="btn btn-primary">Xử lý</button>
									</form:form>
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