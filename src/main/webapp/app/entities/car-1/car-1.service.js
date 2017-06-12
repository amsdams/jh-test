(function() {
    'use strict';
    angular
        .module('testApp')
        .factory('Car1', Car1);

    Car1.$inject = ['$resource'];

    function Car1 ($resource) {
        var resourceUrl =  'api/car-1-s/:id';

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
