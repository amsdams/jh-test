(function() {
    'use strict';

    angular
        .module('testApp')
        .controller('Car2DeleteController',Car2DeleteController);

    Car2DeleteController.$inject = ['$uibModalInstance', 'entity', 'Car2'];

    function Car2DeleteController($uibModalInstance, entity, Car2) {
        var vm = this;

        vm.car2 = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Car2.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
