angular.module('login', ['ngResource', 'jwt'])
	.factory('LoginFactory', function($resource) {
		return $resource('/login',{}, {
			login: {method: 'POST',
				transformResponse: function(data, headers) {
					response = {};
					response.data = data;
					response.headers = headers();
					return response;
				}}
		});
	})
	.controller('LoginController', function(LoginFactory, $window, jwt, toaster) {
		var _this = this;
		_this.submitAttempted = false;

		
		
		_this.init = function() {
			if(jwt.isValid()) {
				$window.location.href='/chat.html';
			}
		}
		
		_this.login = function() {
			toaster.clear(undefined, 'loginFailedToastId');
			_this.submitAttempted = true;
			if(_this.loginForm.$invalid) {
				 toaster.pop({
			            type: 'warning',
			            body: 'Please correct the login form.'
			        });
			} else {
				LoginFactory.login({username: _this.username, password: _this.password}).$promise.then(function(data) {
					var token = data.headers.authorization.substr(7, jwt.length);
					jwt.save(token);
					$window.location.href='/chat.html';
				},function() {
					toaster.pop({
			            type: 'error',
			            body: 'Login failed, please correct the login form and try again.',
			            toastId: 'loginFailedToastId',
			            timeout: 0
			        });
				})
			}
		}
		
		_this.init();
	});