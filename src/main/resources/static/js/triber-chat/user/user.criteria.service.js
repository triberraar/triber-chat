'use strict';

angular.module('user.criteria.service', [])
    .factory('UserCriteriaService', function () {
        var username, email, activated , validated ;
        var userCriteriaService = {
            get username() {
                return username;
            },
            set username(value) {
                username = value ;
            },
            get email() {
                return email;
            },
            set emai(value) {
                email = value;
            },
            get activated() {
                return activated;
            },
            set activated(value) {
                activated = (value === '' ? undefined : value);
            },
            get validated() {
                return validated;
            },
            set validated(value) {
                validated = (value === '' ? undefined : value);
            }
        };

        return userCriteriaService;
    });