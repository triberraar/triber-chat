'use strict';

var jsFiles = [
    '/js/triber-chat-login/common/password-compare.js',
    '/js/triber-chat-login/common/lodash.js'
];

angular.module('register', ['vcRecaptcha', jsFiles])
.factory('RegisterResource', function($resource) {
	return $resource('/register', {}, {
		register : {
			method : 'POST'
		}
	});
})
.controller('RegisterController', function(vcRecaptchaService, toaster, RegisterResource, _) {
		var vm = this;
	vm.submitAttempted = false;
	vm.registering = false;
	
	vm.register = function() {
		vm.submitAttempted = true;
		if (vm.registerForm.$invalid) {
			toaster.pop({
				type : 'warning',
				body : 'Please correct the register form.'
			});
			return;
		}
		vm.registering = true;
		var registration = {
				username: vm.username,
				email: vm.email,
				password: vm.password,
				captcha: vm.recaptcha
		}
		RegisterResource.register(registration).$promise.then(function(data){
			toaster.pop({
				type: 'success',
				body: 'Registration successful, you will receive an email.'
			});
			vm.registering = false;
		}, function(data){
			vm.registering = false;
			vm.refreshRecaptcha();
			var toasterBody = "";
			if(data.data.errors) {
				angular.forEach(data.data.errors, function(error) {
					toasterBody = toasterBody + error + "<br>";
				});
			} else {
				toasterBody = "Unknown error"
			}
			toaster.pop({
				type: 'error',
				body:toasterBody,
				bodyOutputType: 'trustedHtml'
			});
		}) 
	}
	
	vm.recaptchaCreated = function(widgetId) {
		vm.widgetId = widgetId;
	}
	
	vm.refreshRecaptcha = function() {
		vcRecaptchaService.reload(vm.widgetId);
		vm.recaptcha = undefined;
	}
	
});