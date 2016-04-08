'use strict';

angular.module('notificationService', ['websocket', 'securityService'])
.factory('UnvalidatedUserResource', function($resource) {
		return $resource('/user/unvalidated', {}, {
			existsUnvalidated : {
				method : 'GET'
			}
		});
	})
.factory('NotificationService', function(UnvalidatedUserResource, Websocket, $rootScope,SecurityService) {
	var notifications = {};
	var numberOfNotifications = 0;
	
	if(SecurityService.hasRole('ROLE_ADMIN')) {
		Websocket.subscribe('/topic/notifications/registeredUser', 'registeredUser');
		Websocket.subscribe('/topic/notifications/validatedUser', 'validatedUser');
	}
	
	var registeredUserBroadcast = $rootScope.$on('registeredUser', function(event, args) {
		notificationService.checkUnvalidatedUsers();
	});
	var validatedUserBroadcast = $rootScope.$on('validatedUser', function(event, args) {
		notificationService.checkUnvalidatedUsers();
	});
	var conntectedBroadcast = $rootScope.$on('connected', function(event, args) {
		notificationService.checkUnvalidatedUsers();
	});
	
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
			checkUnvalidatedUsers: function() {
				UnvalidatedUserResource.existsUnvalidated().$promise.then(function() {
					notificationService.addNotification('unvalidatedUser', 'There are unvalidated users.');
				}, function() {
					notificationService.removeNotification('unvalidatedUser');
				});
			},
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