'use strict';
var jsFiles = [
    'js/common/error-service.js',
    'js/common/success-service.js',
    'js/triber-chat/user/user.repository.js',
    'js/triber-chat/user/user.resource.js',
    'js/triber-chat/user/user.criteria.service.js',
    'js/triber-chat/user/user.criteria.component.js',
    'js/triber-chat/user/user.criteria.controller.js',
    'js/triber-chat/user/user.result.component.js',
    'js/triber-chat/user/user.result.controller.js'
];

angular.module('user.component', ['user.criteria.component', 'user.result.component', {files: jsFiles}])
    .component('user', {
        templateUrl: '/js/triber-chat/user/user.component.html'
    });