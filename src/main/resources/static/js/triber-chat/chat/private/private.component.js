'use strict';
var jsFiles = [
    '/js/triber-chat/chat/private/private.service.js',
    '/js/triber-chat/chat/private/private.controller.js'
];

angular.module('chat.private.component', [{files: jsFiles}])
    .component('privateChat', {
        templateUrl: '/js/triber-chat/chat/private/private.component.html',
        controller: 'PrivateChatController',
        controllerAs: 'privateChatCtrl'
    });