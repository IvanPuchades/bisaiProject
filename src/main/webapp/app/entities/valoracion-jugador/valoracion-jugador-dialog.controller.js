(function() {
    'use strict';

    angular
        .module('bisaiApp')
        .controller('ValoracionJugadorDialogController', ValoracionJugadorDialogController);

    ValoracionJugadorDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ValoracionJugador', 'Jugador'];

    function ValoracionJugadorDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ValoracionJugador, Jugador) {
        var vm = this;

        vm.valoracionJugador = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.jugadors = Jugador.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.valoracionJugador.id !== null) {
                ValoracionJugador.update(vm.valoracionJugador, onSaveSuccess, onSaveError);
            } else {
                ValoracionJugador.save(vm.valoracionJugador, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bisaiApp:valoracionJugadorUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.hora = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
