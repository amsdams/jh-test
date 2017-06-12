(function() {
    'use strict';

    angular
        .module('testApp')
        .controller('Owner4DetailController', Owner4DetailController);

    Owner4DetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Owner4', 'Car4'];

    function Owner4DetailController($scope, $rootScope, $stateParams, previousState, entity, Owner4, Car4) {
        var vm = this;

        vm.owner4 = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('testApp:owner4Update', function(event, result) {
            vm.owner4 = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
