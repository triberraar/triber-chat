angular.module('login', ['ngResource', 'common'])
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
	.controller('LoginController', function(LoginFactory, $window, $localStorage) {
		var _this = this;
		
		_this.submitAttempted = false;
		
		_this.login = function() {
			submitAttempted = true;
			LoginFactory.login({username: _this.username, password: _this.password}).$promise.then(function(data) {
				console.log('success');
				var jwt = data.headers.authorization;
				$localStorage.set('jwt', jwt.substr(7, jwt.length));
				$window.location.href='/chat.html';
			},function() {
				console.log('failure');
			})
		}
	});