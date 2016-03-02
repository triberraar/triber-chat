angular.module('login', ['ngResource'])
	.factory('LoginFactory', function($resource) {
		return $resource('/login',{}, {
			login: {method: 'POST'}
		});
	})
	.controller('LoginController', function(LoginFactory, $window) {
		var _this = this;
		
		_this.submitAttempted = false;
		
		_this.login = function() {
			submitAttempted = true;
			LoginFactory.login({username: _this.username, password: _this.password}).$promise.then(function(data) {
				console.log('success');
				$window.location.href='/chat.html';
			},function() {
				console.log('failure');
			})
		}
	});