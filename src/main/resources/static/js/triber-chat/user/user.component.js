'use strict';
var jsFiles = [
    'js/triber-chat/user/user.controller.js'
];

angular.module('user.directive', ['user.controller', {files: jsFiles}])
    .component('users', {
        controller: 'UserController',
        controllerAs: 'userCtrl',
        templateUrl: '/js/triber-chat/user/user.html'
    });