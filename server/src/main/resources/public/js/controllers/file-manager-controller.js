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
  $scope.breadcrums  = ['/'];



  $scope.options = {
    onNodeSelect: function (node, breadcrums) {
        $scope.breadcrums = breadcrums;
        console.log(node);
        parrent = {"parrent":node.path,"id":node.id};
        $scope.openchild();
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

  $scope.rootFolder = function () {
    parrent = {};
    $scope.refresh();
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

  $scope.openchild = function () {
    $scope.modelFolder.data = [];
    $scope.modelFolder.data.length = 0;
    $scope.modelFolder.count = 0;
    $scope.promise = $timeout(function () {
      $http.post('../file/api/get-list-directory', parrent, config)
       .then(function(response) {
         var result = response.data.Result;
         angular.extend($scope.modelFolder.data, result.folders,  result.files)

         angular.forEach($scope.treeView.folders, function (value, key) {
           if (value.id == parrent.id) {
              value.folders = angular.copy(result.folders);
           }
         });


        // $scope.treeView.folders  = angular.copy(result.folders);

         $scope.modelFolder.count = $scope.modelFolder.data.length;
       })
       .catch(function(response) {
         console.error('Refresh error', response.status, response.data);
       })
       .finally(function() {
         //console.log("finally finished gists");
       });
    }, 500);

  }

  $scope.refresh = function () {
            $scope.modelFolder.data = [];
            $scope.modelFolder.data.length = 0;
            $scope.modelFolder.count = 0;
            $scope.promise = $timeout(function () {
              $http.post('../file/api/get-list-directory', parrent, config)
               .then(function(response) {
                 var result = response.data.Result;
                 angular.extend($scope.modelFolder.data, result.folders,  result.files)
                 $scope.treeView.folders  = angular.copy(result.folders);
                 $scope.modelFolder.count = $scope.modelFolder.data.length;
               })
               .catch(function(response) {
                 console.error('Refresh error', response.status, response.data);
               })
               .finally(function() {
                 //console.log("finally finished gists");
               });
            }, 500);

    //$scope.promise = $nutrition.desserts.get($scope.query, success).$promise;
  };

    $scope.rowSelect = function () {
      //console.log("rowSelect");
    };

    $scope.rowDeSelect = function () {
      //console.log("rowDeSelect");
    };

    $scope.openfolder = function (ev) {
      parrent = {"parrent":angular.copy(ev.path),"id":angular.copy(ev.id)};
      console.log(parrent);
      $scope.openchild();
    };

    $scope.edit = function (ev) {
      console.log(ev);
    };

    $scope.delete = function (ev) {
      console.log(ev);
    };

    $scope.download = function (ev) {
      console.log(ev);
    };

  $scope.refresh();

}]);

})(window.angular);
