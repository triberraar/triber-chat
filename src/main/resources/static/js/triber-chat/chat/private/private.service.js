'use strict';

angular.module('chat.private.service', [])
    .factory('PrivateChatService', function (Websocket, _, MessageConfig, SecurityService, NotificationService) {
        var show = false, selectedUser, messages= {};

        Websocket.subscribe('/user/topic/message/private', function(message) {
            var place;
            if(message.from === SecurityService.getUsername()) {
                message.own = true;
                place = message.to;
            } else if(message.to === SecurityService.getUsername()) {
                message.own = false;
                place = message.from;
            }
            if(!messages[place]) {
                messages[place] = [];
            }
            messages[place].push(message);
            messages[place] = _.takeRight(messages[place], MessageConfig.numberOfMessages);
            if(!selectedUser) {
                NotificationService.addNotification({ key: 'privateMessage', message: 'You received private messages.', cssClass: 'fa fa-comment fa-fw', admin: false});
            } else if(!message.own && message.to !== selectedUser.username) {
                NotificationService.addNotification({ key: 'privateMessage', message: 'You received private messages.', cssClass: 'fa fa-comment fa-fw', admin: false});
            }
        });

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
            },
            getMessagesFromSelectedUser: function() {
                if(selectedUser) {
                    return messages[selectedUser.username];
                }
            }
        };

        return privateChatService;
    });