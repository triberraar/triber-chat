'use strict';

angular.module('generalChat', ['errorService', '_'])
.factory('ConnectedUsersResource', function($resource) {
	return $resource('/user/connected', {}, {
		all : {
			method : 'GET',
			isArray: true
		}
	});
})
.factory('GeneralChatService', function($rootScope, _, Websocket) {
	var messages = [];
	
	var generalMessageBroadcast = $rootScope.$on('messageGeneral', function(event, args) {
		messages.push(args);
		messages = _.takeRight(messages, 10);
	});
	
	var generalChatService = {
		getMessages: function() {
			return messages;
		},
		sendMessage: function(message) {
			Websocket.send('/app/message/general', message);
		}
	};
	
	return generalChatService;
	
})
.factory('ConnectedUserService', function($rootScope, ConnectedUsersResource, ErrorService) {
	var users = [];
	var loadData = function() {
		ConnectedUsersResource.all().$promise.then(function(data) {
			users = data;
		},function() {
			ErrorService.error('Couldn\'t load connected users.');
		});
	};
	
	var connectedUserBroadcast = $rootScope.$on('connectedUser', function(event, message) {
		loadData();
	});
	var disconnectedUserBroadcast = $rootScope.$on('disconnectedUser', function(event, message) {
		loadData();
	});
		
	var connectedBroadcast = $rootScope.$on('connected', function(event, args) {
		loadData();
	});

	loadData();
	
	var connectedUserService = {
		getUsers: function() {
			return users;
		}
	};
	
	return connectedUserService;
})
.controller('GeneralChatController', function($rootScope, _, ConnectedUsersResource,ErrorService, Websocket, GeneralChatService, ConnectedUserService) {
	var vm = this;
	
	vm.connected = function() {
		return Websocket.connected();
	};
	
	vm.say = function() {
		if( angular.isDefined( vm.content) && vm.content.trim() != '' && vm.messageForm.$valid) {
			GeneralChatService.sendMessage({content: vm.content});
			vm.content=undefined;
		}
	};
	
	vm.messages = function() {
		return GeneralChatService.getMessages();
	};
	
	vm.users = function() {
		return ConnectedUserService.getUsers();
	};
})
.run(function(Websocket) {
	Websocket.subscribe('/topic/user/connected', 'connectedUser');
	Websocket.subscribe('/topic/user/disconnected', 'disconnectedUser');
	Websocket.subscribe('/topic/message/general', 'messageGeneral');
});