(function() {
    'use strict';

    angular
        .module('testApp')
        .factory('Car2Search', Car2Search);

    Car2Search.$inject = ['$resource'];

    function Car2Search($resource) {
        var resourceUrl =  'api/_search/car-2-s/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
