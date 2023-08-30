<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" type="image/png" sizes="16x16"
	href="plugins/images/favicon.png">
<title>Pixel Admin</title>
<!-- Bootstrap Core CSS -->
<link href="bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
<!-- Menu CSS -->
<link
	href="plugins/bower_components/sidebar-nav/dist/sidebar-nav.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css">
<!-- animation CSS -->
<link href="css/animate.css" rel="stylesheet">
<!-- Custom CSS -->
<link href="css/style.css" rel="stylesheet">
<!-- color CSS -->
<link href="css/colors/blue-dark.css" id="theme" rel="stylesheet">
<link rel="stylesheet" href="./css/custom.css">
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
</head>

<body>
	<!-- Preloader -->
	<div class="preloader">
		<div class="cssload-speeding-wheel"></div>
	</div>
	<div id="wrapper">
		<!-- Navigation -->
		<nav class="navbar navbar-default navbar-static-top m-b-0">
			<div class="navbar-header">
				<a class="navbar-toggle hidden-sm hidden-md hidden-lg "
					href="javascript:void(0)" data-toggle="collapse"
					data-target=".navbar-collapse"> <i class="fa fa-bars"></i>
				</a>
				<div class="top-left-part">
					<a class="logo" href="index.jsp"> <b> <img
							src="plugins/images/pixeladmin-logo.png" alt="home" />
					</b> <span class="hidden-xs"> <img
							src="plugins/images/pixeladmin-text.png" alt="home" />
					</span>
					</a>
				</div>
				<ul class="nav navbar-top-links navbar-left m-l-20 hidden-xs">
					<li>
						<form role="search" class="app-search hidden-xs">
							<input type="text" placeholder="Search..." class="form-control">
							<a href=""> <i class="fa fa-search"></i>
							</a>
						</form>
					</li>
				</ul>
				<ul class="nav navbar-top-links navbar-right pull-right">
					<li>
						<div class="dropdown">
							<a class="profile-pic dropdown-toggle" data-toggle="dropdown"
								href="#"> <img src="plugins/images/users/varun.jpg"
								alt="user-img" width="36" class="img-circle" /> <b
								class="hidden-xs">Welcome</b>
							</a>
							<ul class="dropdown-menu">
								<li><a href="<c:url value="/profile.jsp"></c:url>">Thông tin cá nhân</a></li>
								<li><a href="#">Thống kê công việc</a></li>
								<li class="divider"></li>
								<li><a href="<c:url value="/logout" />">Đăng xuất</a></li>
							</ul>
						</div>
					</li>
				</ul>
			</div>
			<!-- /.navbar-header -->
			<!-- /.navbar-top-links -->
			<!-- /.navbar-static-side -->
		</nav>
		<!-- Left navbar-header -->
		<div class="navbar-default sidebar" role="navigation">
			<div class="sidebar-nav navbar-collapse slimscrollsidebar">
				<ul class="nav" id="side-menu">
					<li style="padding: 10px 0 0;"><a href="index.jsp"
						class="waves-effect"><i class="fa fa-clock-o fa-fw"
							aria-hidden="true"></i><span class="hide-menu">Dashboard</span></a></li>
					<li><a href="<c:url value="/user-table" />" class="waves-effect"><i
							class="fa fa-user fa-fw" aria-hidden="true"></i><span
							class="hide-menu">Thành viên</span></a></li>
					<li><a href="<c:url value="/role-table" />" class="waves-effect"><i
							class="fa fa-modx fa-fw" aria-hidden="true"></i><span
							class="hide-menu">Quyền</span></a></li>
					<li><a href="<c:url value="/get-projects" /> class="waves-effect"><i
							class="fa fa-table fa-fw" aria-hidden="true"></i><span
							class="hide-menu">Dự án</span></a></li>
					<li><a href="task.jsp" class="waves-effect"><i
							class="fa fa-table fa-fw" aria-hidden="true"></i><span
							class="hide-menu">Công việc</span></a></li>
					<li><a href="blank.html" class="waves-effect"><i
							class="fa fa-columns fa-fw" aria-hidden="true"></i><span
							class="hide-menu">Blank Page</span></a></li>
					<li><a href="404.html" class="waves-effect"><i
							class="fa fa-info-circle fa-fw" aria-hidden="true"></i><span
							class="hide-menu">Error 404</span></a></li>
				</ul>
			</div>
		</div>
		<!-- Left navbar-header end -->
		<!-- Page Content -->
		<div id="page-wrapper">
			<div class="container-fluid">
				<div class="row bg-title">
					<div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
						<h4 class="page-title">Danh sách thành viên</h4>
					</div>
					<div class="col-lg-9 col-sm-8 col-md-8 col-xs-12 text-right">
						<a href="<c:url value="user-add.jsp" />"
							class="btn btn-sm btn-success">Thêm mới</a>
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /row -->
				<div class="row">
					<div class="col-sm-12">
						<div class="white-box">
							<div class="table-responsive">
								<table class="table" id="example">
									<thead>
										<tr>
											<th>STT</th>
											<th>Email</th>
											<th>Password</th>
											<th>Full Name</th>
											<th>Address</th>
											<th>Phone</th>
											<th>Role Name</th>
											<th>Action</th>
										</tr>
									</thead>
									<tbody>

										<c:forEach var="user" items="${users}">
											<tr id="user-${user.id}">
												<td>${user.id}</td>
												<td>${user.email}</td>
												<td>${user.password}</td>
												<td>${user.fullName}</td>
												<td>${user.address}</td>
												<td>${user.phone}</td>
												<td>${user.role.roleName}</td>
												<td><a data-id="${user.id}" href="#"
													class="btn btn-sm btn-primary btn-edit">Sửa</a> 
													<a
													data-id="${user.id}" href="#"
													class="btn btn-sm btn-danger delete-user-btn">Xóa</a> 
													<a
													href="user-details.html" class="btn btn-sm btn-info">Xem</a>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
				<!-- /.row -->
			</div>
			<!-- /.container-fluid -->
			<footer class="footer text-center"> 2018 &copy; myclass.com
			</footer>
		</div>
		<!-- /#page-wrapper -->
	</div>
	<!-- MODAL  -->
	<div id="editUserModal" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Chỉnh sửa thông tin user</h4>
				</div>
				<div class="modal-body">
					<div class="white-box">
						<form id="editUserForm" class="form-horizontal form-material">
							<div class="form-group">
								<label class="col-md-12">UserID</label>
								<div class="col-md-12">
									<input type="text" placeholder=""
										class="form-control form-control-line userId"
										readonly>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-12">Email</label>
								<div class="col-md-12">
									<input type="email" placeholder=""
										class="form-control form-control-line editEmail"
										name="example-email">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-12">Password</label>
								<div class="col-md-12">
									<input type="password" value="password"
										class="form-control form-control-line editPassword">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-12">Full Name</label>
								<div class="col-md-12">
									<input type="text" placeholder=""
										class="form-control form-control-line editFullName">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-12">Phone Number</label>
								<div class="col-md-12">
									<input type="text" placeholder=""
										class="form-control form-control-line editPhone">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-12">Select Country</label>
								<div class="col-sm-12">
									<select class="form-control form-control-line editAddress">
										<option>London</option>
										<option>India</option>
										<option>VietNam</option>
										<option>Canada</option>
										<option>Thailand</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-12">Select Role</label>
								<div class="col-sm-12">
									<select class="form-control form-control-line editRole">
										<option value="1">ADMIN</option>
										<option value="2">USER</option>
										<option value="3">LEADER</option>
									</select>
								</div>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>
						<button type="submit" class="btn btn-primary" id="updateUser">Lưu
							thay đổi</button>
					</div>
				</div>

			</div>
		</div>
	</div>
		<!-- /#wrapper -->
		<!-- jQuery -->
		<script src="plugins/bower_components/jquery/dist/jquery.min.js"></script>
		<!-- Bootstrap Core JavaScript -->
		<script src="bootstrap/dist/js/bootstrap.min.js"></script>
		<!-- Menu Plugin JavaScript -->
		<script
			src="plugins/bower_components/sidebar-nav/dist/sidebar-nav.min.js"></script>
		<!--slimscroll JavaScript -->
		<script src="js/jquery.slimscroll.js"></script>
		<script src="js/jquery.dataTables.js"></script>
		<!--Wave Effects -->
		<script src="js/waves.js"></script>
		<!-- Custom Theme JavaScript -->
		<script src="js/custom.min.js"></script>
		<script src="js/DeleteUser.js"></script>
		<script src="js/UpdateUser.js"></script>
		<script>
			$(document).ready(function() {
				$('#example').DataTable();
			});
		</script>
</body>

</html>