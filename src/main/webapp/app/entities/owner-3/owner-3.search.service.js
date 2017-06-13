(function() {
    'use strict';

    angular
        .module('testApp')
        .factory('Owner3Search', Owner3Search);

    Owner3Search.$inject = ['$resource'];

    function Owner3Search($resource) {
        var resourceUrl =  'api/_search/owner-3-s/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
