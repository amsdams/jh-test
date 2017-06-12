(function() {
    'use strict';

    angular
        .module('testApp')
        .controller('Car1DetailController', Car1DetailController);

    Car1DetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Car1', 'Owner1'];

    function Car1DetailController($scope, $rootScope, $stateParams, previousState, entity, Car1, Owner1) {
        var vm = this;

        vm.car1 = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('testApp:car1Update', function(event, result) {
            vm.car1 = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
