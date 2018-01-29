(function(angular) {
  'use strict';


var app = angular.module('XSCMS_ADMIN_DASHBOARD');

app.controller('FileManagerCtrl', [ '$scope', '$translate', '$cookies', '$config', 'item', 'fileNavigator', 'fileUploader', 'TemplateService','$modal','$popover',
    function($scope, $translate, $cookies, $config, Item, FileNavigator, FileUploader ,TemplateService, $modal, $popover) {

    $scope.appName = $config.appName;
    $scope.orderProp = ['model.type', 'model.name'];
    $scope.query = '';
    $scope.temp = new Item();
    $scope.fileNavigator = new FileNavigator();
    $scope.fileUploader = FileUploader;
    $scope.uploadFileList = [];
    $scope.viewTemplate = $cookies.viewTemplate || 'main-icons';
    $scope.language = $cookies.language || 'en';

    $translate.use($scope.language);
    $scope.fileNavigator.refresh();
    $scope.ModalTpl   ="";

    $scope.photo={};


    var TokenModal = {};
    var ContextPopover = null;
   // var myPopover = $popover(element, {title: 'My Title', content: 'My Content', trigger: 'manual'});

   $scope.modal = {
      "title": "Title",
      "content": "Hello Modal<br />This is a multiline message!",
      "backdrop": 'static',
      "keyboard": false
    };


    $scope.setTemplate = function(name) {
        console.log(name);
        $scope.viewTemplate = $cookies.viewTemplate = name;
    };

    $scope.changeLanguage = function (locale) {
        $scope.language = $cookies.language = locale;
        $translate.use(locale);
    };

    $scope.templateUrl = function($templ) {
       // return "foo-"+ $scope.fooId +"-bar-"+ $scope.barId +"-baz-"+ $scope.bazId +".html";
    }

    $scope.touch = function(item) {
        item = (item && item.revert && item) || new Item();
        item.revert && item.revert();
        $scope.temp = item;

    };

    $scope.initModal=function(tpl,item){
       // console.log(item);
        if (ContextPopover) {
           ContextPopover.hide();
        }

        if(TokenModal.$isShown===true){
            TokenModal.$promise.then(TokenModal.hide);
        }

        switch (tpl) {
           case 'mdelete':
               $scope.ModalTpl   ="/modal-delete.html";
              break;
           case 'mrename':
               $scope.ModalTpl   ="/modal-rename.html";
              break;
           case 'mcopy':
               $scope.ModalTpl   ="/modal-copy.html";
              break;
           case 'mcompress':
               $scope.ModalTpl   ="/modal-compress.html";
              break;
           case 'mextract':
               $scope.ModalTpl   ="/modal-extract.html";
              break;
           case 'medit':
               $scope.ModalTpl   ="/modal-edit.html";
              break;
           case 'mnewfolder':
               $scope.ModalTpl   ="/modal-new-folder.html";
              break;
           case 'muploadfile':
               $scope.ModalTpl   ="/modal-upload-file.html";
              break;
           case 'mpermission':
               $scope.ModalTpl   ="/modal-change-permissions.html";
              break;
           case 'imageview':
               $scope.ModalTpl   ="/modal-image-preview.html";
              break;
           default:
               $scope.ModalTpl   ="/modal-empty.html.html";
              break;
        }

        $scope.touch(item);
        TokenModal = $modal({scope: $scope, template: $scope.ModalTpl, placement:'center',backdrop:false,show:false,keyboard:false,animation:'am-fade'});

        if(TokenModal.$isShown===false){
            TokenModal.$promise.then(TokenModal.show);
        }
    };


    $scope.smartRightClick = function(item,$event) {
        console.log(item);
       var element=angular.element($event.target);

       if ($event.target.nodeName === 'I' || $event.target.nodeName === 'IMG') {
          element= angular.element($event.target).parent().parent().parent();
       } else if ($event.target.nodeName === 'DIV') {
          element= angular.element($event.target).parent().parent();
       }
       else {
          element= angular.element($event.target).parent();
       }

        if (ContextPopover) {
           ContextPopover.hide();
        }
        $scope.touch(item);
        ContextPopover = $popover(element, {title: '', placement:'bottom', contentTemplate:'/item-context-menu.html', html: true, trigger: 'manual', scope:$scope});
        ContextPopover.$promise.then(ContextPopover.show);

    };


    $scope.smartClick = function(item) {
        if (item.isFolder()) {
            return $scope.fileNavigator.folderClick(item);
        }else if (item.isImage()) {
          //  console.log(item);
          //  var eitem={src:item.model.url,desc:'test'};
            $scope.photo={src:item.model.url,desc:'test'};
            $scope.initModal('imageview',item);
            //return item.preview();
        }
        if (item.isEditable()) {
            item.getContent();
            $scope.touch(item);
            return;
        }
    };

    $scope.edit = function(item) {
        item.edit(function() {
            TokenModal.hide();
        });
    };

    $scope.changePermissions = function(item) {
        item.changePermissions(function() {
            TokenModal.hide();
        });
    };

    $scope.copy = function(item) {
        var samePath = item.tempModel.path.join() === item.model.path.join();
        if (samePath && $scope.fileNavigator.fileNameExists(item.tempModel.name)) {
            item.error = $translate.instant('error_invalid_filename');
            return false;
        }
        item.copy(function() {
            $scope.fileNavigator.refresh();
            TokenModal.hide();
        });
    };

    $scope.compress = function(item) {
        item.compress(function() {
            item.success = true;
            $scope.fileNavigator.refresh();
        }, function() {
            item.success = false;
        });
    };

    $scope.extract = function(item) {
        item.extract(function() {
            item.success = true;
            $scope.fileNavigator.refresh();
        }, function() {
            item.success = false;
        });
    };

    $scope.remove = function(item) {
        item.remove(function() {
            $scope.fileNavigator.refresh();
            TokenModal.hide();
        });
    };

    $scope.rename = function(item) {
        console.log(item);

        var samePath = item.tempModel.path.join() === item.model.path.join();
        if (samePath && $scope.fileNavigator.fileNameExists(item.tempModel.name)) {
            item.error = $translate.instant('error_invalid_filename');
            return false;
        }
        item.rename(function() {
            $scope.fileNavigator.refresh();
            TokenModal.hide();
        });

    };

    $scope.createFolder = function(item) {
        name = item.tempModel.name && item.tempModel.name.trim();
        item.tempModel.type = 'dir';
        item.tempModel.path = $scope.fileNavigator.currentPath;
        if (name && !$scope.fileNavigator.fileNameExists(name)) {
            item.createFolder(function() {
                $scope.fileNavigator.refresh();
                TokenModal.hide();
            });
        } else {
            $scope.temp.error = $translate.instant('error_invalid_filename');
            return false;
        }
    };

    $scope.uploadFiles = function() {
        $scope.fileUploader.upload($scope.uploadFileList,
            $scope.fileNavigator.currentPath, function() {
            $scope.fileNavigator.refresh();
            TokenModal.hide();
            $scope.uploadFileList=[];
        });
    };
}]);


//###########################################################################################################################################################################################



app.constant("$config", {
    appName: "FM",
    who:'SN_Media',
    Url: "../file/api/get-file-list",


    isEditableFilePattern: '\\.(txt|html|htm|aspx|asp|ini|pl|py|md|php|css|js|log|htaccess|htpasswd|json)$',
    isImageFilePattern: '\\.(jpg|jpeg|gif|bmp|png|svg|tiff)$',
    isExtractableFilePattern: '\\.(zip|gz|tar|rar|gzip)$'
});

app.service('chmod', function () {

    var Chmod = function() {
        var self = this;

        var values = {
            read: 0,
            write: 0,
            execute: 0
        };

        self.values = {
            owner: angular.copy(values),
            group: angular.copy(values),
            others: angular.copy(values)
        };
    };

    Chmod.prototype.getNumber = function() {
        return [
            +this.values.owner.read + +this.values.owner.write + +this.values.owner.execute,
            +this.values.group.read + +this.values.group.write + +this.values.group.execute,
            +this.values.others.read + +this.values.others.write + +this.values.others.execute
        ].join('');
    };

    Chmod.prototype.permissionValues = {
        read: 4, write: 2, execute: 1
    };

    return Chmod;
});

app.service('fileNavigator', ['$http', '$config', 'item', function ($http, $config, Item) {

    $http.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

    var FileNavigator = function() {
        this.requesting = false;
        this.fileList = [];
        this.currentPath = [];
        this.history = [];
        this.error = '';
    };

    FileNavigator.prototype.refresh = function(success, error) {
        var self = this;
        var path = self.currentPath.join('/');
        var data = {
            who: $config.who,
            what:"listdirectory",
            mode: "list",
            onlyFolders: false,
            path: '/' + path
        };

        self.requesting = true;
        self.fileList = [];
        self.error = '';


        $http.post($config.Url, data).success(function(data) {
            self.fileList = [];
            angular.forEach(data.result, function(file) {
                self.fileList.push(new Item(file, self.currentPath));

            });
            self.requesting = false;
            self.buildTree(path);

            if (data.error) {
                 console.log(data);
                self.error = data.error;
                return typeof error === 'function' && error(data);
            }
            typeof success === 'function' && success(data);
        }).error(function(data) {
            self.requesting = false;
            typeof error === 'function' && error(data);
        });
    };

    FileNavigator.prototype.buildTree = function(path) {
        var self = this;
        var recursive = function(parent, file, path) {
            var absName = path ? (path + '/' + file.name) : file.name;
            if (parent.name && !path.match(new RegExp('^' + parent.name))) {
                parent.nodes = [];
            }
            if (parent.name !== path) {
                for (var i in parent.nodes) {
                    recursive(parent.nodes[i], file, path);
                }
            } else {
                for (var i in parent.nodes) {
                    if (parent.nodes[i].name === absName) {
                        return;
                    }
                }
                parent.nodes.push({name: absName, nodes: []});
            }
        };

        !self.history.length && self.history.push({name: path, nodes: []});
        for (var i in self.fileList) {
            var file = self.fileList[i].model;
            file.type === 'dir' && recursive(self.history[0], file, path);
        }
    };

    FileNavigator.prototype.folderClickByName = function(fullPath) {
        var self = this;
        fullPath = fullPath.replace(new RegExp(/^\/*/g), '').split('/');
        self.currentPath = fullPath && fullPath[0] === "" ? [] : fullPath;
        self.refresh();
    };

    FileNavigator.prototype.folderClick = function(item) {
        var self = this;
        if (item && item.model.type === 'dir') {
            self.currentPath.push(item.model.name);
            self.refresh();
        }
    };

    FileNavigator.prototype.upDir = function() {
        var self = this;
        if (self.currentPath[0]) {
            self.currentPath = self.currentPath.slice(0, -1);
            self.refresh();
        }
    };

    FileNavigator.prototype.goTo = function(index) {
        var self = this;
        self.currentPath = self.currentPath.slice(0, index + 1);
        self.refresh();
    };

    FileNavigator.prototype.fileNameExists = function(fileName) {
        var self = this;
        for (var item in self.fileList) {
            item = self.fileList[item];
            if (fileName.trim && item.model.name.trim() === fileName.trim()) {
                return true;
            }
        }
    };

    FileNavigator.prototype.listHasFolders = function() {
        var self = this;
        for (var item in self.fileList) {
            if (self.fileList[item].model.type === 'dir') {
                return true;
            }
        }
    };

    return FileNavigator;
}]);

app.service('fileUploader', ['$http', '$config','DoService', function ($http, $config, DoService) {
    var self = this;

    self.requesting = false;
    self.upload = function(fileList, path, success, error) {

        var params={who:$config.who, what:'itemupload',filelist:fileList,destination:'/' + path.join('/')}
        self.requesting = true;
        var res=DoService.doPost(params,'service');

        res.success(function(data, status, headers, config) {
            self.requesting = false;
            typeof success === 'function' && success(data);
        }).error(function(data, status, headers, config) {self.requesting = false; typeof error === 'function' && error(data);});
    };
}]);



app.factory('item', ['$http', '$translate', '$config', 'chmod', function($http, $translate, $config, Chmod) {

    var Item = function(model, path) {
        var rawModel = {
            name: '',
            path: path || [],
            type: 'file',
            size: 0,
            //date: model && new Date(model.month + ' ' + model.day + ', ' + new Date().getFullYear() + ' ' + model.time + ':00'),
            //date: model && model.day + ' ' + model.month + ' - ' + model.time,
            date: model &&  model.time,
            perms: new Chmod(),
            content: '',
            sizeKb: function() {
                return Math.round(this.size / 1024, 1);
            },
            fullPath: function() {
                return ('/' + this.path.join('/') + '/' + this.name).replace(new RegExp('\/\/'), '/');
            }
        };

        this.error = '';
        this.inprocess = false;
        this.model = angular.copy(rawModel);
        this.tempModel = angular.copy(rawModel);

        this.update = function() {
            angular.extend(this.model, angular.copy(this.tempModel));
            return this;
        };

        this.revert = function() {
            angular.extend(this.tempModel, this.model);
            this.error = '';
            return this;
        };

        angular.extend(this.model, model);
        angular.extend(this.tempModel, model);
    };

    Item.prototype.defineCallback = function(data, success, error) {
        /* Check if there was some error in a 200 response */
        var self = this;
        if (data.result && data.result.error) {
            self.error = data.result.error;
            return typeof error === 'function' && error(data);
        }
        if (data.error) { //fz
            self.error = data.error.message;
            return typeof error === 'function' && error(data);
        }
        self.update();
        return typeof success === 'function' && success(data);
    };

    Item.prototype.createFolder = function(success, error) {
        var self = this;
        var data = {
            who: $config.who,
            what:"addfolder",
            path: self.tempModel.path.join('/'),
            name: self.tempModel.name
        };

        if (self.tempModel.name.trim()) {
            self.inprocess = true;
            self.error = '';
            $http.post($config.Url, data).success(function(data) {
                self.defineCallback(data, success, error);
            }).error(function(data) {
                self.error = data.result && data.result.error ?
                    data.result.error:
                    $translate.instant('error_creating_folder');
                typeof error === 'function' && error(data);
            }).finally(function() {
                self.inprocess = false;
            });
        }
        return self;
    };

    Item.prototype.rename = function(success, error) {
        var self = this;
        var data = {
            who: $config.who,
            what:"itemrename",
            "mode": "rename",
            "path": self.model.fullPath(),
            "newPath": self.tempModel.fullPath()
        };
        if (self.tempModel.name.trim()) {
            self.inprocess = true;
            self.error = '';
            $http.post($config.Url, data).success(function(data) {
                self.defineCallback(data, success, error);
            }).error(function(data) {
                self.error = data.result && data.result.error ?
                    data.result.error:
                    $translate.instant('error_renaming');
                typeof error === 'function' && error(data);
            }).finally(function() {
                self.inprocess = false;
            });
        }
        return self;
    };

    Item.prototype.copy = function(success, error) {
        var self = this;
        var data = {
            who: $config.who,
            what:"itemcopy",
            mode: "copy",
            path: self.model.fullPath(),
            newPath: self.tempModel.fullPath() //.replace(new RegExp(self.model.name + '$'), self.tempModel.name)
        };
        if (self.tempModel.name.trim()) {
            self.inprocess = true;
            self.error = '';
            $http.post($config.Url, data).success(function(data) {
                self.defineCallback(data, success, error);
            }).error(function(data) {
                self.error = data.result && data.result.error ?
                    data.result.error:
                    $translate.instant('error_copying');
                typeof error === 'function' && error(data);
            }).finally(function() {
                self.inprocess = false;
            });
        }
        return self;
    };

    Item.prototype.compress = function(success, error) {
        var self = this;
        var data = {
             who: $config.who,
            what:"itemcompress",
            mode: "compress",
            path: self.model.fullPath(),
            destination: self.tempModel.fullPath()
        };
        if (self.tempModel.name.trim()) {
            self.inprocess = true;
            self.error = '';
            $http.post($config.Url, data).success(function(data) {
                self.defineCallback(data, success, error);
            }).error(function(data) {
                self.error = data.result && data.result.error ?
                    data.result.error:
                    $translate.instant('error_compressing');
                typeof error === 'function' && error(data);
            }).finally(function() {
                self.inprocess = false;
            });
        }
        return self;
    };

    Item.prototype.extract = function(success, error) {
        var self = this;
        var data = {
            who: $config.who,
            what:"itemextract",
            mode: "extract",
            path: self.model.fullPath(),
            sourceFile: self.model.fullPath(),
            destination: self.tempModel.fullPath()
        };
        if (true) {
            self.inprocess = true;
            self.error = '';
            $http.post($config.Url, data).success(function(data) {
                self.defineCallback(data, success, error);
            }).error(function(data) {
                self.error = data.result && data.result.error ?
                    data.result.error:
                    $translate.instant('error_extracting');
                typeof error === 'function' && error(data);
            }).finally(function() {
                self.inprocess = false;
            });
        }
        return self;
    };

    Item.prototype.download = function(preview) {
        var self = this;
        var data = {
            who: $config.who,
            what:"itemdownload",
            mode: "download",
            preview: preview,
            path: self.model.fullPath()
        };
        var url = [$config.Url, $.param(data)].join('&');
        if (self.model.type !== 'dir') {
            window.open(url, '_blank', '');
        }
        return self;
    };

    Item.prototype.preview = function() {
        console.log(this);
        //var self = this;
       // return self.download(true);
    };

    Item.prototype.getContent = function(success, error) {
        var self = this;
        var data = {
            who: $config.who,
            what:"itemeditfile",
            mode: "editfile",
            path: self.tempModel.fullPath()
        };
        self.inprocess = true;
        self.error = '';
        $http.post($config.Url, data).success(function(data) {
            self.tempModel.content = self.model.content = data.result;
            self.defineCallback(data, success, error);
        }).error(function(data) {
            self.error = data.result && data.result.error ?
                data.result.error:
                $translate.instant('error_getting_content');
            typeof error === 'function' && error(data);
        }).finally(function() {
            self.inprocess = false;
        });
        return self;
    };

    Item.prototype.remove = function(success, error) {
        var self = this;
        var data = {
            who: $config.who,
            what:"itemdelete",
            mode: "delete",
            path: self.tempModel.fullPath()
        };
        self.inprocess = true;
        self.error = '';
        $http.post($config.Url, data).success(function(data) {
            self.defineCallback(data, success, error);
        }).error(function(data) {
            self.error = data.result && data.result.error ?
                data.result.error:
                $translate.instant('error_deleting');
            typeof error === 'function' && error(data);
        }).finally(function() {
            self.inprocess = false;
        });
        return self;
    };

    Item.prototype.edit = function(success, error) {
        var self = this;
        var data = {
            who: $config.who,
            what:"itemsavefile",
            mode: "savefile",
            content: self.tempModel.content,
            path: self.tempModel.fullPath()
        };
        self.inprocess = true;
        self.error = '';

        $http.post($config.Url, data).success(function(data) {
            self.defineCallback(data, success, error);
        }).error(function(data) {
            self.error = data.result && data.result.error ?
                data.result.error:
                $translate.instant('error_modifying');
            typeof error === 'function' && error(data);
        }).finally(function() {
            self.inprocess = false;
        });
        return self;
    };

    Item.prototype.changePermissions = function(success, error) {
        var self = this;
        var data = {
            who: $config.who,
            what:"itemchangepermissions",
            mode: "changepermissions",
            path: self.tempModel.fullPath(),
            perms: self.tempModel.perms.getNumber()
        };
        self.inprocess = true;
        self.error = '';
        $http({
            method: 'POST',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            url: $config.Url,
            data: $.param(data)
        }).success(function(data) {
            self.defineCallback(data, success, error);
        }).error(function(data) {
            self.error = data.result && data.result.error ?
                data.result.error:
                $translate.instant('error_modifying');
            typeof error === 'function' && error(data);
        }).finally(function() {
            self.inprocess = false;
        });
        return self;
    };

    Item.prototype.isFolder = function() {
        return this.model.type === 'dir';
    };

    Item.prototype.isEditable = function() {
        return !this.isFolder() && !!this.model.name.toLowerCase().match(new RegExp($config.isEditableFilePattern));
    };

    Item.prototype.isImage = function() {
        return !!this.model.name.toLowerCase().match(new RegExp($config.isImageFilePattern));
    };

    Item.prototype.isCompressible = function() {
        return this.isFolder();
    };

    Item.prototype.isExtractable = function() {
        return !!(!this.isFolder() && this.model.name.match($config.isExtractableFilePattern));
    };

    return Item;
}]);

app.config(function($translateProvider) {

    $translateProvider.translations('en', {
        language: "Language",
        english: "English",
        spanish: "Spanish",
        portuguese: "Portuguese",
        confirm: "Confirm",
        cancel: "Cancel",
        close: "Close",
        upload_file: "Upload file",
        files_will_uploaded_to: "Files will be uploaded to",
        uploading: "Uploading",
        permissions: "Permissions",
        select_destination_folder: "Select the destination folder",
        source: "Source",
        destination: "Destination",
        copy_file: "Copy file",
        sure_to_delete: "Are you sure to delete",
        change_name_move: "Change name / move",
        enter_new_name_for: "Enter new name for",
        extract_item: "Extract item",
        extraction_started: "Extraction started in a background process",
        compression_started: "Compression started in a background process",
        enter_folder_name_for_extraction: "Enter the folder name for the extraction of",
        enter_folder_name_for_compression: "Enter the folder name for the compression of",
        toggle_fullscreen: "Toggle fullscreen",
        edit_file: "Edit file",
        file_content: "File content",
        loading: "Loading",
        search: "Search",
        create_folder: "Create folder",
        create: "Create",
        folder_name: "Folder name",
        upload: "Upload",
        change_permissions: "Change permissions",
        change: "Change",
        details: "Details",
        icons: "Icons",
        list: "List",
        name: "Name",
        size: "Size",
        actions: "Actions",
        date: "Date",
        no_files_in_folder: "No files in this folder",
        no_folders_in_folder: "This folder not contains children folders",
        select_this: "Select this",
        go_back: "Go back",
        wait: "Wait",
        move: "Move",
        download: "Download",
        view_item: "View item",
        remove: "Delete",
        edit: "Edit",
        copy: "Copy",
        rename: "Rename",
        extract: "Extract",
        compress: "Compress",
        error_invalid_filename: "Invalid filename or already exists, specify another name",
        error_modifying: "An error occurred modifying the file",
        error_deleting: "An error occurred deleting the file or folder",
        error_renaming: "An error occurred renaming the file",
        error_copying: "An error occurred copying the file",
        error_compressing: "An error occurred compressing the file or folder",
        error_extracting: "An error occurred extracting the file",
        error_creating_folder: "An error occurred creating the folder",
        error_getting_content: "An error occurred getting the content of the file"
    });
});

app.directive('ngRightClick', function($parse) {
    return function(scope, element, attrs) {
        var fn = $parse(attrs.ngRightClick);
        element.bind('contextmenu', function(event) {
            scope.$apply(function() {
                event.preventDefault();
                fn(scope, {$event: event});
            });
        });
    };
});

})(window.angular);
