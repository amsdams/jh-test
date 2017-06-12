(function() {
    'use strict';

    angular
        .module('testApp')
        .controller('Car2DetailController', Car2DetailController);

    Car2DetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Car2', 'Owner2'];

    function Car2DetailController($scope, $rootScope, $stateParams, previousState, entity, Car2, Owner2) {
        var vm = this;

        vm.car2 = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('testApp:car2Update', function(event, result) {
            vm.car2 = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
