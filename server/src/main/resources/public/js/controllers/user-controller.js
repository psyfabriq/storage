(function(angular) {
  'use strict';
  var app = angular.module('XSCMS_ADMIN_DASHBOARD');
  app.controller('UserController', [ '$scope', '$translate', '$cookies', '$http', '$timeout','$mdDialog', function($scope, $translate, $cookies, $http, $timeout, $mdDialog ) {

    var config = {
     headers : {
         'Content-Type': 'application/json;'
     }
    };

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
                $scope.promise = $timeout(function () {
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
                }, 2000);

        //$scope.promise = $nutrition.desserts.get($scope.query, success).$promise;
      };

      $scope.offUsers = function () {

      };

      $scope.deleteUsers = function () {
        angular.forEach($scope.selected, function(item, index) {
          $http.post('../admin/api/rm-user', item, config)
                    .then(function(response) {

                        })
                        .catch(function(response) {
                          console.error('Server error ', response.status, response.data);
                        })
                        .finally(function() {
                          console.log("finally finished rm user");
                        });
        });
          $scope.getUsers();
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
          .then(function(data) {
            $http.post('../admin/api/add-user', data, config)
                      .then(function(response) {
                            $scope.getUsers();
                          })
                          .catch(function(response) {
                            console.error('Server error ', response.status, response.data);
                          })
                          .finally(function() {
                            console.log("finally finished add user");
                          });
          //  console.log(answer);
          }, function() {
            $scope.status = 'You cancelled the dialog.';
          });
        };


        function DialogController($scope, $mdDialog) {
          $scope.data={};
          $scope.hide = function() {
            $mdDialog.hide();
          };

          $scope.cancel = function() {
            $mdDialog.cancel();
          };

          $scope.answer = function() {
            $mdDialog.hide($scope.data);
          };
        }

    $scope.getUsers();

  }]);

})(window.angular);
