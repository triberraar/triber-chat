'use strict';

var jsFiles = [
];

angular.module('activateRegistration', ['vcRecaptcha', jsFiles])
.factory('ActivateRegistrationResource', function($resource) {
	return $resource('/register/:userId/activate', {}, {
		register : {
			method : 'POST'
		}
	});
})
.controller('ActivateRegistrationController', function($state, $stateParams, ActivateRegistrationResource, toaster) {
		var vm = this;
	vm.submitAttempted = false;
	
	vm.activateRegistration = function() {
		vm.submitAttempted = true;
		if (vm.activateRegistration.$invalid) {
			toaster.pop({
				type : 'warning',
				body : 'Please correct the activate form.'
			});
			return;
		}
		ActivateRegistrationResource.register({userId: $stateParams.userId}, {password: vm.password}).$promise.then(function(data){
			$state.go('login');
		}, function(data){
			var toasterBody;
			if(data.data.errorCode) {
					toasterBody = data.data.errorCode;
			} else {
				toasterBody = "Unknown error";
			}
			toaster.pop({
				type: 'error',
				body: toasterBody,
				bodyOutputType: 'trustedHtml'
			});
		}); 
	}
	
});