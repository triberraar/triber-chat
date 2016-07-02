'use strict';

angular.module('chat.private.service', [])
    .factory('PrivateChatService', function () {
        var show = false, selectedUser;
        var privateChatService = {
            chatWithUser: function(user) {
                show = true;
                selectedUser = user;
            },
            hide: function() {
                show = false;
                selectedUser = undefined;
            },
            get show() {
                return show;
            },
            get selectedUser() {
                return selectedUser;
            }
        };

        return privateChatService;
    });