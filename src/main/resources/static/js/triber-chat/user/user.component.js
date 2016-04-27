'use strict';
var jsFiles = [
    'js/common/error-service.js',
    'js/common/success-service.js',
    '/js/triber-chat/user/user.controller.js',
    'js/triber-chat/user/user.repository.js',
    'js/triber-chat/user/user.resource.js',
    'js/triber-chat/user/user.criteria.service.js'
];

angular.module('user.directive', ['user.controller', {files: jsFiles}])
    .component('users', {
        controller: 'UserController',
        controllerAs: 'userCtrl',
        templateUrl: '/js/triber-chat/user/user.html'
    });