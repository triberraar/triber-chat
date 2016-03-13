'use strict';

angular.module('menu', ['securityService', '_', 'notificationService', 'websocket'])
	.directive('menu', function() {
		return {
			replace: true,
			templateUrl: '/js/triber-chat/menu/menu.html',
			controller: 'MenuController',
			controllerAs: 'menuCtrl'
		}
	})
	.controller('MenuController', function(SecurityService, _, NotificationService, Websocket) {
		var vm = this;
		
		vm.showAdmin = function() {
			return SecurityService.hasRole('ROLE_ADMIN');
		}
		
		vm.numberOfNotifications = function() {
			return NotificationService.numberOfNotifications();
		}
		
		vm.notification = function(key) {
			return NotificationService.notification(key);
		}

		vm.init = function() {
			NotificationService.checkUnvalidatedUsers();
			
		}
		
		
		vm.init();
	});