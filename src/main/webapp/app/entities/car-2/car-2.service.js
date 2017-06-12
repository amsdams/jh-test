(function() {
    'use strict';
    angular
        .module('testApp')
        .factory('Car2', Car2);

    Car2.$inject = ['$resource'];

    function Car2 ($resource) {
        var resourceUrl =  'api/car-2-s/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
