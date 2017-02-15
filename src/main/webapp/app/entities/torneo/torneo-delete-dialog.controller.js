(function() {
    'use strict';

    angular
        .module('bisaiApp')
        .controller('TorneoDeleteController',TorneoDeleteController);

    TorneoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Torneo'];

    function TorneoDeleteController($uibModalInstance, entity, Torneo) {
        var vm = this;

        vm.torneo = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Torneo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
