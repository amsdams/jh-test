(function() {
    'use strict';
    angular
        .module('testApp')
        .factory('Owner0', Owner0);

    Owner0.$inject = ['$resource'];

    function Owner0 ($resource) {
        var resourceUrl =  'api/owner-0-s/:id';

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
