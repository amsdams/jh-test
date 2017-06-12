(function() {
    'use strict';

    angular
        .module('testApp')
        .controller('Car4DeleteController',Car4DeleteController);

    Car4DeleteController.$inject = ['$uibModalInstance', 'entity', 'Car4'];

    function Car4DeleteController($uibModalInstance, entity, Car4) {
        var vm = this;

        vm.car4 = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Car4.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
