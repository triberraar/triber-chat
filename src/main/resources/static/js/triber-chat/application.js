'use strict';

var dependencies = [
    'ui.router',
    'ngMessages',
    'ngResource',
    'ui.bootstrap',
    'toaster',
    'ngAnimate',
    'ngPasswordStrength',
    'vcRecaptcha',
    'angular-ladda',
    'NgSwitchery',
    'jwt',
    'paging',
    'noResults',
    'websocket',
    'menu',
    'notificationService',
    'autoscroll',
    'chatSlimScroll',

    'user.component',
    'chat.component'
];

angular.module('triber-chat', dependencies)
    .config(function ($stateProvider, $urlRouterProvider, $httpProvider) {
        $urlRouterProvider.otherwise('/chat/general');
        $httpProvider.interceptors.push('JWTInterceptor');
        $stateProvider
            .state('user', {
                url: '/user',
                template: '<user></user>'
            })
            .state('chat', {
                abstract: true,
                url: '/chat',
                templateUrl: 'js/triber-chat/chat/chat.html',
                controller: 'ChatController',
                controllerAs: 'chatCtrl'

            })
            .state('chat.general', {
                url: '/general',
                templateUrl: 'js/triber-chat/chat/general/general.html',
                controller: 'GeneralChatController',
                controllerAs: 'generalChatCtrl'
            })
            .state('chat.room', {
                url: '/room',
                templateUrl: 'js/triber-chat/chat/room/room.html'
            });

    })
    .run(function (JWT, $window, Websocket) {
        if (!JWT.isValid()) {
            $window.location.href = '/';
        }

        Websocket.connect();
    })
    .constant('MessageConfig', {
        numberOfMessages: 10
    });
