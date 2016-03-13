'user strict'

angular.module('notificationService', ['websocket'])
.factory('UnvalidatedUserResource', function($resource) {
		return $resource('/user/unvalidated', {}, {
			existsUnvalidated : {
				method : 'GET'
			}
		});
	})
.factory('NotificationService', function(UnvalidatedUserResource, Websocket, $rootScope) {
	var notifications = {};
	var numberOfNotifications = 0;
	
	Websocket.subscribe('/topic/notifications/registeredUser', 'registeredUser');
	Websocket.subscribe('/topic/notifications/validatedUser', 'registeredUser');
	
	$rootScope.$on('registeredUser', function(message) {
		notificationService.checkUnvalidatedUsers();
	});
	$rootScope.$on('validatedUser', function(message) {
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
	}
	return notificationService;
})