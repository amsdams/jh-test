(function() {
    'use strict';

    angular
        .module('testApp')
        .factory('Owner4Search', Owner4Search);

    Owner4Search.$inject = ['$resource'];

    function Owner4Search($resource) {
        var resourceUrl =  'api/_search/owner-4-s/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
