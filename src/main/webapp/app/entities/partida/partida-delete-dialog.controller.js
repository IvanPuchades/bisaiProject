(function() {
    'use strict';

    angular
        .module('bisaiApp')
        .controller('PartidaDeleteController',PartidaDeleteController);

    PartidaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Partida'];

    function PartidaDeleteController($uibModalInstance, entity, Partida) {
        var vm = this;

        vm.partida = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Partida.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
