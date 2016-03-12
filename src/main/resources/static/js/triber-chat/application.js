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
                     'menu',
                     'jwt',
                     'paging',
                     'noResults'];

angular.module('triber-chat', dependencies)
.config(function($stateProvider, $urlRouterProvider, $httpProvider, jwtInterceptorProvider) {
	
	$httpProvider.interceptors.push('JWTInterceptor');
	$stateProvider.state('user', {
		url:'/user',
		templateUrl: 'js/triber-chat/user/user.html',
		controller: 'UserController',
		controllerAs: 'userCtrl',
		resolve: {
			lazyLoad: function($ocLazyLoad) {
				return $ocLazyLoad.load('/js/triber-chat/user/user.js');
			}
		}
	})
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
.run(function(JWT, $window) {
	if(!JWT.isValid()) {
		$window.location.href = '/';
	}
});
