(function() {
    'use strict';

    angular
        .module('testApp')
        .controller('Owner3DialogController', Owner3DialogController);

    Owner3DialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Owner3', 'Car3'];

    function Owner3DialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Owner3, Car3) {
        var vm = this;

        vm.owner3 = entity;
        vm.clear = clear;
        vm.save = save;
        vm.car3s = Car3.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.owner3.id !== null) {
                Owner3.update(vm.owner3, onSaveSuccess, onSaveError);
            } else {
                Owner3.save(vm.owner3, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('testApp:owner3Update', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
