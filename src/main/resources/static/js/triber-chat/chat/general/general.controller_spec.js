'use strict';

describe('chat.general.controller', function() {

    var rootScope, $controller, controller;

    var websocketMock = {
        connected: function() {
            return true;
        }
    }, generalChatServiceMock = {
        sendMessage:function() {},
        getMessages: function() {
            return 'messages';
        }
    };

    beforeEach(module('chat.general.controller'));

    beforeEach(inject(function(_$controller_, _$rootScope_) {
        rootScope = _$rootScope_;
        $controller = _$controller_;
    }));

    var createController = function() {
        var scope = rootScope.$new();
        return $controller('GeneralChatController', {
            $rootScope: rootScope,
            $scope: scope,
            Websocket: websocketMock,
            GeneralChatService: generalChatServiceMock
        });
    };

    beforeEach(function() {
        controller = createController();
    });

    describe('connected', function() {
        it('should check if the websocket is connected', function() {
            expect(controller.connected()).toEqual(true);
        });
    });
    describe('say', function() {
        beforeEach(function() {
            controller = createController();
            controller.content = 'content';
            controller.messageForm = {
                $valid: true
            };
            spyOn(generalChatServiceMock, 'sendMessage');
        });
        it('should send the message if it is filled in and the form is valid', function() {
            controller.say();

            expect(generalChatServiceMock.sendMessage).toHaveBeenCalled();
            expect(generalChatServiceMock.sendMessage).toHaveBeenCalledWith({content: 'content'});
            expect(controller.content).toBeUndefined();
        });
        it('should do nothing when the message is undefined', function() {
            controller.content = undefined;

            controller.say();

            expect(generalChatServiceMock.sendMessage).not.toHaveBeenCalled();
        });
        it('should do nothing when the message is only blanks', function() {
            controller.content = '  ';

            controller.say();

            expect(generalChatServiceMock.sendMessage).not.toHaveBeenCalled();
        });
        it('should do nothing when the form is invalid', function() {
            controller.messageForm.$valid = false;

            controller.say();

            expect(generalChatServiceMock.sendMessage).not.toHaveBeenCalled();
        });
    });
    describe('messages', function() {
        it('should return all the messages', function() {

            expect(controller.messages()).toEqual('messages');
        });
    });
});



