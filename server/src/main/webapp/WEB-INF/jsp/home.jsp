<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en"
	class=" backgroundsize bgpositionshorthand bgpositionxy bgrepeatround bgrepeatspace bgsizecover borderradius cssanimations csscalc csstransforms supports csstransforms3d csstransitions no-flexboxtweener fontface inlinesvg localstorage multiplebgs preserve3d sessionstorage smil svgclippaths svgfilters svgforeignobject canvas todataurljpeg todataurlpng todataurlwebp">
<head>
<meta charset="utf-8"></meta>
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1"></meta>
<meta name="description" content="Bootstrap Admin Template"></meta>
<meta name="keywords"
	content="app, responsive, jquery, bootstrap, dashboard, admin"></meta>
<title layout:fragment="title">PFQ FileServer</title>
<!-- Vendor styles-->
<!-- Animate.CSS-->
<link rel="stylesheet" href="vendor/animate.css/animate.css"></link>
<!-- Bootstrap-->
<link rel="stylesheet"
	href="vendor/bootstrap/dist/css/bootstrap.min.css"></link>
<!-- Ionicons-->
<link rel="stylesheet" href="vendor/ionicons/css/ionicons.css"></link>
<!-- Material Colors-->
<link rel="stylesheet" href="vendor/material-colors/dist/colors.css"></link>
<!-- Datepicker-->
<link rel="stylesheet"
	href="vendor/bootstrap-datepicker/dist/css/bootstrap-datepicker3.css"></link>
<!-- Select2-->
<link rel="stylesheet" href="vendor/select2/dist/css/select2.css"></link>
<!-- Clockpicker-->
<link rel="stylesheet"
	href="vendor/clockpicker/dist/bootstrap-clockpicker.css"></link>
<!-- Range Slider-->
<link rel="stylesheet"
	href="vendor/nouislider/distribute/nouislider.min.css"></link>
<!-- ColorPicker-->
<link rel="stylesheet"
	href="vendor/mjolnic-bootstrap-colorpicker/dist/css/bootstrap-colorpicker.css"></link>
