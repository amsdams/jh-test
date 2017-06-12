(function() {
    'use strict';

    angular
        .module('testApp')
        .controller('Owner5DetailController', Owner5DetailController);

    Owner5DetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Owner5', 'Car5'];

    function Owner5DetailController($scope, $rootScope, $stateParams, previousState, entity, Owner5, Car5) {
        var vm = this;

        vm.owner5 = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('testApp:owner5Update', function(event, result) {
            vm.owner5 = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
