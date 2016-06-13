<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<nav class="navbar navbar-default navbar-static-top custom_navbar_color">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar3">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#"><img
				src="<%out.print(request.getContextPath() + "/resources/images/logo.png");%>"
				class="img-responsive" alt="Dispute Bills"> </a>
		</div>
		<div id="navbar3" class="navbar-collapse collapse">
			<ul class="nav navbar-nav navbar-right">
				<li class="active"><a href="#">Chạy thuật toán</a></li>
				<li><a href="#">Đánh giá thuật toán</a></li>
				<li><a href="#">Thống kê dữ liệu</a></li>
				<li><a href="#"><span class="glyphicon glyphicon-log-in"></span>
						Đăng nhập</a></li>
				<li><a href="#"><span class="glyphicon glyphicon-user"></span>
						Đăng ký</a></li>
			</ul>
		</div>
		<!--/.nav-collapse -->
	</div>
	<!--/.container-fluid -->
</nav>