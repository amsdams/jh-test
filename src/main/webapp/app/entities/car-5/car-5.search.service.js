(function() {
    'use strict';

    angular
        .module('testApp')
        .factory('Car5Search', Car5Search);

    Car5Search.$inject = ['$resource'];

    function Car5Search($resource) {
        var resourceUrl =  'api/_search/car-5-s/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
