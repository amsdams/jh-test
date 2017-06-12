(function() {
    'use strict';

    angular
        .module('testApp')
        .controller('Car5DialogController', Car5DialogController);

    Car5DialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Car5', 'Owner5'];

    function Car5DialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Car5, Owner5) {
        var vm = this;

        vm.car5 = entity;
        vm.clear = clear;
        vm.save = save;
        vm.owner5s = Owner5.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.car5.id !== null) {
                Car5.update(vm.car5, onSaveSuccess, onSaveError);
            } else {
                Car5.save(vm.car5, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('testApp:car5Update', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
