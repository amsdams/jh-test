(function() {
    'use strict';

    angular
        .module('testApp')
        .controller('Car3DetailController', Car3DetailController);

    Car3DetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Car3', 'Owner3'];

    function Car3DetailController($scope, $rootScope, $stateParams, previousState, entity, Car3, Owner3) {
        var vm = this;

        vm.car3 = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('testApp:car3Update', function(event, result) {
            vm.car3 = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
