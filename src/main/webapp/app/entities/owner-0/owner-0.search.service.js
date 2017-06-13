(function() {
    'use strict';

    angular
        .module('testApp')
        .factory('Owner0Search', Owner0Search);

    Owner0Search.$inject = ['$resource'];

    function Owner0Search($resource) {
        var resourceUrl =  'api/_search/owner-0-s/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
