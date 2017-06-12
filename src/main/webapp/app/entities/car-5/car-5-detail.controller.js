(function() {
    'use strict';

    angular
        .module('testApp')
        .controller('Car5DetailController', Car5DetailController);

    Car5DetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Car5', 'Owner5'];

    function Car5DetailController($scope, $rootScope, $stateParams, previousState, entity, Car5, Owner5) {
        var vm = this;

        vm.car5 = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('testApp:car5Update', function(event, result) {
            vm.car5 = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
