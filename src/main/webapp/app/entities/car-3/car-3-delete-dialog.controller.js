(function() {
    'use strict';

    angular
        .module('testApp')
        .controller('Car3DeleteController',Car3DeleteController);

    Car3DeleteController.$inject = ['$uibModalInstance', 'entity', 'Car3'];

    function Car3DeleteController($uibModalInstance, entity, Car3) {
        var vm = this;

        vm.car3 = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Car3.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
