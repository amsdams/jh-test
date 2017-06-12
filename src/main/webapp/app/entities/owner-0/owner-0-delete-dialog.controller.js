(function() {
    'use strict';

    angular
        .module('testApp')
        .controller('Owner0DeleteController',Owner0DeleteController);

    Owner0DeleteController.$inject = ['$uibModalInstance', 'entity', 'Owner0'];

    function Owner0DeleteController($uibModalInstance, entity, Owner0) {
        var vm = this;

        vm.owner0 = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Owner0.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
