'use strict';

var dependencies = [ 
                     'ui.router', 
                     'ngMessages', 
                     'ngResource', 
                     'ui.bootstrap',
                     'toaster', 
                     'ngAnimate', 
                     'oc.lazyLoad',
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
                     'chatSlimScroll'
                     ];

angular.module('triber-chat', dependencies)
.config(function($stateProvider, $urlRouterProvider, $httpProvider) {
	$urlRouterProvider.otherwise('/chat/general');
	$httpProvider.interceptors.push('JWTInterceptor');
	$stateProvider
		.state('user', {
			url:'/user',
			template: '<user></user>',
			resolve: {
				lazyLoad: function($ocLazyLoad) {
					return $ocLazyLoad.load(['/js/triber-chat/user/user.component.js']);
				}
			}
		})
		.state('chat', {
			abstract: true,
			url:'/chat',
			templateUrl: 'js/triber-chat/chat/chat.html',
			controller: 'ChatController',
			controllerAs: 'chatCtrl',
			resolve: {
				lazyLoad: function($ocLazyLoad) {
					return $ocLazyLoad.load(['/js/triber-chat/chat/chat.js', '/js/triber-chat/chat/private/private.component.js']);
				}
			}
		})
		.state('chat.general', {
			url: '/general',
			templateUrl: 'js/triber-chat/chat/general/general.html',
			controller: 'GeneralChatController',
			controllerAs: 'generalChatCtrl',
			resolve: {
				lazyLoad: function($ocLazyLoad) {
					return $ocLazyLoad.load('/js/triber-chat/chat/general/general.js');
				}
			}
		})
		.state('chat.room', {
			url: '/room',
			templateUrl: 'js/triber-chat/chat/room/room.html'
		});
//	$urlRouterProvider.otherwise('/home');
//	$stateProvider.state('/home', {
//		url : '/home',
//		templateUrl : 'js/triber-chat/home/home.html',
//		controller : 'HomeController',
//		controllerAs : 'homeCtrl',
//		resolve : {
//			lazyLoad : function($ocLazyLoad) {
//				return $ocLazyLoad.load( '/js/triber-chat/home/home.js');
//			}
//		}
//	});

})
.run(function(JWT, $window, Websocket) {
	if(!JWT.isValid()) {
		$window.location.href = '/';
	}
	
	Websocket.connect();
});
