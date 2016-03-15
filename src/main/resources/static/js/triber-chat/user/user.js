'use strict';

var jsFiles = [ 
               'js/common/warning-service.js',
               'js/common/error-service.js',
               'js/common/success-service.js',
];

angular.module('User', [jsFiles])
.factory('UserResource', function($resource){
	return $resource('/user/:userId/:action', {}, {
		all : {
			method : 'GET'
		},
		validate: {
			method: 'PUT',
			params: {action: 'validate', userId: '@userId'}
		}
	});
})
.controller('UserController', function($rootScope, UserResource, ErrorService, SuccessService) {
	var vm = this;
	
	$rootScope.$on('registeredUser', function(event, args) {
		vm.loadData();
	});
	$rootScope.$on('validatedUser', function(event, args) {
		vm.loadData();
	});
	
	vm.init = function(){
		vm.currentPage = 1;
		vm.validating = false;
		vm.validated = false;
		vm.activated = false;
		vm.users=[];
		
		vm.loadData();
	}
	
	vm.validate = function(user) {
		if(vm.validating) {
			return;
		}
		vm.validating = true;
		UserResource.validate({userId: user.id}).$promise.then(function() {
			vm.validating = false;
			SuccessService.success('User ' + user.username + ' validated');
			vm.loadData();
		}, function() {
			vm.validating = false;
			ErrorService.error('Validation failed');
			vm.loadData();
		});
	}
	
	vm.loadData = function() {
		UserResource.all({page: vm.currentPage - 1, validated: vm.validated, activated: vm.activated, username: vm.username, email: vm.email}).$promise.then(function(data) {
			vm.users = data.content;
			vm.totalElements = data.totalElements;
			vm.itemsPerPage = data.size;
		}, function() {
			ErrorService.error('Could not get users');
		});
	}
	
	vm.init();
	
});