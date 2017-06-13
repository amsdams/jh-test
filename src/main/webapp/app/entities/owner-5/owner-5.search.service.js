(function() {
    'use strict';

    angular
        .module('testApp')
        .factory('Owner5Search', Owner5Search);

    Owner5Search.$inject = ['$resource'];

    function Owner5Search($resource) {
        var resourceUrl =  'api/_search/owner-5-s/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
