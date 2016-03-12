'use strict'

angular.module('paging', [])
.directive('paging', function() {
	return {
		template: '<uib-pagination total-items="pagingCtrl.totalElements" ng-model="pagingCtrl.currentPage" max-size="pagingCtrl.maxSize" items-per-page="pagingCtrl.itemsPerPage" class="pagination-sm" force-ellipses="true" previous-text="&lsaquo;" next-text="&rsaquo;" first-text="&laquo;" last-text="&raquo;"></uib-pagination>',
		controller: 'PagingController',
		controllerAs: 'pagingCtrl',
		bindToController: {
			totalElements: '=',
			onPageChange: '&',
			currentPage: '='
		}
	}
})
.controller('PagingController', function($scope) {
	var vm = this;
	
	vm.maxSize = 5;
	vm.itemsPerPage = 20;
	
	$scope.$watch(angular.bind(this, function() {
		return vm.currentPage;
	}), function() {
		vm.onPageChange();
	});
	
});