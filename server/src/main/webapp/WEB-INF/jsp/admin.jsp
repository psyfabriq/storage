<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html ng-app="XSCMS_ADMIN_DASHBOARD" class=" backgroundsize bgpositionshorthand bgpositionxy bgrepeatround bgrepeatspace bgsizecover borderradius cssanimations csscalc csstransforms supports csstransforms3d csstransitions no-flexboxtweener fontface inlinesvg localstorage multiplebgs preserve3d sessionstorage smil svgclippaths svgfilters svgforeignobject canvas todataurljpeg todataurlpng todataurlwebp" >
<head>
<meta charset="utf-8"></meta>
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1"></meta>
<meta name="description" content="Bootstrap Admin Template"></meta>
<meta name="keywords"
	content="app, responsive, jquery, bootstrap, dashboard, admin"></meta>
<title layout:fragment="title">PFQ FileServer (Administrative)</title>



<link rel="stylesheet" href="../vendor/animate.css/animate.css"></link>
<link rel="stylesheet"
	href="../vendor/bootstrap/dist/css/bootstrap.min.css"></link>
<link rel="stylesheet" href="../vendor/ionicons/css/ionicons.css"></link>
<link rel="stylesheet" href="../vendor/material-colors/dist/colors.css"></link>
<link rel="stylesheet"
	href="../vendor/bootstrap-datepicker/dist/css/bootstrap-datepicker3.css"></link>
<link rel="stylesheet" href="../vendor/select2/dist/css/select2.css"></link>
<link rel="stylesheet"
	href="../vendor/clockpicker/dist/bootstrap-clockpicker.css"></link>
<link rel="stylesheet"
	href="../vendor/nouislider/distribute/nouislider.min.css"></link>
<link rel="stylesheet"
	href="../vendor/mjolnic-bootstrap-colorpicker/dist/css/bootstrap-colorpicker.css"></link>
<link rel="stylesheet" href="../vendor/multiselect/css/multi-select.css"></link>
<!-- <link rel="stylesheet" href="../css/angular-strap.css"></link> -->
<link rel="stylesheet" href="../css/filemanager.css"></link>
<link rel="stylesheet" href="../css/app.css"></link>



