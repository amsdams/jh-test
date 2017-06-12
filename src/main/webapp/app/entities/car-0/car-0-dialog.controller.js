(function() {
    'use strict';

    angular
        .module('testApp')
        .controller('Car0DialogController', Car0DialogController);

    Car0DialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Car0', 'Owner0'];

    function Car0DialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Car0, Owner0) {
        var vm = this;

        vm.car0 = entity;
        vm.clear = clear;
        vm.save = save;
        vm.owner0s = Owner0.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.car0.id !== null) {
                Car0.update(vm.car0, onSaveSuccess, onSaveError);
            } else {
                Car0.save(vm.car0, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('testApp:car0Update', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
