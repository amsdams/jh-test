(function() {
    'use strict';

    angular
        .module('testApp')
        .controller('Owner1DeleteController',Owner1DeleteController);

    Owner1DeleteController.$inject = ['$uibModalInstance', 'entity', 'Owner1'];

    function Owner1DeleteController($uibModalInstance, entity, Owner1) {
        var vm = this;

        vm.owner1 = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Owner1.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
