(function() {
    'use strict';

    angular
        .module('testApp')
        .controller('Owner3DeleteController',Owner3DeleteController);

    Owner3DeleteController.$inject = ['$uibModalInstance', 'entity', 'Owner3'];

    function Owner3DeleteController($uibModalInstance, entity, Owner3) {
        var vm = this;

        vm.owner3 = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Owner3.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
