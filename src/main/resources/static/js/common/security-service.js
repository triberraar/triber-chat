'use strict';

angular.module('securityService', ['jwt'])
	.factory('SecurityService', function(JWT) {
		return {
			getRoles: function() {
				return JWT.decode().roles || [];
			}
		}
	});