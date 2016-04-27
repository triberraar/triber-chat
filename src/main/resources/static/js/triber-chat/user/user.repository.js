'use strict';

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

angular.module('user.repository', [ 'errorService'])
    .factory('UserRepository', UserRepository);