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
                     'menu',
                     'jwt'];

angular.module('triber-chat', dependencies)
.config(function($stateProvider, $urlRouterProvider, $httpProvider, jwtInterceptorProvider) {
	
	$httpProvider.interceptors.push('JWTInterceptor');
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
