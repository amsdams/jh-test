(function() {
    'use strict';
    angular
        .module('testApp')
        .factory('Owner5', Owner5);

    Owner5.$inject = ['$resource'];

    function Owner5 ($resource) {
        var resourceUrl =  'api/owner-5-s/:id';

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
