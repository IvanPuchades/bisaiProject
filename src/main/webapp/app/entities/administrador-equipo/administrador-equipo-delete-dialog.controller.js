(function() {
    'use strict';

    angular
        .module('bisaiApp')
        .controller('AdministradorEquipoDeleteController',AdministradorEquipoDeleteController);

    AdministradorEquipoDeleteController.$inject = ['$uibModalInstance', 'entity', 'AdministradorEquipo'];

    function AdministradorEquipoDeleteController($uibModalInstance, entity, AdministradorEquipo) {
        var vm = this;

        vm.administradorEquipo = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            AdministradorEquipo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
