'use strict';

angular.module('user.controller', ['errorService', 'successService', 'user.repository', 'user.resource', 'user.criteria.service'])
    .controller('UserController', function ($rootScope, $scope, UserResource, ErrorService, SuccessService, UserRepository, UserCriteriaService) {
        var vm = this;

        vm.registeredUserBroadcast = $rootScope.$on('registeredUser', function () {
            vm.loadData();
        });
        vm.validatedUserBroadcast = $rootScope.$on('validatedUser', function () {
            vm.loadData();
        });

        vm.init = function () {
            vm.validating = false;
            vm.users = [];
            vm.currentPage = UserCriteriaService.currentPage;
            
            vm.loadData();
        };

        vm.username = function(value) {
            return arguments.length ? (UserCriteriaService.username = value) : UserCriteriaService.username;
        };

        vm.email = function(value) {
            return arguments.length ? (UserCriteriaService.email = value) : UserCriteriaService.email;
        };

        vm.validated = function(value) {
            return arguments.length ? (UserCriteriaService.validated = value) : UserCriteriaService.validated;
        };

        vm.activated = function(value) {
            return arguments.length ? (UserCriteriaService.activated = value) : UserCriteriaService.activated;
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
            UserCriteriaService.currentPage = vm.currentPage;
            UserRepository.all({
                page: UserCriteriaService.currentPage - 1,
                validated: vm.validated(),
                activated: vm.activated(),
                username: vm.username(),
                email: vm.email()
            }).then(function () {
                vm.users = UserRepository.users;
                vm.totalElements = UserRepository.totalElements;
                vm.itemsPerPage = UserRepository.itemsPerPage;
            });

        };

        vm.init();

        $scope.$on('$destroy', function () {
            vm.registeredUserBroadcast();
            vm.validatedUserBroadcast();
        });

    });