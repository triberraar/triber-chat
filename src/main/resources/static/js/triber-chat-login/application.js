angular.module('triber-chat-login', [ 'ui.router', 'ngMessages', 'ngResource', 'login', 'register', 'resetPassword' ])
	.config(function($stateProvider, $urlRouterProvider) {
		$urlRouterProvider.otherwise("/login");
		$stateProvider
			.state('login', {
				url: '/login',
				templateUrl: 'js/triber-chat-login/login/login.html',
				controller: 'LoginController',
				controllerAs: 'loginCtrl'
			})
			.state('register', {
				url: '/register',
				templateUrl: 'js/triber-chat-login/register/register.html'
			})
			.state('reset-password', {
				url: '/reset-password',
				templateUrl: 'js/triber-chat-login/reset-password/reset-password.html'
			});
		
	});
