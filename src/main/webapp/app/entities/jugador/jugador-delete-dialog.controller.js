(function() {
    'use strict';

    angular
        .module('bisaiApp')
        .controller('JugadorDeleteController',JugadorDeleteController);

    JugadorDeleteController.$inject = ['$uibModalInstance', 'entity', 'Jugador'];

    function JugadorDeleteController($uibModalInstance, entity, Jugador) {
        var vm = this;

        vm.jugador = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Jugador.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
