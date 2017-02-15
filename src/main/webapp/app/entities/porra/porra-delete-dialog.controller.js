(function() {
    'use strict';

    angular
        .module('bisaiApp')
        .controller('PorraDeleteController',PorraDeleteController);

    PorraDeleteController.$inject = ['$uibModalInstance', 'entity', 'Porra'];

    function PorraDeleteController($uibModalInstance, entity, Porra) {
        var vm = this;

        vm.porra = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Porra.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
