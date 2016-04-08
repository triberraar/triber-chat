'use strict';

angular.module('securityService', ['jwt', '_'])
	.factory('SecurityService', function(JWT, _) {
		var securityService= {
			getRoles: function() {
				return JWT.decode().roles || [];
			},
			hasRole: function(role) {
				return _.includes(securityService.getRoles(), role);
			}
		};
		return securityService;
	})
	.directive('hasPermission', function() {
		return {
			restrict: 'E',
			template: '<div ng-if="securityCtrl.allowed()"><ng-transclude></ng-transclude></div>',
			controller: 'SecurityController',
			controllerAs: 'securityCtrl',
			transclude: true,
			bindToController: {
				permission: '@'
			}
		};
	})
	.controller('SecurityController', function(SecurityService) {
		var vm = this;
		
		vm.allowed = function() {
			return SecurityService.hasRole(vm.permission );
		};
	});