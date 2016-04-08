'use strict';

var jsFiles = [
               '/js/common/password-compare.js',
               'js/common/warning-service.js',
               'js/common/error-service.js',
               'js/common/success-service.js'
           ];

angular.module('resetPassword', [jsFiles])
	.factory('ResetPasswordResource', function($resource) {
		return $resource('/reset-password/:resetPasswordId', {}, {
			reset : {
				method : 'POST'
			}, 
			confirm : {
				method: 'POST'
			}
		});
	})
	.controller('ResetPasswordController', function(ResetPasswordResource, WarningService, SuccessService, ErrorService) {
		var vm = this;
		
		vm.resetPassword = function() {
			vm.submitAttempted = true;
			if (vm.resetPasswordForm.$invalid) {
				WarningService.warn('Please correct the reset password form.');
				return;
			}
			vm.resetting = true;
			ResetPasswordResource.reset({'email': vm.email}).$promise.then(function(data){
				SuccessService.success('Reset password successful, you will receive an email.');
				vm.resetting = false;
			}, function(data){
				vm.resetting = false;
				var toasterBody;
				if(data.data.errorCode) {
					toasterBody = data.data.errorCode;
				} 
				ErrorService.error(toasterBody);
			});
		};
	})
	.controller('ConfirmResetPasswordController', function($state, $stateParams, ResetPasswordResource, WarningService, ErrorService, SuccessService) {
		var vm = this;
		
		vm.confirmResetPassword = function() {
			vm.submitAttempted = true;
			if (vm.confirmResetPasswordForm.$invalid) {
				WarningService.warn('Please correct the confirm password form.');
				return;
			}
			ResetPasswordResource.confirm({resetPasswordId: $stateParams.resetPasswordId}, {password: vm.password}).$promise.then(function(data) {
				SuccessService.success('Password confirmed.');
				$state.go('login');
			}, function(data){
				var toasterBody;
				if(data.data.errorCode) {
					toasterBody = data.data.errorCode;
				}
				ErrorService.error(toasterBody);
			});
		};
	});