<!-- Multiselect-->
<link rel="stylesheet" href="vendor/multiselect/css/multi-select.css"></link>
<!-- Application styles-->
<link rel="stylesheet" href="css/app.css"></link>
</head>
<body
	class="pace-done footer-fixed theme-gradient-header-1"
	style="opacity: 1;">
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
				<h2 class="header-title">PFQ FileServer</h2>
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
				<a class="brand-header-logo" href="#"> <!-- Logo Imageimg(src="img/logo.png", alt="logo")
            --> <span class="brand-header-logo-text">PFQ
						FileServer</span></a>
			</div>
			<div class="sidebar-content">
				<div class="sidebar-toolbar" style="">
					<div class="sidebar-toolbar-background"></div>
					<div class="sidebar-toolbar-content text-center">
						<a href="#"><img class="rounded-circle thumb64"
							src="img/user/01.jpg" alt="Profile"></img></a>
						<div class="mt-3">
							<div class="lead">User name</div>
							<div class="text-thin">Organization</div>
						</div>
					</div>
				</div>
				<nav class="sidebar-nav">
					<ul>
						<li class="ms-hover">
							<div class="sidebar-nav-heading">MENU</div>
						</li>
						<li><a href="dashboard.html"><span
								class="float-right nav-label"></span><span class="nav-icon"><em
									class="ion-ios-keypad-outline"></em></span><span>My Drive</span></a></li>
						<li class="ms-hover"><a href="available.html"><span
								class="float-right nav-label"></span><span class="nav-icon"><em
									class="ion-ios-people"></em></span><span>Available to me</span></a></li>
						<li><a href="resent.html"><span
								class="float-right nav-label"></span><span
								class="nav-icon"><em class="ion-android-time"></em></span><span>Recent</span></a></li>
						<li><a href="favorite.html"><span
								class="nav-icon"><em class="ion-android-star"></em></span><span>Favorite</span></a></li>
						<li><a href="trash_bin.html"><span
								class="float-right nav-label"></span><span
								class="nav-icon"><em class="ion-trash-a"></em></span><span>Trash
									bin</span></a></li>
						<li class="ms-hover"><a href="settings.html"><span
								class="float-right nav-label"></span><span class="nav-icon"><em
									class="ion-ios-gear-outline"></em></span><span>Settings</span></a></li>
					</ul>
				</nav>
			</div>
		</aside>
		<div class="sidebar-layout-obfuscator"></div>
		<!-- Main section-->
		<main class="main-container"> <!-- Page content-->
		<section class="section-container">
			<div class="container-fluid"></div>
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

	<!-- Modernizr-->
	<script src="vendor/modernizr/modernizr.custom.js"></script>
	<!-- PaceJS-->
	<script src="vendor/pace/pace.min.js"></script>
	<!-- jQuery-->
	<script src="vendor/jquery/dist/jquery.js"></script>
	<!-- Bootstrap-->
	<script src="vendor/popper.js/dist/umd/popper.min.js"></script>
	<script src="vendor/bootstrap/dist/js/bootstrap.js"></script>
	<!-- Material Colors-->
	<script src="vendor/material-colors/dist/colors.js"></script>
	<!-- Screenfull-->
	<script src="vendor/screenfull/dist/screenfull.js"></script>
	<!-- jQuery Localize-->
	<script src="vendor/jquery-localize-i18n/dist/jquery.localize.js"></script>
	<!-- Datepicker-->
	<script src="vendor/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
	<!-- Select2-->
	<script src="vendor/select2/dist/js/select2.js"></script>
	<!-- Clockpicker-->
	<script src="vendor/clockpicker/dist/bootstrap-clockpicker.js"></script>
	<!-- Range Slider-->
	<script src="vendor/nouislider/distribute/nouislider.js"></script>
	<!-- ColorPicker-->
	<script
		src="vendor/mjolnic-bootstrap-colorpicker/dist/js/bootstrap-colorpicker.js"></script>
	<!-- Multiselect-->
	<script src="vendor/multiselect/js/jquery.multi-select.js"></script>
	<!-- App script-->
	<script src="js/app.js"></script>

	<div
		class="colorpicker dropdown-menu colorpicker-2x colorpicker-hidden colorpicker-with-alpha colorpicker-right">
		<div class="colorpicker-saturation"
			style="background-color: rgb(0, 41, 255);">
			<i style="top: 38.4314px; left: 119.417px;"><b></b></i>
		</div>
		<div class="colorpicker-hue">
			<i style="top: 72.0867px;"></i>
		</div>
		<div class="colorpicker-alpha"
			style="background-color: rgb(83, 103, 206);">
			<i style="top: 0px;"></i>
		</div>
		<div class="colorpicker-color"
			style="background-color: rgb(83, 103, 206);">
			<div style="background-color: rgb(83, 103, 206);"></div>
		</div>
		<div class="colorpicker-selectors"></div>
	</div>
	<div
		class="colorpicker dropdown-menu colorpicker-hidden colorpicker-with-alpha colorpicker-right">
		<div class="colorpicker-saturation"
			style="background-color: rgb(0, 41, 255);">
			<i style="top: 19.2157px; left: 59.7087px;"><b></b></i>
		</div>
		<div class="colorpicker-hue">
			<i style="top: 36.0434px;"></i>
		</div>
		<div class="colorpicker-alpha"
			style="background-color: rgb(83, 103, 206);">
			<i style="top: 0px;"></i>
		</div>
		<div class="colorpicker-color"
			style="background-color: rgb(83, 103, 206);">
			<div style="background-color: rgb(83, 103, 206);"></div>
		</div>
		<div class="colorpicker-selectors"></div>
	</div>
	<div
		class="colorpicker dropdown-menu colorpicker-hidden colorpicker-right">
		<div class="colorpicker-saturation"
			style="background-color: rgb(0, 41, 255);">
			<i style="top: 19.2157px; left: 59.7087px;"><b></b></i>
		</div>
		<div class="colorpicker-hue">
			<i style="top: 36.0434px;"></i>
		</div>
		<div class="colorpicker-alpha"
			style="background-color: rgb(83, 103, 206);">
			<i style="top: 0px;"></i>
		</div>
		<div class="colorpicker-color"
			style="background-color: rgb(83, 103, 206);">
			<div style="background-color: rgb(83, 103, 206);"></div>
		</div>
		<div class="colorpicker-selectors"></div>
	</div>
	<div
		class="colorpicker dropdown-menu colorpicker-hidden colorpicker-right">
		<div class="colorpicker-saturation"
			style="background-color: rgb(0, 255, 0);">
			<i style="top: 27.8431px; left: 50px;"><b></b></i>
		</div>
		<div class="colorpicker-hue">
			<i style="top: 66.6667px;"></i>
		</div>
		<div class="colorpicker-alpha"
			style="background-color: rgb(92, 184, 92);">
			<i style="top: 0px;"></i>
		</div>
		<div class="colorpicker-color"
			style="background-color: rgb(92, 184, 92);">
			<div style="background-color: rgb(92, 184, 92);"></div>
		</div>
		<div class="colorpicker-selectors colorpicker-visible">
			<i class="colorpicker-selectors-color"
				style="background-color: rgb(119, 119, 119);"></i><i
				class="colorpicker-selectors-color"
				style="background-color: rgb(51, 122, 183);"></i><i
				class="colorpicker-selectors-color"
				style="background-color: rgb(92, 184, 92);"></i><i
				class="colorpicker-selectors-color"
				style="background-color: rgb(91, 192, 222);"></i><i
				class="colorpicker-selectors-color"
				style="background-color: rgb(240, 173, 78);"></i><i
				class="colorpicker-selectors-color"
				style="background-color: rgb(217, 83, 79);"></i>
		</div>
	</div>
</body>
</html>




