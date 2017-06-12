(function() {
    'use strict';
    angular
        .module('testApp')
        .factory('Car5', Car5);

    Car5.$inject = ['$resource'];

    function Car5 ($resource) {
        var resourceUrl =  'api/car-5-s/:id';

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
