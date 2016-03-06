'use strict';

var dependencies = [ 
                     'ui.router', 
                     'ngMessages', 
                     'ngResource', 
                     'toaster', 
                     'ngAnimate', 
                     'oc.lazyLoad',
                     'ngPasswordStrength',
                     'vcRecaptcha'];

angular.module('triber-chat-login', dependencies).config(function($stateProvider, $urlRouterProvider) {
	$urlRouterProvider.otherwise('/login');
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
		templateUrl : 'js/triber-chat-login/register/register.html',
		controller: 'RegisterController',
		controllerAs: 'registerCtrl',
		resolve : {
			lazyLoad : function($ocLazyLoad) {
				return $ocLazyLoad.load( '/js/triber-chat-login/register/register.js');
			}
		}
	}).state('activate-registration', {
		url: '/activate-registration/:userId',
		templateUrl: 'js/triber-chat-login/activate-registration/activate-registration.html',
		controller: 'ActivateRegistrationController',
		controllerAs: 'activateRegistrationCtrl',
		resolve: {
			lazyLoad: function($ocLazyLoad){
				return $ocLazyLoad.load('js/triber-chat-login/activate-registration/activate-registration.js');
			}
		}
	})
	.state('reset-password', {
		url : '/reset-password',
		templateUrl : 'js/triber-chat-login/reset-password/reset-password.html'
	});

});
