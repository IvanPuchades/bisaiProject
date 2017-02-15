(function() {
    'use strict';

    angular
        .module('bisaiApp')
        .controller('PorraDialogController', PorraDialogController);

    PorraDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Porra', 'Jugador', 'Partida'];

    function PorraDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Porra, Jugador, Partida) {
        var vm = this;

        vm.porra = entity;
        vm.clear = clear;
        vm.save = save;
        vm.jugadors = Jugador.query();
        vm.partidas = Partida.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.porra.id !== null) {
                Porra.update(vm.porra, onSaveSuccess, onSaveError);
            } else {
                Porra.save(vm.porra, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bisaiApp:porraUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
