'use strict';

angular.module('menu', ['securityService', '_', 'notificationService', 'websocket', 'jwt'])
	.directive('menu', function() {
		return {
			replace: true,
			templateUrl: '/js/triber-chat/menu/menu.html',
			controller: 'MenuController',
			controllerAs: 'menuCtrl'
		};
	})
	.controller('MenuController', function(SecurityService, _, NotificationService, Websocket, JWT, $window) {
		var vm = this;
		
		vm.showAdmin = function() {
			return SecurityService.hasRole('ROLE_ADMIN');
		};
		
		vm.numberOfNotifications = function() {
			return NotificationService.numberOfNotifications();
		};
		
		vm.notification = function(key) {
			return NotificationService.notification(key);
		};

		vm.init = function() {
			if(SecurityService.hasRole('ROLE_ADMIN')) {
				NotificationService.checkUnvalidatedUsers();
			}
		};
		
		vm.connected = function() {
			return Websocket.connected();
		};

		vm.logout = function() {
			JWT.clear();
			$window.location.href = '/';
		};
		
		
		vm.init();
	});