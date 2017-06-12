(function() {
    'use strict';
    angular
        .module('testApp')
        .factory('Owner4', Owner4);

    Owner4.$inject = ['$resource'];

    function Owner4 ($resource) {
        var resourceUrl =  'api/owner-4-s/:id';

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
