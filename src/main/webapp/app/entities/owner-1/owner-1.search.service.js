(function() {
    'use strict';

    angular
        .module('testApp')
        .factory('Owner1Search', Owner1Search);

    Owner1Search.$inject = ['$resource'];

    function Owner1Search($resource) {
        var resourceUrl =  'api/_search/owner-1-s/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
