(function() {
    'use strict';

    angular
        .module('testApp')
        .factory('Car4Search', Car4Search);

    Car4Search.$inject = ['$resource'];

    function Car4Search($resource) {
        var resourceUrl =  'api/_search/car-4-s/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
