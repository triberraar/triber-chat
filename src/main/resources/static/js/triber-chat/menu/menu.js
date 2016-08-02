'use strict';

angular.module('menu', ['securityService', '_', 'notificationService', 'websocket', 'jwt', 'ng', 'ui.router.state'])
	.directive('menu', function() {
		return {
			replace: true,
			templateUrl: '/js/triber-chat/menu/menu.html',
			controller: 'MenuController',
			controllerAs: 'menuCtrl'
		};
	})
	.controller('MenuController', function(SecurityService, _, NotificationService, Websocket, JWT, $window, $state) {
		var vm = this;
		
		vm.numberOfNotifications = NotificationService.numberOfNotifications;

		vm.notifications = NotificationService.notifications;

		vm.goToNotification = function(notification) {
			if(notification.link) {
				$state.go(notification.link);
			}
		};

		vm.init = function() {

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