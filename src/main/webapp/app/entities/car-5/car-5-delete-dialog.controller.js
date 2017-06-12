(function() {
    'use strict';

    angular
        .module('testApp')
        .controller('Car5DeleteController',Car5DeleteController);

    Car5DeleteController.$inject = ['$uibModalInstance', 'entity', 'Car5'];

    function Car5DeleteController($uibModalInstance, entity, Car5) {
        var vm = this;

        vm.car5 = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Car5.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
