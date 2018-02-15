(function(angular) {
  'use strict';


var app = angular.module('XSCMS_ADMIN_DASHBOARD');

app.controller('FileManagerCtrl', [ '$scope', '$translate', '$cookies','$http','$timeout', '$mdDialog',
function($scope, $translate, $cookies, $http, $timeout, $mdDialog) {

  var config = {
   headers : {
       'Content-Type': 'application/json;'
   }
  };

  var parrent = {};

  $scope.treeView    = {};
  $scope.modelFolder = {"count": 0,"data": []};
  $scope.query       = {order: 'name',limit: 10,page: 1};
  $scope.selected    = [];
  $scope.breadcrums = ['/'];



  $scope.options = {
    onNodeSelect: function (node, breadcrums) {
        $scope.breadcrums = breadcrums;
    }
  };
//  $scope.modelItem   = {};


  function refresh() {

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

  $scope.createFolder = function (ev) {

    $mdDialog.show({
      controller: DialogController,
      templateUrl: '../template/create-folder.html',
      parent: angular.element(document.body),
      targetEvent: ev,
      clickOutsideToClose:true,
      fullscreen: false
    })
      .then(function(data) {
          $http.post('../file/api/add-folder', data , config)
           .then(function(response) {
             //$scope.modelFolder.data  = response.data.Result;
            // $scope.modelFolder.count = $scope.modelFolder.data.length;
           })
           .catch(function(response) {
             console.error('add folder error', response.status, response.data);
           })
           .finally(function() {
             //console.log("finally finished gists");
           });
      }, function() {
        $scope.status = 'You cancelled the dialog.';
      });
    };

    $scope.uploadItem = function (ev) {

      $mdDialog.show({
        controller: DialogController,
        templateUrl: '../template/upload-item.html',
        parent: angular.element(document.body),
        targetEvent: ev,
        clickOutsideToClose:true,
        fullscreen: false
      })
        .then(function(data) {
            $http.post('../file/api/item-upload', data , config)
             .then(function(response) {
               //$scope.modelFolder.data  = response.data.Result;
              // $scope.modelFolder.count = $scope.modelFolder.data.length;
             })
             .catch(function(response) {
               console.error('add folder error', response.status, response.data);
             })
             .finally(function() {
               //console.log("finally finished gists");
             });
        }, function() {
          $scope.status = 'You cancelled the dialog.';
        });
      };


  $scope.refresh = function () {
            $scope.promise = $timeout(function () {
              $http.post('../file/api/get-list-directory', perrant, config)
               .then(function(response) {
                 $scope.modelFolder.data  = response.data.Result;
                 $scope.treeView.folders  = $scope.modelFolder.data;
                 $scope.modelFolder.count = $scope.modelFolder.data.length;
               })
               .catch(function(response) {
                 console.error('Refresh error', response.status, response.data);
               })
               .finally(function() {
                 //console.log("finally finished gists");
               });
            }, 2000);

    //$scope.promise = $nutrition.desserts.get($scope.query, success).$promise;
  };

  $scope.refresh();

}]);

})(window.angular);
