(function(angular) {
  'use strict';

var app = angular.module('XSCMS_ADMIN_DASHBOARD',
  [
      'ngRoute',
      'ab-base64',
      'ui.bootstrap', 
      'pascalprecht.translate',
      'ngCookies',
      'validator.rules',
      'ngTable',
      'mgcrea.ngStrap',
      'ngSanitize',
      'angular-growl',
      'ui.tree',
      'ngAnimate',
      'mdr.file'
      ]);
  

app.constant('MESSAGE_TYPE', [{
                                "Danger":{"class":"alert alert-danger ","icon":"icon fa fa-ban"},
                                "Info":{"class":"alert alert-info ","icon":"icon fa fa-info"},
                                "Warning":{"class":"alert alert-warning ","icon":"icon fa fa-warning"},
                                "Success":{"class":"alert alert-success ","icon":"icon fa fa-check"}
                              }]);


 app.config(function($routeProvider, $controllerProvider, $compileProvider, $filterProvider, $provide)
    {
        app.controllerProvider = $controllerProvider;
        app.compileProvider    = $compileProvider;
        app.routeProvider      = $routeProvider;
        app.filterProvider     = $filterProvider;
        app.provide            = $provide;
       

        // Register routes with the $routeProvider
    });



app.factory('pfqRegister', function ($rootScope) {
    var mem = {};
 
    return {
        store: function (key, value) {
            mem[key] = value;
        },
        get: function (key) {
            return mem[key];
        }
    };
}); 

app.config(['growlProvider', function (growlProvider) {
  growlProvider.globalTimeToLive(3000);
  growlProvider.onlyUniqueMessages(false);
  growlProvider.globalPosition('top-center');
  
}]);



//****************************************************


var byPropertyValue = function(coll, prop, value){
  for (var i = coll.length - 1; i >= 0; i--) {
    if (coll[i][prop] === value)
      return coll[i];
  };
};
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





app.factory('DataService', function ($http) {
    var getData = function ($val) {
        var params={type:$val, what:"getconfig"}
        var res =$http({
          method: 'GET',
          url: '../admin/?do',
          params: params
        });
        return res;
    };

    return {
        getData: getData
    };
});

app.factory('TemplateService', function ($http) {
    var getTemplates = function ($val) {
         var params={type:$val, what:"gettemplate"}
         var res =$http({
          method: 'GET',
          url: '../admin/?do',
          params: params
        });
        return res;
    };

    return {
        getTemplates: getTemplates
    };
});




app.directive('contentItem', function ($compile, TemplateService,base64) {

    var getTemplate = function (templates) {
        var template = '';
                template = base64.decode(templates.Template);
        return template;
    };

    var linker = function (scope, element, attrs) {
        TemplateService.getTemplates(scope.content.content_type).then(function (response) {
            var templates = response.data;
            element.html(getTemplate(templates));
            element.addClass( templates.class );
            $compile(element.contents())(scope);
        });
    };

    return {
        restrict: 'E',
        link: linker,
        scope: {
            content: '='
        }
    };
});


    app.run(function ($rootScope) {
        $rootScope.$on('scope.stored', function (event, data) {
            console.log("scope.stored", data);
        });
    });

 app.controller('AdminAppControler', function($rootScope, $scope, $route, $routeParams, $location, $interval, $timeout , uobj ) {
     $scope.name           = "AdminApp";
     $scope.$route         = $route;
     $scope.$location      = $location;
     $scope.$routeParams   = $routeParams;
     $scope.countDown      = 100000;
     $scope.$pageKey       = $scope.$location.$$url.substr(1);
     $timeout( function(){ $scope.uobj = uobj.getParams(); }, 800);
     $rootScope.$broadcast("setActiveModule", $scope.$pageKey);
     $interval(function(){$scope.countDown--;},1000,0);

 });

app.service('uobj', function(DoService,pfqRegister) {
  
    var params={type:"", what:"getprofile"}
    var res=DoService.doGet(params,'admin');
   
    var uparams={};

    res.success(function(data, status, headers, config) {
    if(data.data_type=='message'){
         // messageService.sendMessage(data.message,data.header,messageType[0][data.mtype]);
         }else if(data.data_type=='message_error'){
        //  messageService.sendMessage(data.message,data.header,messageType[0][data.mtype]);
         }
         else{
           uparams = data;
        }
      }).
      error(function(data, status, headers, config) {

      });    
  
  
   
  this.getParams = function() {
    pfqRegister.store('profile',uparams);
   // console.log(uparams); 
    return uparams;  
  };
  
});


app.config(function($routeProvider) {
	$routeProvider

		// route for the home page
		.when('/', {
			templateUrl : '../template/dashboard.html',
			controller  : 'dashController'
		})

		// route for the about page
		.when('/available', {
			templateUrl : '../template/available.html',
			controller  : 'availableController'
		})

		// route for the contact page
		.when('/resent', {
			templateUrl : '../template/resent.html',
			controller  : 'resentController'
		})
	
	   // route for the contact page
    	.when('/favorite', {
		    templateUrl : '../template/favorite.html',
	    	controller  : 'favoriteController'
    	})
	
	   // route for the contact page
	   .when('/trash-bin', {
	    	templateUrl : '../template/trash_bin.html',
	    	controller  : 'trashController'
	   })
	
	   // route for the contact page
	  .when('/settings', {
		    templateUrl : '../template/settings.html',
	    	controller  : 'settingsController'
	   })
	
	  // route for the contact page
	  .when('/create-user', {
	    	templateUrl : '../template/create_user.html',
	    	controller  : 'userController'
	  });
});



app.config(function($interpolateProvider) {
    $interpolateProvider.startSymbol('[[');
    $interpolateProvider.endSymbol(']]');
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