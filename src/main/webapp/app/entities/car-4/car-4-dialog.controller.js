(function() {
    'use strict';

    angular
        .module('testApp')
        .controller('Car4DialogController', Car4DialogController);

    Car4DialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Car4'];

    function Car4DialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Car4) {
        var vm = this;

        vm.car4 = entity;
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
            if (vm.car4.id !== null) {
                Car4.update(vm.car4, onSaveSuccess, onSaveError);
            } else {
                Car4.save(vm.car4, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('testApp:car4Update', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
