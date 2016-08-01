'use strict';

angular.module('generalChat', ['_', 'connected-user.component'])
.factory('GeneralChatService', function( _, Websocket, MessageConfig) {
	var messages = [];

	Websocket.subscribe('/topic/message/general', function(message) {
		messages.push(message);
		messages = _.takeRight(messages, MessageConfig.numberOfMessages);
	});

	var generalChatService = {
		getMessages: function() {
			return messages;
		},
		sendMessage: function(message) {
			Websocket.send('/app/message/general', message);
		}
	};
	
	return generalChatService;
	
})
.controller('GeneralChatController', function(Websocket, GeneralChatService) {
	var vm = this;
	
	vm.connected = function() {
		return Websocket.connected();
	};
	
	vm.say = function() {
		if( angular.isDefined( vm.content) && vm.content.trim() != '' && vm.messageForm.$valid) {
			GeneralChatService.sendMessage({content: vm.content});
			vm.content=undefined;
		}
	};
	
	vm.messages = function() {
		return GeneralChatService.getMessages();
	};
	
});