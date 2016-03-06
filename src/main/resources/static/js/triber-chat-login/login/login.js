'use strict';

var jsFiles = [ '/js/triber-chat-login/common/jwt.js', '/js/triber-chat-login/common/local-storage.js' ];

angular.module('login', [ 'ngResource', 'jwt', jsFiles ])
.factory('LoginResource', function($resource) {
	return $resource('/login', {}, {
		login : {
			method : 'POST',
			transformResponse : function(data, headers) {
				var response = {};
				response.data = data;
				response.headers = headers();
				return response;
			}
		}
	});
}).controller('LoginController', function(LoginResource, $window, jwt, toaster) {
	var vm = this;
	vm.submitAttempted = false;

	vm.init = function() {
		if (jwt.isValid()) {
			$window.location.href = '/chat.html';
		} else {
			jwt.clear();
		}
	};

	vm.login = function() {
		toaster.clear(undefined, 'loginFailedToastId');
		vm.submitAttempted = true;
		if (vm.loginForm.$invalid) {
			toaster.pop({
				type : 'warning',
				body : 'Please correct the login form.'
			});
		} else {
			LoginResource.login({
				username : vm.username,
				password : vm.password
			}).$promise.then(function(data) {
				var token = data.headers.authorization.substr(7, jwt.length);
				jwt.save(token);
				$window.location.href = '/chat.html';
			}, function() {
				toaster.pop({
					type : 'error',
					body : 'Login failed, please correct the login form and try again.',
					toastId : 'loginFailedToastId',
					timeout : 0
				});
			})
		}
	};

	vm.init();
});