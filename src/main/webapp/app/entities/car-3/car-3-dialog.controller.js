(function() {
    'use strict';

    angular
        .module('testApp')
        .controller('Car3DialogController', Car3DialogController);

    Car3DialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Car3', 'Owner3'];

    function Car3DialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Car3, Owner3) {
        var vm = this;

        vm.car3 = entity;
        vm.clear = clear;
        vm.save = save;
        vm.owner3s = Owner3.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.car3.id !== null) {
                Car3.update(vm.car3, onSaveSuccess, onSaveError);
            } else {
                Car3.save(vm.car3, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('testApp:car3Update', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
