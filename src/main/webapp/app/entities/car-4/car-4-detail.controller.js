(function() {
    'use strict';

    angular
        .module('testApp')
        .controller('Car4DetailController', Car4DetailController);

    Car4DetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Car4'];

    function Car4DetailController($scope, $rootScope, $stateParams, previousState, entity, Car4) {
        var vm = this;

        vm.car4 = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('testApp:car4Update', function(event, result) {
            vm.car4 = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
