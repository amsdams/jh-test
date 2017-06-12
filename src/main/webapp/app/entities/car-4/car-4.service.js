(function() {
    'use strict';
    angular
        .module('testApp')
        .factory('Car4', Car4);

    Car4.$inject = ['$resource'];

    function Car4 ($resource) {
        var resourceUrl =  'api/car-4-s/:id';

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
