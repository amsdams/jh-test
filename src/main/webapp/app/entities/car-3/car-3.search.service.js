(function() {
    'use strict';

    angular
        .module('testApp')
        .factory('Car3Search', Car3Search);

    Car3Search.$inject = ['$resource'];

    function Car3Search($resource) {
        var resourceUrl =  'api/_search/car-3-s/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
