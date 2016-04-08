'use strict';

var jsFiles = [
    'js/common/warning-service.js',
    'js/common/error-service.js',
    'js/common/success-service.js'
];

function UserRepository($http, ErrorService) {
	var UserRepository = {};
	UserRepository.users = {};
    UserRepository.totalElements;
    UserRepository.itemsPerPage;
    UserRepository.all = function (queryParams) {
        return $http.get('/user', {params: queryParams}).
        then( function (response) {
            UserRepository.users = response.data.content;
            UserRepository.totalElements = response.data.totalElements;
            UserRepository.itemsPerPage = response.data.size;
        }, function () {
            ErrorService.error('Could not get users');
        });
    };
    return UserRepository;
}

angular.module('User', [jsFiles])
    .factory('UserResource', function ($resource) {
        return $resource('/user/:userId/:action', {}, {
            all: {
                method: 'GET'
            },
            validate: {
                method: 'PUT',
                params: {action: 'validate', userId: '@userId'}
            }
        });
    })
    .factory('UserRepository', UserRepository)
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