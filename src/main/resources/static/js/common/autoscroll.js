'use strict'

angular.module('autoscroll', [])
.directive('scrollBottom', function() {
	return {
		link: function(scope, element, attr) {
			scope.$watch(function() {
				return document.getElementById(attr.elementToScroll).scrollHeight;
			}, function() {
				var scroller = document.getElementById(attr.elementToScroll);
			      scroller.scrollTop = scroller.scrollHeight;
			})
		}
	}
})