angular.module('jwt', ['angular-jwt', 'localStorage'])
	.factory('jwt', function(jwtHelper, $localStorage) {
		return {
			isValid: function() {
				var jwtToken = $localStorage.get('jwt');
				return jwtToken && !jwtHelper.isTokenExpired(jwtToken);
			},
			get: function() {
				return $localStorage.get('jwt');
			},
			save: function(token) {
				$localStorage.set('jwt', token);
			}
		}
	});