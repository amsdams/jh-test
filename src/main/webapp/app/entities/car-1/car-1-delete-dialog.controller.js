(function() {
    'use strict';

    angular
        .module('testApp')
        .controller('Car1DeleteController',Car1DeleteController);

    Car1DeleteController.$inject = ['$uibModalInstance', 'entity', 'Car1'];

    function Car1DeleteController($uibModalInstance, entity, Car1) {
        var vm = this;

        vm.car1 = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Car1.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
