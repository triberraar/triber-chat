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
			set(key, JSON.stringify(value));
		},
		getObject: function(key) {
			return JSON.parse(get(key));
		}
	}
})