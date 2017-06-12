(function() {
    'use strict';

    angular
        .module('testApp')
        .controller('Owner2DetailController', Owner2DetailController);

    Owner2DetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Owner2', 'Car2'];

    function Owner2DetailController($scope, $rootScope, $stateParams, previousState, entity, Owner2, Car2) {
        var vm = this;

        vm.owner2 = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('testApp:owner2Update', function(event, result) {
            vm.owner2 = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
