'use strict';

angular.module('generalChat', ['errorService'])
.factory('ConnectedUsersResource', function($resource) {
	return $resource('/user/connected', {}, {
		all : {
			method : 'GET',
			isArray: true
		}
	});
})
.controller('GeneralChatController', function($rootScope, ConnectedUsersResource,ErrorService) {
	var vm = this;
	
	vm.loadData = function() {
		ConnectedUsersResource.all().$promise.then(function(data) {
			vm.users = data;
		},function() {
			ErrorService.error('Couldn\'t load connected users.')
		});
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
	
	vm.init = function() {
		vm.loadData();
	};
	
	vm.init();
})
.run(function(Websocket) {
	Websocket.subscribe('/topic/user/connected', 'connectedUser');
	Websocket.subscribe('/topic/user/disconnected', 'disconnectedUser');
});