(function(angular) {
  'use strict';
  var app = angular.module('XSCMS_ADMIN_DASHBOARD');
  app.controller('UserController', [ '$scope', '$translate', '$cookies', '$http', '$timeout','$mdDialog', function($scope, $translate, $cookies, $http, $timeout, $mdDialog ) {

   $scope.selected = [];
   $scope.customFullscreen = false;
   $scope.query = {
      order: 'name',
      limit: 10,
      page: 1
    };

   $scope.users = {
     "count": 0,
     "data": []
   };

    function success(users) {
        $scope.users = users;
      }

      $scope.getUsers = function () {

        $http.get('../admin/api/all-users-get')
                .then(function(response) {
                  $scope.users.data  = response.data;
                  $scope.users.count = $scope.users.data.length;
                })
                .catch(function(response) {
                  console.error('Gists error', response.status, response.data);
                })
                .finally(function() {
                  console.log("finally finished gists");
                });

        //$scope.promise = $nutrition.desserts.get($scope.query, success).$promise;
      };

      $scope.offUsers = function () {

      };

      $scope.deleteUsers = function () {

      };

      $scope.addUser = function(ev) {
          $mdDialog.show({
            controller: DialogController,
            templateUrl: '../template/add_user.html',
            parent: angular.element(document.body),
            targetEvent: ev,
            clickOutsideToClose:true,
            fullscreen: $scope.customFullscreen // Only for -xs, -sm breakpoints.
          })
          .then(function(answer) {
            $scope.status = 'You said the information was "' + answer + '".';
          }, function() {
            $scope.status = 'You cancelled the dialog.';
          });
        };


        function DialogController($scope, $mdDialog) {
          $scope.hide = function() {
            $mdDialog.hide();
          };

          $scope.cancel = function() {
            $mdDialog.cancel();
          };

          $scope.answer = function(answer) {
            $mdDialog.hide(answer);
          };
        }

      $scope.promise = $timeout(function () {
         $scope.getUsers();
      }, 2000);
    //

  }]);

})(window.angular);
