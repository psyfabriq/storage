<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8"></meta>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"></meta>
    <meta name="description" content="Bootstrap Admin Template"></meta>
    <meta name="keywords" content="app, responsive, jquery, bootstrap, dashboard, admin"></meta>
    <title>PFQ FileServer</title>
    <!-- Animate.CSS-->
    <link rel="stylesheet" href="vendor/animate.css/animate.css"></link>
    <!-- Bootstrap-->
    <link rel="stylesheet" href="vendor/bootstrap/dist/css/bootstrap.min.css"></link>
    <!-- Ionicons-->
    <link rel="stylesheet" href="vendor/ionicons/css/ionicons.css"></link>
    <!-- Material Colors-->
    <link rel="stylesheet" href="vendor/material-colors/dist/colors.css"></link>
    <!-- Application styles-->
    <link rel="stylesheet" href="css/app.css"></link>
  </head>
  <body>
    <div class="layout-container" layout:fragment="content">
      <div class="page-container bg-blue-grey-900">
        <div class="d-flex align-items-center align-items-center-ie bg-pic7 bg-cover">
          <div class="fw">
            <div class="container container-xs">
              <form class="cardbox cardbox-flat text-white form-validate text-color" role="form" th:action="@{/login}" method="post">
                <div class="cardbox-heading">
                  <div class="cardbox-title text-center">Login</div>
                </div>
                <div class="cardbox-body">
      
                  <div class="px-5">
                    <div class="form-group">
                      <input class="form-control form-control-inverse" type="text" name="login" id="login" required="required" autofocus="autofocus" placeholder="Email address or login"></input>
                    </div>
                    <div class="form-group">
                      <input class="form-control form-control-inverse" type="password" name="password" id="password" required="required" placeholder="Password"></input>
                    </div>
                    <div class="form-group mt-4">
                      <label class="custom-control custom-checkbox mb-0">
                        <input class="custom-control-input" type="checkbox" name="remember-me" id="remember-me"></input><span class="custom-control-indicator"></span><span class="custom-control-description">Remember me</span>
                        
                      </label>
                    </div>
                    <div class="text-center my-4">
                      <button class="btn btn-lg btn-gradient btn-oval btn-primary btn-block btn-info" type="submit">Sign in</button>
                    </div>
                  </div>
                </div>
                <div class="cardbox-footer text-center text-sm"></div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- Modernizr-->
    <script src="vendor/modernizr/modernizr.custom.js"></script>
    <!-- jQuery-->
    <script src="vendor/jquery/dist/jquery.js"></script>
    <!-- Bootstrap-->
    <script src="vendor/popper.js/dist/umd/popper.min.js"></script>
    <script src="vendor/bootstrap/dist/js/bootstrap.js"></script>
    <!-- Material Colors-->
    <script src="vendor/material-colors/dist/colors.js"></script>
    <!-- jQuery Form Validation-->
    <script src="vendor/jquery-validation/dist/jquery.validate.js"></script>
    <script src="vendor/jquery-validation/dist/additional-methods.js"></script>
    <!-- App script-->
    <script src="js/app.js"></script>
  </body>
</html>