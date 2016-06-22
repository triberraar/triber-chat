'use strict';

angular.module('notificationService', ['websocket', 'securityService', 'ngResource'])
.factory('UnvalidatedUserResource', function($resource) {
		return $resource('/user/unvalidated', {}, {
			existsUnvalidated : {
				method : 'GET'
			}
		});
	})
.factory('NotificationService', function(UnvalidatedUserResource, Websocket, $rootScope, SecurityService) {
	var notifications = {};
	var numberOfNotifications = 0;
	
	if(SecurityService.hasRole('ROLE_ADMIN')) {
		Websocket.subscribe('/topic/notifications/registeredUser', checkUnvalidatedUsers);
		Websocket.subscribe('/topic/notifications/validatedUser', checkUnvalidatedUsers);
		Websocket.onConnected(checkUnvalidatedUsers);
	}
	
	function checkUnvalidatedUsers() {
		UnvalidatedUserResource.existsUnvalidated().$promise.then(function() {
			notificationService.addNotification('unvalidatedUser', 'There are unvalidated users.');
		}, function() {
			notificationService.removeNotification('unvalidatedUser');
		});
	}
	
	var notificationService = {
			addNotification : function(key, notification) {
				if(!notifications[key]) {
					numberOfNotifications++;
				}
				notifications[key] = notification;
			},
			removeNotification: function(key) {
				if(notifications[key]) {
					numberOfNotifications--;
					notifications[key] = undefined;
				}
			},
			checkUnvalidatedUsers: checkUnvalidatedUsers,
			notifications : function() {
				return notifications;
			},
			notification: function(key) {
				return notifications[key];
			},
			numberOfNotifications: function() {
				return numberOfNotifications;
			}
	};
	return notificationService;
});