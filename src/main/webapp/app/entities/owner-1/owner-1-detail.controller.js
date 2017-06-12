(function() {
    'use strict';

    angular
        .module('testApp')
        .controller('Owner1DetailController', Owner1DetailController);

    Owner1DetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Owner1'];

    function Owner1DetailController($scope, $rootScope, $stateParams, previousState, entity, Owner1) {
        var vm = this;

        vm.owner1 = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('testApp:owner1Update', function(event, result) {
            vm.owner1 = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
