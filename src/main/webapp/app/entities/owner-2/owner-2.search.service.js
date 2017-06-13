(function() {
    'use strict';

    angular
        .module('testApp')
        .factory('Owner2Search', Owner2Search);

    Owner2Search.$inject = ['$resource'];

    function Owner2Search($resource) {
        var resourceUrl =  'api/_search/owner-2-s/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
