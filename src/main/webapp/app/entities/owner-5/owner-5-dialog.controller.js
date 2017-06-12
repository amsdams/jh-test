(function() {
    'use strict';

    angular
        .module('testApp')
        .controller('Owner5DialogController', Owner5DialogController);

    Owner5DialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Owner5', 'Car5'];

    function Owner5DialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Owner5, Car5) {
        var vm = this;

        vm.owner5 = entity;
        vm.clear = clear;
        vm.save = save;
        vm.car5s = Car5.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.owner5.id !== null) {
                Owner5.update(vm.owner5, onSaveSuccess, onSaveError);
            } else {
                Owner5.save(vm.owner5, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('testApp:owner5Update', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
