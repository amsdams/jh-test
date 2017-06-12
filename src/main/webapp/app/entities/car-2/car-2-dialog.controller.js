(function() {
    'use strict';

    angular
        .module('testApp')
        .controller('Car2DialogController', Car2DialogController);

    Car2DialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Car2', 'Owner2'];

    function Car2DialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Car2, Owner2) {
        var vm = this;

        vm.car2 = entity;
        vm.clear = clear;
        vm.save = save;
        vm.owner2s = Owner2.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.car2.id !== null) {
                Car2.update(vm.car2, onSaveSuccess, onSaveError);
            } else {
                Car2.save(vm.car2, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('testApp:car2Update', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
