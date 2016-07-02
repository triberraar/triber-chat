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

	Websocket.subscribe('/topic/message/general', function(message) {
		messages.push(message);
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
.factory('ConnectedUserService', function($rootScope, ConnectedUsersResource, ErrorService, Websocket) {
	var users = [];
	var loadData = function() {
		ConnectedUsersResource.all().$promise.then(function(data) {
			users = data;
		},function() {
			ErrorService.error('Couldn\'t load connected users.');
		});
	};

	Websocket.subscribe('/topic/user/connected', loadData);
	Websocket.subscribe('/topic/user/disconnected', loadData);

	Websocket.onConnected(loadData);
	
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

	vm.clickedUser = function(user) {
		console.log('clicked ' + user);
	}
});