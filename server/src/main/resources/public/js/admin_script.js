(function(angular) {
  'use strict';

var app = angular.module('XSCMS_ADMIN_DASHBOARD',  [ 'ngRoute', 'ngCookies', 'ngSanitize', 'ngAnimate', 'ngTable', 'angular-growl', 'pascalprecht.translate', 'mdr.file','ngMaterial', 'md.data.table','lfNgMdFileInput']);

app.config(function($interpolateProvider) {
  //  $interpolateProvider.startSymbol('[[');
  //  $interpolateProvider.endSymbol(']]');
});

app.factory('DoService', function ($http) {
    var doGet = function(params,type){
        var res =$http({
          method: 'GET',
          url: '../'+type,
          params: params
        });
        return res;
    };

    var doPost = function(params,type){
        var res = $http({
              method: "post",
              url: '../'+type,
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
			controller : 'FileManagerCtrl'
		})

		// route for the about page
		.when('/available', {
			templateUrl : '../template/available.html',
			controller : 'AvailableController'
		})

		// route for the contact page
		.when('/resent', {
			templateUrl : '../template/resent.html',
			controller : 'ResentController'
		})

		// route for the contact page
		.when('/favorite', {
			templateUrl : '../template/favorite.html',
			controller : 'FavoriteController'
		})

		// route for the contact page
		.when('/trash-bin', {
			templateUrl : '../template/trash_bin.html',
			controller : 'TrashController'
		})

		// route for the contact page
		.when('/settings', {
			templateUrl : '../template/settings.html',
			controller : 'SettingsController'
		})

		// route for the contact page
		.when('/settings-user', {
			templateUrl : '../template/settings_user.html',
			controller : 'UserController'
		});
	});


})(window.angular);
