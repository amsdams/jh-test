(function() {
    'use strict';

    angular
        .module('testApp')
        .controller('Car0DeleteController',Car0DeleteController);

    Car0DeleteController.$inject = ['$uibModalInstance', 'entity', 'Car0'];

    function Car0DeleteController($uibModalInstance, entity, Car0) {
        var vm = this;

        vm.car0 = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Car0.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
