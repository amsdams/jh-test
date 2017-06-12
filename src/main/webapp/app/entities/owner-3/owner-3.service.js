(function() {
    'use strict';
    angular
        .module('testApp')
        .factory('Owner3', Owner3);

    Owner3.$inject = ['$resource'];

    function Owner3 ($resource) {
        var resourceUrl =  'api/owner-3-s/:id';

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
