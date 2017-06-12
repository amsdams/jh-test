(function() {
    'use strict';

    angular
        .module('testApp')
        .controller('Owner0DetailController', Owner0DetailController);

    Owner0DetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Owner0', 'Car0'];

    function Owner0DetailController($scope, $rootScope, $stateParams, previousState, entity, Owner0, Car0) {
        var vm = this;

        vm.owner0 = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('testApp:owner0Update', function(event, result) {
            vm.owner0 = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
