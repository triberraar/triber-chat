'use strict';

angular.module('localStorage', [])
.factory('$localStorage', function($window) {
	return {
		set: function(key, value) {
			$window.localStorage[key]=value;
		},
		get: function(key) {
			return $window.localStorage[key];
		},
		setObject: function(key, value) {
			set(key, angular.fromJson(value));
		},
		getObject: function(key) {
			return angular.toJson(get(key));
		}
	}
});