(function() {
    'use strict';

    angular
        .module('bisaiApp')
        .controller('EquipoDialogController', EquipoDialogController);

    EquipoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Equipo', 'Jugador', 'Clasificacion', 'ValoracionEquipo', 'AdministradorEquipo', 'Partida', 'Torneo'];

    function EquipoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Equipo, Jugador, Clasificacion, ValoracionEquipo, AdministradorEquipo, Partida, Torneo) {
        var vm = this;

        vm.equipo = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.jugadors = Jugador.query();
        vm.clasificacions = Clasificacion.query();
        vm.valoracionequipos = ValoracionEquipo.query();
        vm.administradorequipos = AdministradorEquipo.query();
        vm.partidas = Partida.query();
        vm.torneos = Torneo.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.equipo.id !== null) {
                Equipo.update(vm.equipo, onSaveSuccess, onSaveError);
            } else {
                Equipo.save(vm.equipo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bisaiApp:equipoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fechaCreacion = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
