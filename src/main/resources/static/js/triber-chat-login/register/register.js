'use strict';

var jsFiles = [
    '/js/triber-chat-login/common/password-compare.js'
];

angular.module('register', ['vcRecaptcha', jsFiles])
	.controller('RegisterController', function(vcRecaptchaService) {
		var vm = this;
		vm.submitAttempted = false;
		
		vm.register = function() {
			vm.submitAttempted = true;
			if(vm.registerForm.$error.recaptcha) {
				console.log('error');
			} else {
				console.log('no error');
			}
		}
		
		vm.recaptchaCreated = function(widgetId) {
			vm.widgetId = widgetId;
		}
		
	});