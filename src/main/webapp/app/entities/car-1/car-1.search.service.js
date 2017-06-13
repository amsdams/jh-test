(function() {
    'use strict';

    angular
        .module('testApp')
        .factory('Car1Search', Car1Search);

    Car1Search.$inject = ['$resource'];

    function Car1Search($resource) {
        var resourceUrl =  'api/_search/car-1-s/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
