(function() {
    'use strict';

    angular
        .module('bisaiApp')
        .controller('EquipoDeleteController',EquipoDeleteController);

    EquipoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Equipo'];

    function EquipoDeleteController($uibModalInstance, entity, Equipo) {
        var vm = this;

        vm.equipo = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Equipo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
