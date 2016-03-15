'use strict';

angular.module('websocket', ['jwt'])
.factory('Websocket', function(JWT, $rootScope, $timeout) {
	var websocket = {};
	var subscriptions = [];
	var reconnectTimeout;
	//websocket.stomp.debug = null;
	
	websocket.connect = function() {
		websocket.client = new SockJS('/chat?jwt=' + JWT.get());
		websocket.stomp = Stomp.over(websocket.client);
		websocket.stomp.connect({}, function() {
			$rootScope.$emit('connected');
			$timeout.cancel(reconnectTimeout);
			angular.forEach(subscriptions, function(subscription) {
				websocket.stomp.subscribe(subscription.channel, function(message) {
					websocket.broadcast(subscription.eventName, message.body);
				});
			})
		}, function() {
			$rootScope.$apply(function() {
				reconnectTimeout =$timeout(function() {
					websocket.connect();
				}, 5000);
			});
		});
	}
	
	websocket.broadcast = function(eventName, content) {
		$rootScope.$emit(eventName, content);
	}
	
	websocket.subscribe = function(channel, eventName) {
		subscriptions.push({channel: channel, eventName: eventName});
		if(websocket.stomp.connected) {
			websocket.stomp.subscribe(channel, function(message) {
				websocket.broadcast(eventName, message);
			});
			return;
		}
	}
	
	websocket.connected = function() {
		return websocket.stomp && websocket.stomp.connected;
	}
	
//	websocket.send = function(channel, message) {
//		if(connected) {
//			websocket.stomp.send(channel, {}, message);
//			return;
//		}
//		sendInterval = $interval(function() {
//			console.log('send timeout');
//			if(connected) {
//				websocket.stomp.send(channel, {}, message);
//				$interval.cancel(sendInterval);
//				sendInterval = null;
//			}
//		}, 100);
//	}
	
	return websocket;
});