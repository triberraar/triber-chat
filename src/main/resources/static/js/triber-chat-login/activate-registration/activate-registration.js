'use strict';

var jsFiles = [
               'js/common/warning-service.js',
               'js/common/error-service.js',
               'js/common/success-service.js'
];

angular.module('activateRegistration', ['vcRecaptcha', jsFiles])
.factory('ActivateRegistrationResource', function($resource) {
	return $resource('/register/:userId/activate', {}, {
		register : {
			method : 'POST'
		}
	});
})
.controller('ActivateRegistrationController', function($state, $stateParams, ActivateRegistrationResource, WarningService, ErrorService, SuccessService) {
		var vm = this;
	vm.submitAttempted = false;
	
	vm.activateRegistration = function() {
		vm.submitAttempted = true;
		if (vm.activateRegistrationForm.$invalid) {
			WarningService.warn('Please correct the activate form.');
			return;
		}
		ActivateRegistrationResource.register({userId: $stateParams.userId}, {password: vm.password}).$promise.then(function(data){
			SuccessService.success('You are activated');
			$state.go('login');
		}, function(data){
			var toasterBody;
			if(data.data.errorCode) {
					toasterBody = data.data.errorCode;
			} 
			ErrorService.error(toasterBody);
		}); 
	}
	
});