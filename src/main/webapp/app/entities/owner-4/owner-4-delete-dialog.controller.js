(function() {
    'use strict';

    angular
        .module('testApp')
        .controller('Owner4DeleteController',Owner4DeleteController);

    Owner4DeleteController.$inject = ['$uibModalInstance', 'entity', 'Owner4'];

    function Owner4DeleteController($uibModalInstance, entity, Owner4) {
        var vm = this;

        vm.owner4 = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Owner4.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
