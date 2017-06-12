(function() {
    'use strict';

    angular
        .module('testApp')
        .controller('Car1DialogController', Car1DialogController);

    Car1DialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Car1', 'Owner1'];

    function Car1DialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Car1, Owner1) {
        var vm = this;

        vm.car1 = entity;
        vm.clear = clear;
        vm.save = save;
        vm.owner1s = Owner1.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.car1.id !== null) {
                Car1.update(vm.car1, onSaveSuccess, onSaveError);
            } else {
                Car1.save(vm.car1, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('testApp:car1Update', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
