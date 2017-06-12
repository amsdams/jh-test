(function() {
    'use strict';

    angular
        .module('testApp')
        .controller('Owner3DetailController', Owner3DetailController);

    Owner3DetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Owner3', 'Car3'];

    function Owner3DetailController($scope, $rootScope, $stateParams, previousState, entity, Owner3, Car3) {
        var vm = this;

        vm.owner3 = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('testApp:owner3Update', function(event, result) {
            vm.owner3 = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
