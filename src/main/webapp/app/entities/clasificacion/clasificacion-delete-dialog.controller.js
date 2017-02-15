(function() {
    'use strict';

    angular
        .module('bisaiApp')
        .controller('ClasificacionDeleteController',ClasificacionDeleteController);

    ClasificacionDeleteController.$inject = ['$uibModalInstance', 'entity', 'Clasificacion'];

    function ClasificacionDeleteController($uibModalInstance, entity, Clasificacion) {
        var vm = this;

        vm.clasificacion = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Clasificacion.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
