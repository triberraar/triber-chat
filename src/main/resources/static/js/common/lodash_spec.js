'use strict';

describe('_', function() {

    var windowMock = {
        _ : 'lodash'
    };

    beforeEach(module('_'));

    beforeEach(module(function($provide) {
        $provide.value('$window', windowMock);
    }));

    beforeEach(inject(function(___) {
        _ = ___;
    }));

    it('should return lodash', function() {
        expect(_).toEqual('lodash');
    });
});