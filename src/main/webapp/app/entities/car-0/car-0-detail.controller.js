(function() {
    'use strict';

    angular
        .module('testApp')
        .controller('Car0DetailController', Car0DetailController);

    Car0DetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Car0', 'Owner0'];

    function Car0DetailController($scope, $rootScope, $stateParams, previousState, entity, Car0, Owner0) {
        var vm = this;

        vm.car0 = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('testApp:car0Update', function(event, result) {
            vm.car0 = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
