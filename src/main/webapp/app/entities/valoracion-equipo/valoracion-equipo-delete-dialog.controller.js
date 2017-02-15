(function() {
    'use strict';

    angular
        .module('bisaiApp')
        .controller('ValoracionEquipoDeleteController',ValoracionEquipoDeleteController);

    ValoracionEquipoDeleteController.$inject = ['$uibModalInstance', 'entity', 'ValoracionEquipo'];

    function ValoracionEquipoDeleteController($uibModalInstance, entity, ValoracionEquipo) {
        var vm = this;

        vm.valoracionEquipo = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ValoracionEquipo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
