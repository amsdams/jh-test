(function() {
    'use strict';

    angular
        .module('testApp')
        .controller('Owner5DeleteController',Owner5DeleteController);

    Owner5DeleteController.$inject = ['$uibModalInstance', 'entity', 'Owner5'];

    function Owner5DeleteController($uibModalInstance, entity, Owner5) {
        var vm = this;

        vm.owner5 = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Owner5.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
