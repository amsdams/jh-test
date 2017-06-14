(function() {
    'use strict';

    angular
        .module('testApp')
        .controller('Owner4DialogController', Owner4DialogController);

    Owner4DialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Owner4', 'Car4'];

    function Owner4DialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Owner4, Car4) {
        var vm = this;

        vm.owner4 = entity;
        vm.clear = clear;
        vm.save = save;
        vm.car4s = Car4.query({filter: 'owner4-is-null',size:2000});
        $q.all([vm.owner4.$promise, vm.car4s.$promise]).then(function() {
            if (!vm.owner4.car4Id) {
                return $q.reject();
            }
            return Car4.get({id : vm.owner4.car4Id}).$promise;
        }).then(function(car4) {
            vm.car4s.push(car4);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.owner4.id !== null) {
                Owner4.update(vm.owner4, onSaveSuccess, onSaveError);
            } else {
                Owner4.save(vm.owner4, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('testApp:owner4Update', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
