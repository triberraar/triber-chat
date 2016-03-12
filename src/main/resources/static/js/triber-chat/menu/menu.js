'use strict';

angular.module('menu', ['securityService', '_', 'notificationService'])
	.directive('menu', function() {
		return {
			replace: true,
			templateUrl: '/js/triber-chat/menu/menu.html',
			controller: 'MenuController',
			controllerAs: 'menuCtrl'
		}
	})
	.factory('UnvalidatedUserResource', function($resource) {
		return $resource('/user/unvalidated', {}, {
			existsUnvalidated : {
				method : 'GET'
			}
		});
	})
	.controller('MenuController', function(SecurityService, _, NotificationService, UnvalidatedUserResource) {
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
			UnvalidatedUserResource.existsUnvalidated().$promise.then(function() {
				NotificationService.addNotification('unvalidatedUser', 'There are unvalidated users.');
			}, function() {
			})
		}
		
		vm.init();
	});