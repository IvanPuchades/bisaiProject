(function() {
    'use strict';

    angular
        .module('bisaiApp')
        .controller('ValoracionEquipoDialogController', ValoracionEquipoDialogController);

    ValoracionEquipoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ValoracionEquipo', 'Equipo', 'Jugador'];

    function ValoracionEquipoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ValoracionEquipo, Equipo, Jugador) {
        var vm = this;

        vm.valoracionEquipo = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.equipos = Equipo.query();
        vm.jugadors = Jugador.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.valoracionEquipo.id !== null) {
                ValoracionEquipo.update(vm.valoracionEquipo, onSaveSuccess, onSaveError);
            } else {
                ValoracionEquipo.save(vm.valoracionEquipo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bisaiApp:valoracionEquipoUpdate', result);
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
