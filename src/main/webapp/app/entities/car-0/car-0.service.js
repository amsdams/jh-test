(function() {
    'use strict';
    angular
        .module('testApp')
        .factory('Car0', Car0);

    Car0.$inject = ['$resource'];

    function Car0 ($resource) {
        var resourceUrl =  'api/car-0-s/:id';

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
