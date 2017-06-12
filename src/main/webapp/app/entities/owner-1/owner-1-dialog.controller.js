(function() {
    'use strict';

    angular
        .module('testApp')
        .controller('Owner1DialogController', Owner1DialogController);

    Owner1DialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Owner1'];

    function Owner1DialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Owner1) {
        var vm = this;

        vm.owner1 = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.owner1.id !== null) {
                Owner1.update(vm.owner1, onSaveSuccess, onSaveError);
            } else {
                Owner1.save(vm.owner1, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('testApp:owner1Update', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
