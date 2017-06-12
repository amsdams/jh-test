(function() {
    'use strict';

    angular
        .module('testApp')
        .controller('Owner0DialogController', Owner0DialogController);

    Owner0DialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Owner0', 'Car0'];

    function Owner0DialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Owner0, Car0) {
        var vm = this;

        vm.owner0 = entity;
        vm.clear = clear;
        vm.save = save;
        vm.car0s = Car0.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.owner0.id !== null) {
                Owner0.update(vm.owner0, onSaveSuccess, onSaveError);
            } else {
                Owner0.save(vm.owner0, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('testApp:owner0Update', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
