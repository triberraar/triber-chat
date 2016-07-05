'use strict';

angular.module('chat.private.controller', ['chat.private.service'])
.controller('PrivateChatController', function(PrivateChatService, Websocket, SecurityService) {
    var vm = this;

    vm.shouldShow = function() {
        return PrivateChatService.show;
    };

    vm.hide = PrivateChatService.hide;

    vm.user = function() {
      return PrivateChatService.selectedUser;
    };

    vm.chat = function() {
        if(vm.message && vm.message !== '') {
            Websocket.send('/app/message/private', {
                content: vm.message,
                to: PrivateChatService.selectedUser.username,
                from: SecurityService.getUsername()
            });
            vm.message = '';
        }
    };

    vm.messages = PrivateChatService.getMessagesFromSelectedUser;
});