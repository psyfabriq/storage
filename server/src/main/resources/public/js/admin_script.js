(function(angular) {
  'use strict';

var app = angular.module('XSCMS_ADMIN_DASHBOARD',  [ 'ngRoute', 'ngCookies', 'ngSanitize', 'ngAnimate', 'ngTable', 'angular-growl', 'pascalprecht.translate', 'mdr.file', 'mgcrea.ngStrap']);

app.config(function($interpolateProvider) {
    $interpolateProvider.startSymbol('[[');
    $interpolateProvider.endSymbol(']]');
});

app.factory('DoService', function ($http) {
    var doGet = function(params,type){
        var res =$http({
          method: 'GET',
          url: '../'+type+'/?do',
          params: params
        });
        return res;
    };

    var doPost = function(params,type){
        var res = $http({
              method: "post",
              url: '../'+type+'/?do',
              data: params
          });
        return res;
    };

    return {
        doGet: doGet,
        doPost: doPost
    };
});




app.config(function($routeProvider) {

		$routeProvider

		// route for the home page
		.when('/', {
			templateUrl : '../template/dashboard.html',
			controller : 'dashController'
		})

		// route for the about page
		.when('/available', {
			templateUrl : '../template/available.html',
			controller : 'availableController'
		})

		// route for the contact page
		.when('/resent', {
			templateUrl : '../template/resent.html',
			controller : 'resentController'
		})

		// route for the contact page
		.when('/favorite', {
			templateUrl : '../template/favorite.html',
			controller : 'favoriteController'
		})

		// route for the contact page
		.when('/trash-bin', {
			templateUrl : '../template/trash_bin.html',
			controller : 'trashController'
		})

		// route for the contact page
		.when('/settings', {
			templateUrl : '../template/settings.html',
			controller : 'settingsController'
		})

		// route for the contact page
		.when('/create-user', {
			templateUrl : '../template/create_user.html',
			controller : 'userController'
		});
	});

app.controller('dashController', function($scope) {
	$scope.message = 'DASHBOARD';
	console.log($scope.message); 
	
});

app.controller('availableController', function($scope) {
	$scope.message = 'AVAILABLE';
	console.log($scope.message); 
});

app.controller('resentController', function($scope) {
	$scope.message = 'RESENT';
	console.log($scope.message); 
});

app.controller('favoriteController', function($scope) {
	$scope.message = 'FAVORITE';
	console.log($scope.message); 
});

app.controller('trashController', function($scope) {
	$scope.message = 'TRASH';
	console.log($scope.message); 
});

app.controller('settingsController', function($scope) {
	$scope.message = 'SETTINGS';
	console.log($scope.message); 
});

app.controller('userController', function($scope) {
	$scope.message = 'USER';
	console.log($scope.message); 
});

})(window.angular);