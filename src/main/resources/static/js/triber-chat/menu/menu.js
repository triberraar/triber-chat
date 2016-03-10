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
	.factory('UserResource', function($resource) {
		return $resource('/user/:action', {}, {
			existsUnvalidated : {
				method : 'GET',
				params: {action: 'unvalidated'}
			}
		});
	})
	.controller('MenuController', function(SecurityService, _, NotificationService, UserResource) {
		var vm = this;
		
		vm.showAdmin = function() {
			return _.includes(SecurityService.getRoles(), 'ROLE_ADMIN');
		}
		
		vm.numberOfNotifications = function() {
			return NotificationService.numberOfNotifications();
		}
		
		vm.notification = function(key) {
			return NotificationService.notification(key);
		}
		
		vm.init = function() {
			UserResource.existsUnvalidated().$promise.then(function() {
				NotificationService.addNotification('unvalidatedUser', 'There are unvalidated users.');
			}, function() {
			})
		}
		
		vm.init();
	});