</head>
<body
	class="pace-done footer-fixed theme-gradient-header-1"
	style="opacity: 1;" >
	<div class="pace  pace-inactive">
		<div class="pace-progress" data-progress-text="100%"
			data-progress="99" style="transform: translate3d(100%, 0px, 0px);">
			<div class="pace-progress-inner"></div>
		</div>
		<div class="pace-activity"></div>
	</div>
	<div class="layout-container" layout:fragment="content">
		<!-- top navbar-->
		<header class="header-container">
			<nav>
				<ul class="d-lg-none">
					<li><a class="sidebar-toggler menu-link menu-link-close"
						href="#"><span><em></em></span></a></li>
				</ul>
				<ul class="d-none d-sm-block">
					<li class="ms-hover"><a
						class="covermode-toggler menu-link menu-link-close" href="#"><span><em></em></span></a></li>
				</ul>
				<h2 class="header-title">PFQ FileServer (Administrative)</h2>
				<ul class="float-right">
					<li class="dropdown ms-hover"><a
						class="dropdown-toggle has-badge" href="#" data-toggle="dropdown"><em
							class="ion-ios-keypad"></em></a>
						<div
							class="dropdown-menu dropdown-menu-right dropdown-scale dropdown-card">
							<div class="p-3">
								<div class="d-flex mb-3">
									<div class="wd-xs mr-3">
										<div class="card bg-gradient-primary border-0">
											<div class="card-body text-white text-center">
												<div class="mb-1">
													<em class="ion-stats-bars icon-2x"></em>
												</div>
												<small class="text-bold">Stats</small>
											</div>
										</div>
									</div>
									<div class="wd-xs">
										<div class="card bg-gradient-info border-0">
											<div class="card-body text-white text-center">
												<div class="mb-1">
													<em class="ion-map icon-2x"></em>
												</div>
												<small class="text-bold">Places</small>
											</div>
										</div>
									</div>
								</div>
								<div class="d-flex">
									<div class="wd-xs mr-3">
										<div class="card bg-gradient-warning border-0">
											<div class="card-body text-white text-center">
												<div class="mb-1">
													<em class="ion-clock icon-2x"></em>
												</div>
												<small class="text-bold">Events</small>
											</div>
										</div>
									</div>
									<div class="wd-xs">
										<div class="card bg-gradient-danger border-0">
											<div class="card-body text-white text-center">
												<div class="mb-1">
													<em class="ion-plane icon-2x"></em>
												</div>
												<small class="text-bold">Flights</small>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div></li>
					<li class="ms-hover"><a id="header-search" href="#"><em
							class="ion-ios-search-strong"></em></a></li>
				</ul>
			</nav>
		</header>
		<!-- sidebar-->
		<aside class="sidebar-container">
			<div class="brand-header">
				<div class="float-left pt-4 text-muted sidebar-close">
					<em class="ion-arrow-left-c icon-lg"></em>
				</div>
				<a class="brand-header-logo" href="#"> 
            <span class="brand-header-logo-text">PFQ
						FileServer</span></a>
			</div>
			<div class="sidebar-content">
				<div class="sidebar-toolbar" style="">
					<div class="sidebar-toolbar-background"></div>
					<div class="sidebar-toolbar-content text-center">
						<a href="#"><img class="rounded-circle thumb64"
							src="img/user/01.jpg" alt="Profile"></img></a>
						<div class="mt-3">
							<div class="lead">${username}</div>
							<div class="text-thin">${useremail}</div>
						</div>
					</div>
				</div>
				<nav class="sidebar-nav">
					<ul>
						<li class="ms-hover">
							<div class="sidebar-nav-heading">MENU</div>
						</li>
						<li><a href="#!"><span
								class="float-right nav-label"></span><span class="nav-icon"><em
									class="ion-ios-keypad-outline"></em></span><span>My Drive</span></a></li>
						<li class="ms-hover"><a href="#!available"><span
								class="float-right nav-label"></span><span class="nav-icon"><em
									class="ion-ios-people"></em></span><span>Available to me</span></a></li>
						<li><a href="#!resent"><span
								class="float-right nav-label"></span><span
								class="nav-icon"><em class="ion-android-time"></em></span><span>Recent</span></a></li>
						<li><a href="#!favorite"><span
								class="nav-icon"><em class="ion-android-star"></em></span><span>Favorite</span></a></li>
						<li><a href="#!trash-bin"><span
								class="float-right nav-label"></span><span
								class="nav-icon"><em class="ion-trash-a"></em></span><span>Trash
									bin</span></a></li>
						<li class="ms-hover"><a href="#!settings"><span
								class="float-right nav-label"></span><span class="nav-icon"><em
									class="ion-ios-gear-outline"></em></span><span>Settings</span></a></li>
						<li class="ms-hover"><a href="#!create-user"><span
								class="float-right nav-label"></span><span class="nav-icon"><em
									class="ion-person-add"></em></span><span>Create user</span></a></li>			
					</ul>
				</nav>
			</div>
		</aside>
		<div class="sidebar-layout-obfuscator"></div>
		<!-- Main section-->
		<main class="main-container"> <!-- Page content-->
		<section class="section-container">
			<div class="container-fluid">
			   
			                 <div ng-view=""></div>
			
			</div>
		</section>
		<!-- Page footer--> <footer class="footer-container">
			<div class="d-flex justify-content-between fh">
			    <a class="footer-icon" href="#"><em class="ion-pie-graph"></em><span>Charts</span></a>
				<a class="footer-icon footer-icon-lg" href="#"><em class="ion-home"></em><span>Dashboard</span></a>
				<a class="footer-icon" href="#"><em class="ion-chatboxes"></em><span>Messages</span></a>
			</div>
		</footer> </main>
	</div>
	<!-- Search template-->
	<div class="modal modal-top fade modal-search" tabindex="-1"
		role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div class="modal-search-form">
						<form action="#">
							<div class="input-group">
								<div class="input-group-btn">
									<button class="btn btn-flat" type="button" data-dismiss="modal">
										<em class="ion-arrow-left-c icon-lg text-muted"></em>
									</button>
								</div>
								<input class="form-control header-input-search" type="text"
									placeholder="Search.."></input>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- End Search template-->

	<script src="../vendor/modernizr/modernizr.custom.js"></script>
	<script src="../vendor/pace/pace.min.js"></script>
	<script src="../vendor/jquery/dist/jquery.js"></script>
	<script src="../vendor/popper.js/dist/umd/popper.min.js"></script>
	<script src="../vendor/bootstrap/dist/js/bootstrap.js"></script>
	<script src="../vendor/material-colors/dist/colors.js"></script>
	<script src="../vendor/screenfull/dist/screenfull.js"></script>
	<script src="../vendor/jquery-localize-i18n/dist/jquery.localize.js"></script>
	<script src="../vendor/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
	<script src="../vendor/select2/dist/js/select2.js"></script>
	<script src="../vendor/clockpicker/dist/bootstrap-clockpicker.js"></script>
	<script src="../vendor/nouislider/distribute/nouislider.js"></script>
	<script src="../vendor/mjolnic-bootstrap-colorpicker/dist/js/bootstrap-colorpicker.js"></script>
	<script src="../vendor/multiselect/js/jquery.multi-select.js"></script>
	
	<!-- App script-->
	
	<script src="../js/angular.js"></script>
	<script src="../js/angular-additions.js"></script>
	<script src="../js/ng-file-upload.min.js"></script>
	
	<script src="../js/admin_script.js"></script>
	
	<script src="../js/filemanager.js"></script>
	
	
	<script src="../js/app.js"></script>
</body>
</html>




