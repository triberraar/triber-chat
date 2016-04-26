'use strict';

angular.module('user.resource', [])
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
    });
