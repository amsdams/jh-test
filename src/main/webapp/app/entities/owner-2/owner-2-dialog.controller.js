(function() {
    'use strict';

    angular
        .module('testApp')
        .controller('Owner2DialogController', Owner2DialogController);

    Owner2DialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Owner2', 'Car2'];

    function Owner2DialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Owner2, Car2) {
        var vm = this;

        vm.owner2 = entity;
        vm.clear = clear;
        vm.save = save;
        vm.car2s = Car2.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.owner2.id !== null) {
                Owner2.update(vm.owner2, onSaveSuccess, onSaveError);
            } else {
                Owner2.save(vm.owner2, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('testApp:owner2Update', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
