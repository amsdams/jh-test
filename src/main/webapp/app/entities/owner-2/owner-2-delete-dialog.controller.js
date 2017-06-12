(function() {
    'use strict';

    angular
        .module('testApp')
        .controller('Owner2DeleteController',Owner2DeleteController);

    Owner2DeleteController.$inject = ['$uibModalInstance', 'entity', 'Owner2'];

    function Owner2DeleteController($uibModalInstance, entity, Owner2) {
        var vm = this;

        vm.owner2 = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Owner2.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
