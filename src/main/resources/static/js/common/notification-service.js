'user strict'

angular.module('notificationService', [])
.factory('NotificationService', function() {
	var notifications = {};
	var numberOfNotifications = 0;
	var notificationService = {
			addNotification : function(key, notification) {
				if(!notifications.key) {
					numberOfNotifications++;
				}
				return notifications[key] = notification;
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