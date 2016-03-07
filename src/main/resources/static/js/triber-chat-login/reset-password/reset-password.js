'use strict';

var jsFiles = [
               '/js/triber-chat-login/common/password-compare.js'
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
	.controller('ResetPasswordController', function(toaster, ResetPasswordResource) {
		var vm = this;
		
		vm.resetPassword = function() {
			vm.submitAttempted = true;
			if (vm.resetPasswordForm.$invalid) {
				toaster.pop({
					type : 'warning',
					body : 'Please correct the reset password form.'
				});
				return;
			}
			vm.resetting = true;
			ResetPasswordResource.reset({'email': vm.email}).$promise.then(function(data){
				toaster.pop({
					type: 'success',
					body: 'Reset password successful, you will receive an email.'
				});
				vm.resetting = false;
			}, function(data){
				vm.resetting = false;
				var toasterBody = "";
				if(data.data.errorCode) {
					toasterBody = data.data.errorCode
				} else {
					toasterBody = "Unknown error"
				}
				toaster.pop({
					type: 'error',
					body:toasterBody,
					bodyOutputType: 'trustedHtml'
				});
			}) 
		}
	})
	.controller('ConfirmResetPasswordController', function($state, $stateParams, toaster, ResetPasswordResource) {
		var vm = this;
		
		vm.confirmResetPassword = function() {
			vm.submitAttempted = true;
			if (vm.confirmResetPasswordForm.$invalid) {
				toaster.pop({
					type : 'warning',
					body : 'Please correct the confirm password form.'
				});
				return;
			}
			ResetPasswordResource.confirm({resetPasswordId: $stateParams.resetPasswordId}, {password: vm.password}).$promise.then(function(data){
				$state.go('login');
			}, function(data){
				var toasterBody = "";
				if(data.data.errorCode) {
					toasterBody = data.data.errorCode
				} else {
					toasterBody = "Unknown error"
				}
				toaster.pop({
					type: 'error',
					body:toasterBody,
					bodyOutputType: 'trustedHtml'
				});
			}) 
		}
	});