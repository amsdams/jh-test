(function() {
    'use strict';
    angular
        .module('testApp')
        .factory('Car3', Car3);

    Car3.$inject = ['$resource'];

    function Car3 ($resource) {
        var resourceUrl =  'api/car-3-s/:id';

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
