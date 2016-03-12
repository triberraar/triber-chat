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
			method : 'GET',
			isArray: true
		},
		validate: {
			method: 'PUT',
			params: {action: 'validate', userId: '@userId'}
		}
	});
})
.controller('UserController', function(UserResource, ErrorService, SuccessService) {
	var vm = this;
	
	vm.init = function(){
		vm.loadData();
	}
	
	vm.validate = function(user) {
		UserResource.validate({userId: user.id}).$promise.then(function() {
			SuccessService.success('User ' + user.username + ' validated');
			vm.loadData();
		}, function() {
			ErrorService.error('Validation failed');
			vm.loadData();
		})
	}
	
	vm.loadData = function() {
		UserResource.all().$promise.then(function(data) {
			vm.users = data;
		}, function() {
			ErrorService.error('Could not get users');
		});
	}
	
	vm.init();
	
});