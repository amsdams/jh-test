(function() {
    'use strict';
    angular
        .module('testApp')
        .factory('Owner1', Owner1);

    Owner1.$inject = ['$resource'];

    function Owner1 ($resource) {
        var resourceUrl =  'api/owner-1-s/:id';

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
