'use strict';

var jsFiles = [
    'js/common/error-service.js',
    'js/common/success-service.js',
    'js/triber-chat/user/user.repository.js',
    'js/triber-chat/user/user.resource.js'
];

angular.module('user.controller', ['errorService', 'successService', 'user.repository', 'user.resource', {files: jsFiles}])
    .controller('UserController', function ($rootScope, $scope, UserResource, ErrorService, SuccessService, UserRepository) {
        var vm = this;

        vm.registeredUserBroadcast = $rootScope.$on('registeredUser', function () {
            vm.loadData();
        });
        vm.validatedUserBroadcast = $rootScope.$on('validatedUser', function () {
            vm.loadData();
        });

        vm.init = function () {
            vm.currentPage = 1;
            vm.validating = false;
            vm.validated = false;
            vm.activated = false;
            vm.users = [];

            vm.loadData();
        };

        vm.validate = function (user) {
            if (vm.validating) {
                return;
            }
            vm.validating = true;
            UserResource.validate({userId: user.id}).$promise.then(function () {
                vm.validating = false;
                SuccessService.success('User ' + user.username + ' validated');
                vm.loadData();
            }, function () {
                vm.validating = false;
                ErrorService.error('Validation failed');
                vm.loadData();
            });
        };

        vm.loadData = function () {
            UserRepository.all({
                page: vm.currentPage - 1,
                validated: vm.validated,
                activated: vm.activated,
                username: vm.username,
                email: vm.email
            }).then(function () {
                vm.users = UserRepository.users;
                vm.totalElements = UserRepository.totalElements;
                vm.itemsPerPage = UserRepository.size;
            });

        };

        vm.init();

        $scope.$on('$destroy', function () {
            vm.registeredUserBroadcast();
            vm.validatedUserBroadcast();
        });

    });