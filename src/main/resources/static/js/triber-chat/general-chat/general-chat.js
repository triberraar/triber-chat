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
.controller('GeneralChatController', function($rootScope, _, ConnectedUsersResource,ErrorService, Websocket) {
	var vm = this;
	
	vm.loadData = function() {
		ConnectedUsersResource.all().$promise.then(function(data) {
			vm.users = data;
		},function() {
			ErrorService.error('Couldn\'t load connected users.')
		});
	}
	
	vm.connected = function() {
		return Websocket.connected();
	}
	
	vm.say = function() {
		if( vm.content != undefined && vm.content.trim() != "" && vm.messageForm.$valid) {
			Websocket.send('/app/message/general', {content: vm.content});
			vm.content=undefined;
		}
	}
	
	$rootScope.$on('connectedUser', function(event, message) {
		vm.loadData();
	});
	$rootScope.$on('disconnectedUser', function(event, message) {
		vm.loadData();
	});
		
	$rootScope.$on('connected', function(event, args) {
		vm.loadData();
	});
	$rootScope.$on('messageGeneral', function(event, args) {
		vm.messages.push(args);
		vm.messages = _.takeRight(vm.messages, 10);
	});
	
	vm.init = function() {
		vm.loadData();
		vm.messages = [];
	};
	
	vm.init();
})
.run(function(Websocket) {
	Websocket.subscribe('/topic/user/connected', 'connectedUser');
	Websocket.subscribe('/topic/user/disconnected', 'disconnectedUser');
	Websocket.subscribe('/topic/message/general', 'messageGeneral');
});