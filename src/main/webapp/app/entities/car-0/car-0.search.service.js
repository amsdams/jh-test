(function() {
    'use strict';

    angular
        .module('testApp')
        .factory('Car0Search', Car0Search);

    Car0Search.$inject = ['$resource'];

    function Car0Search($resource) {
        var resourceUrl =  'api/_search/car-0-s/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
