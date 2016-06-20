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
<title>Quản lý dataset - Recommender</title>

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
						<label class="text-uppercase">Danh sách dataset đã upload</label>
					</div>
					<div id="panel-content" class="panel-collapse collapse in">
						<div class="panel-body">
							<table class="table table-hover">
								<thead>
									<tr>
										<th>STT</th>
										<th>Tên dataset</th>
										<th>Ngày đăng</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>1</td>
										<td>job.txt</td>
										<td>20-6-2016</td>
									</tr>
									<tr>
										<td>1</td>
										<td>cv.txt</td>
										<td>20-6-2016</td>
									</tr>
									<tr>
										<td>1</td>
										<td>score.txt</td>
										<td>20-6-2016</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>


				<div class="panel panel-primary">
					<div class="panel-heading" data-toggle="collapse"
						data-target="#panel-content2">
						<label class="text-uppercase">Upload datasets</label>
					</div>
					<div id="panel-content2" class="panel-collapse collapse in">
						<div class="panel-body">
							<div class="row">
								<div class="col-md-6">
									<form class="form" role="form" action="excute" method="post"
										enctype="multipart/form-data">
										<label for="db">Chọn File Score.txt</label> <input type="file"
											class="filestyle" required="required"
											data-buttonName="btn-primary" name="file"
											data-buttonText="Chọn file" data-buttonBefore="true"
											id="score"> <label for="db">Chọn File Job.txt</label>
										<input type="file" class="filestyle"
											data-buttonName="btn-primary" name="file"
											data-buttonText="Chọn file" data-buttonBefore="true"
											id="score"> <label for="db">Chọn File CV.txt</label>
										<input type="file" class="filestyle"
											data-buttonName="btn-primary" name="file"
											data-buttonText="Chọn file" data-buttonBefore="true"
											id="score"> <br> <input class="btn btn-primary"
											type="submit" value="Upload">
									</form>
								</div>
								<div class="col-md-6">
									<div class="panel panel-primary">
										<div class="panel panel-heading" data-target="#note"
											data-toggle="collapse">
											<label class="text-uppercase">Lưu ý quan trọng</label>
										</div>
										<div class="panel-collapse collapse in" id="note">
											<div class="panel-body">
												<ul>
													<li><p class="text-danger">Để chạy được các thuật
															toán cần upload đủ 3 file dataset như yêu cầu</p></li>
													<li><p class="text-danger">Mỗi lần upload dataset
															mới cần đặt tên khác tên dataset cũ để khỏi bị ghi đè</p></li>
													<li><p class="text-danger">File upload có định
															dạng là file text (*.txt)</p></li>
													<li><p class="text-danger">Các cột cách nhau bằng
															ký tự tab (\t). Các file có thứ tự các cột đúng theo thứ
															tự bên dưới.</p></li>
													<li><p class="text-danger">File Score.txt gồm:
															UserId, JobId, Score.</p></li>
													<li><p class="text-danger">File Job.txt gồm:
															JobId, JobName, Location, Salary, Category, Requirement,
															Tag, Description.</p></li>
													<li><p class="text-danger">File CV.txt gồm:
															UserId, CVId, UserName, CVName, UserAddress,
															ExpectedSalary, Category, Education, Language, Skill,
															CareerObjective.</p></li>
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