'use strict';

angular.module('chat.private.controller', ['chat.private.service'])
.controller('PrivateChatController', function(PrivateChatService) {
    var vm = this;

    vm.shouldShow = function() {
        return PrivateChatService.show;
    };

    vm.hide = PrivateChatService.hide;

    vm.user = function() {
      return PrivateChatService.selectedUser;;
    }
});