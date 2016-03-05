angular.module('triber-chat-login', [ 'ui.router', 'ngMessages', 'ngResource', 'toaster', 'ngAnimate', 'oc.lazyLoad' ]).config(function($stateProvider, $urlRouterProvider) {
	$urlRouterProvider.otherwise("/login");
	$stateProvider.state('login', {
		url : '/login',
		templateUrl : 'js/triber-chat-login/login/login.html',
		controller : 'LoginController',
		controllerAs : 'loginCtrl',
		resolve : {
			lazyLoad : function($ocLazyLoad) {
				return $ocLazyLoad.load( '/js/triber-chat-login/login/login.js');
			}
		}
	}).state('register', {
		url : '/register',
		templateUrl : 'js/triber-chat-login/register/register.html'
	}).state('reset-password', {
		url : '/reset-password',
		templateUrl : 'js/triber-chat-login/reset-password/reset-password.html'
	});

});
