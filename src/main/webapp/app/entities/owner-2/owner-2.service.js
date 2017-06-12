(function() {
    'use strict';
    angular
        .module('testApp')
        .factory('Owner2', Owner2);

    Owner2.$inject = ['$resource'];

    function Owner2 ($resource) {
        var resourceUrl =  'api/owner-2-s/:id';

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
