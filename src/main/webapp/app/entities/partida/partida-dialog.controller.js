(function() {
    'use strict';

    angular
        .module('bisaiApp')
        .controller('PartidaDialogController', PartidaDialogController);

    PartidaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Partida', 'Equipo', 'Torneo', 'Porra'];

    function PartidaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Partida, Equipo, Torneo, Porra) {
        var vm = this;

        vm.partida = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.equipos = Equipo.query();
        vm.torneos = Torneo.query();
        vm.porras = Porra.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.partida.id !== null) {
                Partida.update(vm.partida, onSaveSuccess, onSaveError);
            } else {
                Partida.save(vm.partida, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bisaiApp:partidaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fechaInicio = false;
        vm.datePickerOpenStatus.fechaFinal = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
