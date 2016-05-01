'use strict';

describe('service', function() {

    var notificationService, $rootScope, $httpBackend;

    var securityServiceMock = {
        hasRole: function() {}
    }, websocketMock = {
        subscribe:function() {}
    };

    beforeEach(module('notificationService'));

    beforeEach(module(function ($provide) {
        $provide.value('SecurityService', securityServiceMock);
        $provide.value('Websocket', websocketMock);
    }));

    describe('init', function() {

        it('should subscribe to registeredUser and validatedUser when admin', function() {
            spyOn(securityServiceMock, 'hasRole').and.returnValue(true);
            spyOn(websocketMock, 'subscribe');

            inject(function(_NotificationService_) {
                notificationService = _NotificationService_;
            });

            expect(securityServiceMock.hasRole).toHaveBeenCalled();
            expect(securityServiceMock.hasRole).toHaveBeenCalledWith('ROLE_ADMIN');
            expect(websocketMock.subscribe).toHaveBeenCalledTimes(2);
            expect(websocketMock.subscribe).toHaveBeenCalledWith('/topic/notifications/registeredUser', 'registeredUser');
            expect(websocketMock.subscribe).toHaveBeenCalledWith('/topic/notifications/validatedUser', 'validatedUser');
        });
        it('shouldn\'t subscribe when not admin', function() {
            spyOn(securityServiceMock, 'hasRole').and.returnValue(false);
            spyOn(websocketMock, 'subscribe');

            inject(function(_NotificationService_, _$rootScope_) {
                notificationService = _NotificationService_;
                $rootScope = _$rootScope_;
            });

            expect(securityServiceMock.hasRole).toHaveBeenCalled();
            expect(securityServiceMock.hasRole).toHaveBeenCalledWith('ROLE_ADMIN');
            expect(websocketMock.subscribe).not.toHaveBeenCalled();
        });
    });
    describe('on registeredUser event', function() {
        beforeEach(inject(function(_NotificationService_, _$rootScope_, _$httpBackend_) {
            notificationService = _NotificationService_;
            $rootScope = _$rootScope_;
            $httpBackend = _$httpBackend_;
        }));
        it('should add notification if unvalidated user exists', function() {
            $httpBackend.expectGET('/user/unvalidated').respond(200);
            $rootScope.$emit('registeredUser');
            $httpBackend.flush();

            expect(notificationService.notification('unvalidatedUser')).toBeDefined();
        });
        it('should remove notification if no unvalidated user exists', function() {
            $httpBackend.expectGET('/user/unvalidated').respond(404);
            $rootScope.$emit('registeredUser');
            $httpBackend.flush();

            expect(notificationService.notification('unvalidatedUser')).toBeUndefined();
        });

        afterEach(function() {
            $httpBackend.verifyNoOutstandingExpectation();
            $httpBackend.verifyNoOutstandingRequest();
        });
    });
    describe('on validatedUser event', function() {
        beforeEach(inject(function(_NotificationService_, _$rootScope_, _$httpBackend_) {
            notificationService = _NotificationService_;
            $rootScope = _$rootScope_;
            $httpBackend = _$httpBackend_;
        }));
        it('should add notification if unvalidated user exists', function() {
            $httpBackend.expectGET('/user/unvalidated').respond(200);
            $rootScope.$emit('validatedUser');
            $httpBackend.flush();

            expect(notificationService.notification('unvalidatedUser')).toBeDefined();
        });
        it('should remove notification if no unvalidated user exists', function() {
            $httpBackend.expectGET('/user/unvalidated').respond(404);
            $rootScope.$emit('validatedUser');
            $httpBackend.flush();

            expect(notificationService.notification('unvalidatedUser')).toBeUndefined();
        });
        afterEach(function() {
            $httpBackend.verifyNoOutstandingExpectation();
            $httpBackend.verifyNoOutstandingRequest();
        });
    });
    describe('on connected event', function() {
        beforeEach(inject(function(_NotificationService_, _$rootScope_, _$httpBackend_) {
            notificationService = _NotificationService_;
            $rootScope = _$rootScope_;
            $httpBackend = _$httpBackend_;
        }));
        it('should add notification if unvalidated user exists', function() {
            $httpBackend.expectGET('/user/unvalidated').respond(200);
            $rootScope.$emit('connected');
            $httpBackend.flush();

            expect(notificationService.notification('unvalidatedUser')).toBeDefined();
        });
        it('should remove notification if no unvalidated user exists', function() {
            $httpBackend.expectGET('/user/unvalidated').respond(404);
            $rootScope.$emit('connected');
            $httpBackend.flush();

            expect(notificationService.notification('unvalidatedUser')).toBeUndefined();
        });
        afterEach(function() {
            $httpBackend.verifyNoOutstandingExpectation();
            $httpBackend.verifyNoOutstandingRequest();
        });
    });
    describe('addNotification', function() {
        beforeEach(inject(function(_NotificationService_) {
            notificationService = _NotificationService_;
        }));
        it('should add the notification and increase number of notification if notification does\'t exist already', function() {
            notificationService.addNotification('key', 'value');

            expect(notificationService.notification('key')).toEqual('value');
            expect(notificationService.numberOfNotifications()).toEqual(1);
        });
        it('should do nothing if notification already exists', function() {
            notificationService.addNotification('key', 'value');
            notificationService.addNotification('key', 'value');

            expect(notificationService.notification('key')).toEqual('value');
            expect(notificationService.numberOfNotifications()).toEqual(1);
        });
    });
    describe('removeNotification', function() {
        beforeEach(inject(function(_NotificationService_) {
            notificationService = _NotificationService_;
        }));
        it('should remove the notification and decrease number of notifications if notification exists', function() {
            notificationService.addNotification('key', 'value');
            notificationService.removeNotification('key');

            expect(notificationService.notification('key')).toBeUndefined();
            expect(notificationService.numberOfNotifications()).toEqual(0);
        });
        it('should do nothing if notification doesn\'t exist', function() {
            notificationService.removeNotification('key');

            expect(notificationService.notification('key')).toBeUndefined();
            expect(notificationService.numberOfNotifications()).toEqual(0);
        });
    });
    describe('checkUnvalidatedUsers', function() {
        beforeEach(inject(function(_NotificationService_, _$httpBackend_) {
            notificationService = _NotificationService_;
            $httpBackend = _$httpBackend_;
        }));
        it('should add notification if unvalidated user exists', function() {
            $httpBackend.expectGET('/user/unvalidated').respond(200);

            notificationService.checkUnvalidatedUsers();

            $httpBackend.flush();

            expect(notificationService.notification('unvalidatedUser')).toEqual('There are unvalidated users.');
        });
        it('should remove notification if no unvalidated user exists', function() {
            $httpBackend.expectGET('/user/unvalidated').respond(404);

            notificationService.checkUnvalidatedUsers();

            $httpBackend.flush();

            expect(notificationService.notification('unvalidatedUser')).toBeUndefined();
        });
        afterEach(function() {
            $httpBackend.verifyNoOutstandingExpectation();
            $httpBackend.verifyNoOutstandingRequest();
        });
    });
    describe('notifications', function() {
        it('should return the notifications', function() {
            notificationService.addNotification('key', 'value');
            notificationService.addNotification('key2', 'value2');

            expect(notificationService.notifications()).toEqual({key: 'value', key2: 'value2'});
        });
    });
    describe('notification', function() {
        beforeEach(inject(function(_NotificationService_) {
            notificationService = _NotificationService_;
        }));
        it('should return the notification with key', function() {
            notificationService.addNotification('key', 'value');

            expect(notificationService.notification('key')).toEqual('value');
        });
    });
    describe('numberOfNotifications', function() {
        beforeEach(inject(function(_NotificationService_) {
            notificationService = _NotificationService_;
        }));
        it('should return the number of notifications', function() {
            expect(notificationService.numberOfNotifications()).toEqual(0);
            notificationService.addNotification('key', 'value');

            expect(notificationService.numberOfNotifications()).toEqual(1);
            notificationService.addNotification('key2', 'value');
            expect(notificationService.numberOfNotifications()).toEqual(2);
        });
    });
});